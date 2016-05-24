package com.seventh.personalfinance;

import java.util.Calendar;
import java.util.TimeZone;

import com.seventh.db.AccountDBdao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNodes extends Activity implements OnClickListener {
	private String name;// �˺�
	AccountDBdao accountDBdao;

	private EditText mEditTextMoney;// ���
	private EditText mEditTextRemark;// ��ע
	private EditText mEditTextTime; // ʱ��
	private Spinner mSpinnerType; // ����
	private Spinner mSpinnerEarnings; // ����
	private Button mButtonAdd;
	private Button mButtonCancel;

	private String time;
	private float money;
	private String type;
	private boolean earning;
	private String remark;

	private static final String[] types = { "�·�װ��", "���ʽ���", "Ͷ��ӯ��", "���н�ͨ",
			"���־ۻ�", "������Ʒ", "ˮ�緿��", "�ɷ��嵥", "��Ʊ����", "����" };
	private static final String[] earnings = { "֧��", "����" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addnodes);

		Intent intent = this.getIntent();
		name = intent.getStringExtra("name");// ���յ�¼���������

		mEditTextMoney = (EditText) this.findViewById(R.id.et_addnodes_money);// ���
		mEditTextRemark = (EditText) this.findViewById(R.id.et_addnodes_remark);// ��ע
		mEditTextTime = (EditText) this.findViewById(R.id.et_addnodes_time);// ʱ��

		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")); // ��ȡ������ʱ��
		int year = c.get(Calendar.YEAR); // ��ȡ��
		int month = c.get(Calendar.MONTH) + 1; // ��ȡ�·ݣ�0��ʾ1�·�
		int day = c.get(Calendar.DAY_OF_MONTH); // ��ȡ��ǰ����
		time = year + "/" + month + "/" + day;// ��ȡϵͳ��ǰʱ��
		mEditTextTime.setText(time);// ����ʱ��

		// ����
		mSpinnerType = (Spinner) this.findViewById(R.id.sp_addnodes_type);
		// ����ѡ������mSpinnerType��������
		ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this,
				R.layout.addnodes_earnings, types);
		// ���������б�ķ��
		adapterType
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapterType ��ӵ�mSpinnerType��
		mSpinnerType.setAdapter(adapterType);

		// ����
		mSpinnerEarnings = (Spinner) this
				.findViewById(R.id.sp_addnodes_earnings);
		ArrayAdapter<String> adapterEarnings = new ArrayAdapter<String>(this,
				R.layout.addnodes_earnings, earnings);
		adapterEarnings
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerEarnings.setAdapter(adapterEarnings);

		mButtonAdd = (Button) this.findViewById(R.id.bt_addnodes_add);
		mButtonCancel = (Button) this.findViewById(R.id.bt_addnodes_cancel);
		mButtonAdd.setOnClickListener(this);
		mButtonCancel.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_addnodes_add:// ��Ӱ�ť
			if (mEditTextMoney.getText().toString().trim().equals("")) {
				Toast.makeText(getApplicationContext(), "����Ϊ�գ�", 0).show();
				break;
			} else {
				money = Float.parseFloat(mEditTextMoney.getText().toString()
						.trim());
			}
			time = mEditTextTime.getText().toString().trim();
			type = mSpinnerType.getSelectedItem().toString();
			if (mSpinnerEarnings.getSelectedItem().toString().equals("����")) {
				earning = true;
			} else {
				earning = false;
			}
			remark = mEditTextRemark.getText().toString().trim();

			accountDBdao = new AccountDBdao(getApplicationContext());
			accountDBdao.add(time, money, type, earning, remark, name);
			Toast.makeText(getApplicationContext(), "�� �� �� �� �� Ŀ �� �� ��", 0)
					.show();
			break;
		case R.id.bt_addnodes_cancel:// ȡ����ť
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("name", name);
			// ��ֵ �ʻ���
			startActivity(intent);
			break;

		}

	}

}
