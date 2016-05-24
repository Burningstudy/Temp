package com.seventh.personalfinance;

import com.seventh.db.Account;
import com.seventh.db.AccountDBdao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MoreAction extends Activity implements OnClickListener {
	private Intent intent = null;// ����һ����ͼ
	AccountDBdao accountDBdao;// ���ݿ�
	private String name;// �˺�
	private String title;// ����
	private String id;// id

	private EditText mEditTextMoney;// ���
	private EditText mEditTextRemark;// ��ע
	private EditText mEditTextTime; // ʱ��
	private Spinner mSpinnerType; // ����
	private Spinner mSpinnerEarnings; // ����
	private Button mButtonUpdate;
	private Button mButtonDelete;
	private Button mButtonCancel;

	private String time;
	private float money;
	private String type;
	private boolean earning;
	private String remark;

	private Account account;

	private static final String[] types = { "�·�װ��", "���ʽ���", "Ͷ��ӯ��", "���н�ͨ",
			"���־ۻ�", "������Ʒ", "ˮ�緿��", "�ɷ��嵥", "��Ʊ����", "����" };
	private static final String[] earnings = { "֧��", "����" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_action);

		intent = this.getIntent();
		name = intent.getStringExtra("name");// ���վ������ݽ��������
		title = intent.getStringExtra("title");// ���վ������ݽ��������
		id = intent.getStringExtra("id");// ���վ������ݽ����������

		mEditTextMoney = (EditText) this.findViewById(R.id.et_moreaction_money);// ���
		mEditTextRemark = (EditText) this
				.findViewById(R.id.et_moreaction_remark);// ��ע
		mEditTextTime = (EditText) this.findViewById(R.id.et_moreaction_time);// ʱ��

		// ����
		mSpinnerType = (Spinner) this.findViewById(R.id.sp_moreaction_type);
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
				.findViewById(R.id.sp_moreaction_earnings);
		ArrayAdapter<String> adapterEarnings = new ArrayAdapter<String>(this,
				R.layout.addnodes_earnings, earnings);
		adapterEarnings
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerEarnings.setAdapter(adapterEarnings);

		mButtonUpdate = (Button) this.findViewById(R.id.bt_moreaction_update);
		mButtonDelete = (Button) this.findViewById(R.id.bt_moreaction_delete);
		mButtonCancel = (Button) this.findViewById(R.id.bt_moreaction_cancel);
		mButtonUpdate.setOnClickListener(this);
		mButtonDelete.setOnClickListener(this);
		mButtonCancel.setOnClickListener(this);

		// ���ó�ʼ��ʾ��ֵ
		accountDBdao = new AccountDBdao(getApplicationContext());
		account = accountDBdao.findInfoById(id);
		mEditTextMoney.setText(account.getMoney() + "");// ���
		mEditTextRemark.setText(account.getRemark());// ��ע
		mEditTextTime.setText(account.getTime()); // ʱ��
		// "��", "ʳ", "ס", "��", "����", "������Ʒ","ˮ��", "����"
		if (account.getType().equals("�·�װ��")) {
			mSpinnerType.setSelection(0);
		} else if (account.getType().equals("���ʽ���")) {
			mSpinnerType.setSelection(1);
		} else if (account.getType().equals("Ͷ��ӯ��")) {
			mSpinnerType.setSelection(2);
		} else if (account.getType().equals("���н�ͨ")) {
			mSpinnerType.setSelection(3);
		} else if (account.getType().equals("���־ۻ�")) {
			mSpinnerType.setSelection(4);
		} else if (account.getType().equals("������Ʒ")) {
			mSpinnerType.setSelection(5);
		} else if (account.getType().equals("ˮ�緿��")) {
			mSpinnerType.setSelection(6);
		} else if (account.getType().equals("�ɷ��嵥")) {
			mSpinnerType.setSelection(7);
		} else if (account.getType().equals("��Ʊ����")) {
			mSpinnerType.setSelection(8);
		} 	
		else {
			mSpinnerType.setSelection(9);
		}
		// ����
		if (account.isEarnings()) {
			mSpinnerEarnings.setSelection(1);
		} else {
			mSpinnerEarnings.setSelection(0);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_moreaction_update:// �޸İ�ť
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
			accountDBdao.update(id, time, money, type, earning, remark);
			Toast.makeText(getApplicationContext(), "�� �� ��  �� ��", 0).show();
			break;
		case R.id.bt_moreaction_delete:// ɾ����ť
			accountDBdao = new AccountDBdao(getApplicationContext());
			accountDBdao.delete(id);
			Toast.makeText(getApplicationContext(), "ɾ ��  �� �� ��", 0).show();
			finish();
			break;
		case R.id.bt_moreaction_cancel:// ȡ����ť
			Intent intent = new Intent(this, SpecificData.class);
			intent.putExtra("name", name);
			intent.putExtra("title", title);
			// ��ֵ �ʻ���
			startActivity(intent);
			finish();
			break;
		}

	}

}
