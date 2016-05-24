package com.seventh.personalfinance;


import com.seventh.db.Person;
import com.seventh.db.PersonDBdao;

import android.os.Bundle;
import android.app.Activity;

import android.content.Intent;

import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {
	PersonDBdao persondbdao;
	private Person person;
	private EditText mEditTextName;// �˺�
	private EditText mEditTextPwd;// ����
	private Button mButtonOK;
	private Button mButtonCancel;
	private TextView mTextViewRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ������
		setContentView(R.layout.activity_login);
		
		LoginOk();
		
		mEditTextName = (EditText) this.findViewById(R.id.et_login_name);
		mEditTextPwd = (EditText) this.findViewById						(R.id.et_login_password);
		mButtonOK = (Button) this.findViewById(R.id.bt_login_ok);
		mButtonCancel = (Button) this.findViewById(R.id.bt_login_cancel);
		mTextViewRegister = (TextView) this
				.findViewById(R.id.tv_login_register_link);
		mButtonOK.setOnClickListener(this);
		mButtonCancel.setOnClickListener(this);
		mTextViewRegister.setOnClickListener(this);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	private void LoginOk() {
		persondbdao = new PersonDBdao(getApplicationContext());
		person = persondbdao.findLoginOk();
		if (person == null) {

		} else {
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("name", person.getName());
			// ��ֵ �ʻ���
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// ��ť��Ӧ�ĵ���¼�
	// ���� v ����ľ��ǵ�ǰ���������Ŀ��Ӧ��view����
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_login_ok:// ��¼��ť
			// ��Ӧ��ť�ĵ���¼�)
			if (mEditTextName.getText().toString().trim().equals("")) 	{
				Toast.makeText(getApplicationContext(), "�˻�������			Ϊ�գ�",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (mEditTextPwd.getText().toString().equals("")) {
				Toast.makeText(getApplicationContext(), "���벻��Ϊ			�գ�",
						Toast.LENGTH_SHORT).show();
				break;
			}

			persondbdao = new PersonDBdao(getApplicationContext());
			boolean result = persondbdao.find(mEditTextName.getText()
					.toString());

			if (result) {
				result = persondbdao.findLogin(mEditTextName.getText	()
						.toString(), mEditTextPwd.getText	().toString());
				if (result) {
					persondbdao.updateLoginOK	(mEditTextName.getText()
							.toString());
					Intent intent = new Intent(this, 	MainActivity.class);
					// Intent intent = new Intent();
					// intent.setClassName	("com.seventh.personalfinance",
					// 	"com.seventh.personalfinance.MainActivity");

					intent.putExtra("name", mEditTextName.getText	().toString()
							.trim());
					// ��ֵ �ʻ���
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(), "����	����", 0).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "�����ڸ���	��", 0).show();
			}

			break;
		case R.id.bt_login_cancel:// ��¼ȡ��
			System.exit(0);
			break;
		case R.id.tv_login_register_link:
			Intent intent = new Intent(this, Registration.class);
			startActivity(intent);
			break;
		}

	}
}
