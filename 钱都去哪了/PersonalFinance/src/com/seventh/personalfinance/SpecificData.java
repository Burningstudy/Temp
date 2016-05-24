package com.seventh.personalfinance;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.seventh.db.Account;
import com.seventh.db.AccountDBdao;
import com.seventh.view.CornerListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class SpecificData extends Activity {
	private Intent intent = null;// ����һ����ͼ
	private String name;// �˺�
	private String title;// ����
	AccountDBdao accountDBdao;// ���ݿ�

	private TextView mTextViewTime;// ����

	private String time1;
	private String time2;
	private String time3;
	
	private CornerListView cornerListView = null;// ���ݱ���
	private List<Account> accounts;// �˵�����
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_specific_data);

		intent = this.getIntent();
		name = intent.getStringExtra("name");// ���������������
		title = intent.getStringExtra("title");// ���������������

		// ���ñ���
		mTextViewTime = (TextView) this
				.findViewById(R.id.tv_specific_data_txtDataRange);
		mTextViewTime.setText(title);

		accountDBdao = new AccountDBdao(getApplicationContext());

		// ʱ��
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")); // ��ȡ������ʱ��
		int year = c.get(Calendar.YEAR); // ��ȡ��
		int month = c.get(Calendar.MONTH) + 1; // ��ȡ�·ݣ�0��ʾ1�·�
		int day = c.get(Calendar.DAY_OF_MONTH); // ��ȡ��ǰ����
		time1 = year + "/" + month + "/" + day;
		time2 = year + "/" + month + "%";
		time3 = year + "%";

		// ����listview ֵ
		inflater = LayoutInflater.from(this);
		cornerListView = (CornerListView) findViewById(R.id.lv_specific_data_list);
		GetData();

		// ���listview������
		cornerListView.setAdapter(new MyAdapter());
		// listviewѡ��ĵ���¼�
		cornerListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Account account = accounts.get(arg2);
				GoMoreAction(account.getId(), name);

			}
		});
	}

	private void GoMoreAction(int id, String name) {
		// TODO Auto-generated method stub
		intent = new Intent(this, MoreAction.class);
		intent.putExtra("name", name);
		intent.putExtra("id", id + "");
		intent.putExtra("title", title);
		startActivity(intent);
	}

	// ��ȡ����
	private void GetData() {
		try {
			if (title.equals("�����˵�")) {
				accounts = accountDBdao.findTotalIntoByName(name);
			} else if (title.equals("֧���˵�")) {
				accounts = accountDBdao.findTotalOutByName(name);
			} else if (title.equals("��ϸ�˵�")) {
				accounts = accountDBdao.findAllByName(name);
			} else if (title.equals("�����˵�")) {
				accounts = accountDBdao.findSomeTimeByName(name, time1);
			} else if (title.equals("�����˵�")) {
				accounts = accountDBdao.findSomeTimeByName(name, time2);
			} else if (title.equals("�����˵�")) {
				accounts = accountDBdao.findSomeTimeByName(name, time3);
			}
		} catch (Exception e) {
			Toast.makeText(this, "��ȡ����ʧ��", 0).show();
			e.printStackTrace();
		}
	}

	// listview������
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return accounts.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub

			return accounts.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.specific_data_data, null);
			Account account = accounts.get(position);
			TextView tv_text1 = (TextView) view
					.findViewById(R.id.ls_sp_tv_time);
			TextView tv_text2 = (TextView) view
					.findViewById(R.id.ls_sp_tv_type);
			TextView tv_text3 = (TextView) view
					.findViewById(R.id.ls_sp_tv_money);
			tv_text1.setText("ʱ�� :" + account.getTime());
			tv_text2.setText("����: " + account.getType());
			tv_text3.setText("���:" + account.getMoney() + "");
			return view;
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		GetData();

		// ���listview������
		cornerListView.setAdapter(new MyAdapter());
	}

}
