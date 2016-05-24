package com.seventh.personalfinance;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


import com.seventh.db.AccountDBdao;
import com.seventh.db.PersonDBdao;
import com.seventh.view.CornerListView;
import com.seventh.view.DataRange;
import com.seventh.view.MainActivityService;
import com.seventh.view.PieChart;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnClickListener,
		OnTouchListener, OnGestureListener {
	private Intent intent = null;// ����һ����ͼ
	private String name;// �˺�
	private String pwd1;
	private String pwd2;
	AccountDBdao accountDBdao;// ���ݿ�
	PersonDBdao persondbdao;

	private EditText mEditTextName;// �˺�
	private EditText mEditTextPwd1;// ����
	private EditText mEditTextPwd2;// ����
	private Button mButtonOK;
	private Button mButtonCancel;

	private TextView mTextViewTime;// ʱ��
	private Button mButtonAddNodes;// ��һ�ʰ�ť

	private PieChart piechart;// ����ͼ
	private LinearLayout piechar;
	private int varlue1;// �������
	private int varlue2;// ֧������

	private float totalOut;// ��֧��
	private float totalInto;// ������

	private CornerListView cornerListView1 = null;// �Զ���listview1
	private List<Map<String, String>> map_list1 = null;

	private CornerListView cornerListView2 = null;// �Զ���listview2
	private List<DataRange> dataRanges;
	private LayoutInflater inflater;

	private LinearLayout ll_right;
	private LinearLayout ll_left;
	private GestureDetector mGestureDetector; // ���Ƽ����

	private int window_width; // ��Ļ�Ŀ��
	private static float SNAP_VELOCITY = 400; // x�����ϻ����ľ���
	private int SPEED = 30; // �������ٶ�
	private int MAX_WIDTH = 0; // ������������
	private int mScrollX;
	private boolean isScrolling = false;
	private boolean isFinish = true;
	private boolean isMenuOpen = false;
	private boolean hasMeasured = false; // �Ƿ�Measured.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainactivity);

		intent = this.getIntent();
		name = intent.getStringExtra("name");// ���յ�¼���������
		if (name == null) {
			intent = new Intent(this, Login.class);
			startActivity(intent);
			finish();
		} else {

			// ����ʱ��
			mTextViewTime = (TextView) this.findViewById(R.id.tv_main_time);
			mTextViewTime.setText(GetTime());// ����ʱ��

			accountDBdao = new AccountDBdao(getApplicationContext());
			totalOut = accountDBdao.fillTotalOut(name);// ��֧��
			totalInto = accountDBdao.fillTotalInto(name);// ������

			// ���ñ���ͼ
			SetVarlue(totalOut, totalInto);
			piechar = (LinearLayout) findViewById(R.id.barchart);
			piechart = new PieChart();
			piechart.paintingPieChart(getApplicationContext(), piechar,
					varlue1, varlue2);

			// ����listview1 ֵ
			cornerListView1 = (CornerListView) findViewById(R.id.lv_main_calculation);
			map_list1 = MainActivityService.getDataSource1(totalInto, totalOut);
			// listview1������
			SimpleAdapter adapter1 = new SimpleAdapter(getApplicationContext(),
					map_list1, R.layout.main_listview_calculation,
					new String[] { "txtCalculationName", "txtMoney" },
					new int[] { R.id.ls_tv_txtCalculationName,
							R.id.ls_tv_txtMoney });
			// ���listview1������
			cornerListView1.setAdapter(adapter1);

			// ����listview2 ֵ
			inflater = LayoutInflater.from(this);
			cornerListView2 = (CornerListView) findViewById(R.id.lv_main_datareport);
			try {
				dataRanges = MainActivityService.getDataSource2(name,
						getApplicationContext());
			} catch (Exception e) {
				Toast.makeText(this, "��ȡ����ʧ��", 0).show();
				e.printStackTrace();
			}
			// ���listview2������
			cornerListView2.setAdapter(new MyAdapter());

			
			// listview1ѡ��ĵ���¼� �����ܶ� ֧���ܶ� Ԥ�����
			cornerListView1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					switch (arg2) {
					case 0:
						TotalIntoData();
						break;
					case 1:
						TotalOutData();
						break;
					case 2:
						TotalAllData();
						break;
					}

				}
			});

			// listview2ѡ��ĵ���¼�      һ����
			cornerListView2.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					switch (arg2) {
					case 0:
						TodayData();
						break;
					case 1:
						MonthData();
						break;
					case 2:
						YearData();
						break;
					}
				}
			});

			mEditTextName = (EditText) this.findViewById(R.id.et_main_username);
			mEditTextName.setText(name);
			mEditTextPwd1 = (EditText) this.findViewById(R.id.et_main_new_pwd);
			mEditTextPwd2 = (EditText) this
					.findViewById(R.id.et_main_confirm_pwd);
			mButtonOK = (Button) this.findViewById(R.id.bt_main_ok);
			mButtonCancel = (Button) this.findViewById(R.id.bt_main_cancel);
			mButtonAddNodes = (Button) this.findViewById(R.id.bt_main_addnotes);
			mButtonOK.setOnClickListener(this);
			mButtonCancel.setOnClickListener(this);
			mButtonAddNodes.setOnClickListener(this);

			ll_right = (LinearLayout) findViewById(R.id.layout_right);// �����Ĳ˵�
			ll_left = (LinearLayout) findViewById(R.id.layout_left);
			ll_right.setOnTouchListener(this);
			mGestureDetector = new GestureDetector(this);
			mGestureDetector.setIsLongpressEnabled(false);// ���ó�������
			getMaxX();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_main_addnotes:
			intent = new Intent(this, AddNodes.class);
			intent.putExtra("name", name);
			// ��ֵ �ʻ���
			startActivity(intent);
			break;
		case R.id.bt_main_ok:
			name = mEditTextName.getText().toString();
			pwd1 = mEditTextPwd1.getText().toString().trim();
			pwd2 = mEditTextPwd2.getText().toString().trim();

			if (name.equals("")) {
				Toast.makeText(getApplicationContext(), "�˻�������Ϊ�գ�",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (pwd1.equals("")) {
				Toast.makeText(getApplicationContext(), "���벻��Ϊ�գ�",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (!pwd1.equals(pwd2)) {
				Toast.makeText(getApplicationContext(), "ȷ�����벻ͬ��",
						Toast.LENGTH_SHORT).show();
				break;
			}
			persondbdao = new PersonDBdao(getApplicationContext());
			persondbdao.update(name, name, pwd2);
			Toast.makeText(getApplicationContext(), "�޸ĳɹ���", Toast.LENGTH_SHORT)
					.show();

			finish();
			break;
		case R.id.bt_main_cancel:
			persondbdao = new PersonDBdao(getApplicationContext());
			persondbdao.updateLoginCancel(name);
			intent = new Intent(this, Login.class);
			startActivity(intent);
			finish();
			break;
		}
	}

	// ��ת�������˵�
	public void TotalIntoData() {
		intent = new Intent(this, SpecificData.class);
		intent.putExtra("name", name);
		intent.putExtra("title", "�����˵�");
		startActivity(intent);
	}

	// ��ת��֧���˵�
	public void TotalOutData() {
		intent = new Intent(this, SpecificData.class);
		intent.putExtra("name", name);
		intent.putExtra("title", "֧���˵�");
		startActivity(intent);
	}

	// ��ת����ϸ�˵�
	public void TotalAllData() {
		intent = new Intent(this, SpecificData.class);
		intent.putExtra("name", name);
		intent.putExtra("title", "��ϸ�˵�");
		startActivity(intent);
	}

	// ��ת�������˵�
	public void TodayData() {
		intent = new Intent(this, SpecificData.class);
		intent.putExtra("name", name);
		intent.putExtra("title", "�����˵�");
		startActivity(intent);
	}

	// ��ת�������˵�
	public void MonthData() {
		intent = new Intent(this, SpecificData.class);
		intent.putExtra("name", name);
		intent.putExtra("title", "�����˵�");
		startActivity(intent);
	}

	// ��ת�������˵�
	public void YearData() {
		intent = new Intent(this, SpecificData.class);
		intent.putExtra("name", name);
		intent.putExtra("title", "�����˵�");
		startActivity(intent);
	}

	// ����ʱ��
	public String GetTime() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")); // ��ȡ������ʱ��
		int year = c.get(Calendar.YEAR); // ��ȡ��
		int month = c.get(Calendar.MONTH) + 1; // ��ȡ�·ݣ�0��ʾ1�·�
		int day = c.get(Calendar.DAY_OF_MONTH); // ��ȡ��ǰ����
		String time = year + "/" + month + "/" + day;
		return time;
	}

	
	// ���ñ���ͼ����
	public void SetVarlue(float totalInto, float totalOut) {
		// ������֧����������仯����ͼ
		if ((totalInto - totalOut) < 0) {
			varlue1 = 0;
			varlue2 = 1;
		} else if (totalInto == totalOut) {
			varlue1 = 1;
			varlue2 = 1;
		} else {
			varlue1 = (int) totalInto;
			varlue2 = (int) totalOut;
		}
	}

	// listview2������
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dataRanges.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return dataRanges.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.main_listview_datareport,
					null);
			DataRange aboutBillData = dataRanges.get(position);

			TextView tv2_text1 = (TextView) view
					.findViewById(R.id.ls_tv2_txtDataRange);
			TextView tv2_text2 = (TextView) view
					.findViewById(R.id.ls_tv2_txtInto);
			TextView tv2_text3 = (TextView) view
					.findViewById(R.id.ls_tv2_txtOut);
			tv2_text1.setText(aboutBillData.getText1());
			tv2_text2.setText(aboutBillData.getText2());
			tv2_text3.setText(aboutBillData.getText3());

			return view;
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mTextViewTime.setText(GetTime());// ����ʱ��

		totalOut = accountDBdao.fillTotalOut(name);// ��֧��
		totalInto = accountDBdao.fillTotalInto(name);// ������
		SetVarlue(totalInto, totalOut);
		piechart = new PieChart();
		piechart.paintingPieChart(getApplicationContext(), piechar, varlue1,
				varlue2);

		map_list1 = MainActivityService.getDataSource1(totalInto, totalOut);
		// listview1������
		SimpleAdapter adapter1 = new SimpleAdapter(getApplicationContext(),
				map_list1, R.layout.main_listview_calculation, new String[] {
						"txtCalculationName", "txtMoney" }, new int[] {
						R.id.ls_tv_txtCalculationName, R.id.ls_tv_txtMoney });
		// ���listview1������
		cornerListView1.setAdapter(adapter1);

		try {
			dataRanges = MainActivityService.getDataSource2(name,
					getApplicationContext());
		} catch (Exception e) {
			Toast.makeText(this, "��ȡ����ʧ��", 0).show();
			e.printStackTrace();
		}
		// ���listview2������
		cornerListView2.setAdapter(new MyAdapter());
	}

	void getMaxX() {// �õ������������,����layout�Ŀ��

		ViewTreeObserver viewTreeObserver = ll_right.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				if (!hasMeasured) {
					window_width = getWindowManager().getDefaultDisplay()
							.getWidth(); // ��Ļ���
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ll_right
							.getLayoutParams(); // layout����
					layoutParams.width = window_width;
					ll_right.setLayoutParams(layoutParams);
					hasMeasured = true;
					MAX_WIDTH = ll_left.getWidth();// ���layout���
				}
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		mScrollX = 0;
		isScrolling = false;
		return true;// ��֮��Ϊtrue����Ȼ�¼��������´���.
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int currentX = (int) e2.getX();
		int lastX = (int) e1.getX();

		if (isMenuOpen) {

			if (!isScrolling && currentX - lastX >= 0) {

				return false;
			}
		} else {

			if (!isScrolling && currentX - lastX <= 0) {

				return false;

			}
		}

		boolean suduEnough = false;

		if (velocityX > MainActivity.SNAP_VELOCITY
				|| velocityX < -MainActivity.SNAP_VELOCITY) {

			suduEnough = true;

		} else {

			suduEnough = false;

		}

		doCloseScroll(suduEnough);

		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if (isFinish)
			doScrolling(distanceX);
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		if (isFinish) {

			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ll_right
					.getLayoutParams();
			// ���ƶ�
			if (layoutParams.leftMargin >= MAX_WIDTH) {
				new AsynMove().execute(-SPEED);
			} else {
				// ���ƶ�
				new AsynMove().execute(SPEED);
			}
		}
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	public void doScrolling(float distanceX) {
		isScrolling = true;
		mScrollX += distanceX;// distanceX:����Ϊ������Ϊ��
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ll_right
				.getLayoutParams();

		layoutParams.leftMargin -= mScrollX;
		layoutParams.rightMargin += mScrollX;

		if (layoutParams.leftMargin <= 0) {
			isScrolling = false;// �Ϲ�ͷ�˲���Ҫ��ִ��AsynMove��
			layoutParams.leftMargin = 0;
			layoutParams.rightMargin = 0;

		} else if (layoutParams.leftMargin >= MAX_WIDTH) {
			// �Ϲ�ͷ�˲���Ҫ��ִ��AsynMove��
			isScrolling = false;
			layoutParams.leftMargin = MAX_WIDTH;
		}
		ll_right.setLayoutParams(layoutParams);
		ll_left.invalidate();
	}

	public void doCloseScroll(boolean suduEnough) {
		if (isFinish) {

			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ll_right
					.getLayoutParams();

			int tempSpeed = SPEED;

			if (isMenuOpen) {
				tempSpeed = -tempSpeed;
			}

			if (suduEnough
					|| (!isMenuOpen && (layoutParams.leftMargin > window_width / 2))
					|| (isMenuOpen && (layoutParams.leftMargin < window_width / 2))) {

				new AsynMove().execute(tempSpeed);

			} else {

				new AsynMove().execute(-tempSpeed);

			}

		}
	}

	class AsynMove extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			isFinish = false;
			int times = 0;
			if (MAX_WIDTH % Math.abs(params[0]) == 0)// ����
				times = MAX_WIDTH / Math.abs(params[0]);
			else
				times = MAX_WIDTH / Math.abs(params[0]) + 1;// ������

			for (int i = 0; i < times; i++) {
				publishProgress(params[0]);
				try {
					Thread.sleep(Math.abs(params[0]));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			isFinish = true;
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ll_right
					.getLayoutParams();
			if (layoutParams.leftMargin >= MAX_WIDTH) {
				isMenuOpen = true;
			} else {
				isMenuOpen = false;
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ll_right
					.getLayoutParams();
			// ���ƶ�
			if (values[0] > 0) {
				layoutParams.leftMargin = Math.min(layoutParams.leftMargin
						+ values[0], MAX_WIDTH);
				layoutParams.rightMargin = Math.max(layoutParams.rightMargin
						- values[0], -MAX_WIDTH);

			} else {
				// ���ƶ�
				layoutParams.leftMargin = Math.max(layoutParams.leftMargin
						+ values[0], 0);

			}
			ll_right.setLayoutParams(layoutParams);
			ll_left.invalidate();
		}

	}
}
