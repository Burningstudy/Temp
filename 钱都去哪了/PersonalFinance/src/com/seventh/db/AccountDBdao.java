package com.seventh.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class AccountDBdao {
	private Context context;
	MyDBOpenHelper dbOpenHelper;

	public AccountDBdao(Context context) {
		this.context = context;
		dbOpenHelper = new MyDBOpenHelper(context);
	}

	/**
	 * ���һ����¼
	 */
	public void add(String time, float money, String type, boolean earnings,
			String remark, String name) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		if (db.isOpen()) {
			// db.execSQL("insert into account (time,money,type,earnings,remark,name) values (?,?,?,?,?,?)",new
			// Object[]{time,money,type,earnings,remark,name});
			ContentValues values = new ContentValues();
			values.put("time", time);// ����ʱ��
			values.put("money", money);// ��Ǯ
			values.put("type", type);// ����
			values.put("earnings", earnings);// �Ƿ�����
			values.put("remark", remark);// ��ע
			values.put("name", name);// �û���
			db.insert("account", null, values); // ��ƴsql�����ɵ���ӵĲ���
			db.close();
		}

	}

	/**
	 * ɾ��һ����¼
	 */
	public void delete(String accountid) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete("account", "accountid=?", new String[] { accountid });
			db.close();
		}
	}

	/**
	 * ���ݿ�ĸ��Ĳ���
	 */
	public void update(String accountid, String time, float money, String type,
			boolean earnings, String remark) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values = new ContentValues();
			values.put("time", time);// ����ʱ��
			values.put("money", money);// ��Ǯ
			values.put("type", type);// ����
			values.put("earnings", earnings);// �Ƿ�����
			values.put("remark", remark);// ��ע
			db.update("account", values, "accountid=?",
					new String[] { accountid });
			db.close();
		}
	}

	/**
	 * ���ݿ�Ĳ�ѯ���� �ж����޸�����
	 */
	public boolean find(String name) {
		boolean result = false;
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			// select * from person
			Cursor cursor = db.query("account", null, "name=?",
					new String[] { name }, null, null, null);
			if (cursor.moveToFirst()) {
				result = true;
			}
			cursor.close();
			db.close();
		}
		return result;

	}

	/**
	 * ��ѯ������Ϣ
	 */
	public List<Account> findAll() {
		List<Account> accounts = null;
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.query("account", null, null, null, null, null,
					null);
			accounts = new ArrayList<Account>();
			while (cursor.moveToNext()) {
				Account account = new Account();
				int id = cursor.getInt(cursor.getColumnIndex("accountid"));
				account.setId(id);
				String name = cursor.getString(cursor.getColumnIndex("name"));
				account.setName(name);
				float money = Float.parseFloat(cursor.getString(cursor
						.getColumnIndex("money")));
				account.setMoney(money);
				String time = cursor.getString(cursor.getColumnIndex("time"));
				account.setTime(time);
				String type = cursor.getString(cursor.getColumnIndex("type"));
				account.setType(type);
				long earnings = cursor.getLong(cursor
						.getColumnIndex("earnings"));
				if (earnings == 0) {
					account.setEarnings(false);
				} else {
					account.setEarnings(true);
				}
				String remark = cursor.getString(cursor
						.getColumnIndex("remark"));
				account.setRemark(remark);
				accounts.add(account);
			}
			cursor.close();
			db.close();
		}
		return accounts;
	}

	/**
	 * �����û�����ѯ������Ϣ
	 */
	public List<Account> findAllByName(String userName) {
		List<Account> accounts = null;
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from account where name=?",
					new String[] { userName });
			accounts = new ArrayList<Account>();
			while (cursor.moveToNext()) {
				Account account = new Account();
				int id = cursor.getInt(cursor.getColumnIndex("accountid"));
				account.setId(id);
				String name = cursor.getString(cursor.getColumnIndex("name"));
				account.setName(name);
				float money = Float.parseFloat(cursor.getString(cursor
						.getColumnIndex("money")));
				account.setMoney(money);
				String time = cursor.getString(cursor.getColumnIndex("time"));
				account.setTime(time);
				String type = cursor.getString(cursor.getColumnIndex("type"));
				account.setType(type);
				long earnings = cursor.getLong(cursor
						.getColumnIndex("earnings"));
				if (earnings == 0) {
					account.setEarnings(false);
				} else {
					account.setEarnings(true);
				}
				String remark = cursor.getString(cursor
						.getColumnIndex("remark"));
				account.setRemark(remark);
				accounts.add(account);
			}
			cursor.close();
			db.close();
		}
		return accounts;
	}

	/**
	 * �����û�����ѯ����������Ϣ
	 */
	public List<Account> findTotalIntoByName(String userName) {
		List<Account> accounts = null;
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"select * from account where earnings=1 and name=?",
					new String[] { userName });
			accounts = new ArrayList<Account>();
			while (cursor.moveToNext()) {
				Account account = new Account();
				int id = cursor.getInt(cursor.getColumnIndex("accountid"));
				account.setId(id);
				String name = cursor.getString(cursor.getColumnIndex("name"));
				account.setName(name);
				float money = Float.parseFloat(cursor.getString(cursor
						.getColumnIndex("money")));
				account.setMoney(money);
				String time = cursor.getString(cursor.getColumnIndex("time"));
				account.setTime(time);
				String type = cursor.getString(cursor.getColumnIndex("type"));
				account.setType(type);
				long earnings = cursor.getLong(cursor
						.getColumnIndex("earnings"));
				if (earnings == 0) {
					account.setEarnings(false);
				} else {
					account.setEarnings(true);
				}
				String remark = cursor.getString(cursor
						.getColumnIndex("remark"));
				account.setRemark(remark);
				accounts.add(account);
			}
			cursor.close();
			db.close();
		}
		return accounts;
	}

	/**
	 * �����û�����ѯ����֧����Ϣ
	 */
	public List<Account> findTotalOutByName(String userName) {
		List<Account> accounts = null;
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"select * from account where earnings=0 and name=?",
					new String[] { userName });
			accounts = new ArrayList<Account>();
			while (cursor.moveToNext()) {
				Account account = new Account();
				int id = cursor.getInt(cursor.getColumnIndex("accountid"));
				account.setId(id);
				String name = cursor.getString(cursor.getColumnIndex("name"));
				account.setName(name);
				float money = Float.parseFloat(cursor.getString(cursor
						.getColumnIndex("money")));
				account.setMoney(money);
				String time = cursor.getString(cursor.getColumnIndex("time"));
				account.setTime(time);
				String type = cursor.getString(cursor.getColumnIndex("type"));
				account.setType(type);
				long earnings = cursor.getLong(cursor
						.getColumnIndex("earnings"));
				if (earnings == 0) {
					account.setEarnings(false);
				} else {
					account.setEarnings(true);
				}
				String remark = cursor.getString(cursor
						.getColumnIndex("remark"));
				account.setRemark(remark);
				accounts.add(account);
			}
			cursor.close();
			db.close();
		}
		return accounts;
	}

	/**
	 * �����û�����ѯûʱ��ε��˵���Ϣ
	 */
	public List<Account> findSomeTimeByName(String userName, String sometime) {
		List<Account> accounts = null;
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"select * from account where name=? and time like ?",
					new String[] { userName, sometime });
			accounts = new ArrayList<Account>();
			while (cursor.moveToNext()) {
				Account account = new Account();
				int id = cursor.getInt(cursor.getColumnIndex("accountid"));
				account.setId(id);
				String name = cursor.getString(cursor.getColumnIndex("name"));
				account.setName(name);
				float money = Float.parseFloat(cursor.getString(cursor
						.getColumnIndex("money")));
				account.setMoney(money);
				String time = cursor.getString(cursor.getColumnIndex("time"));
				account.setTime(time);
				String type = cursor.getString(cursor.getColumnIndex("type"));
				account.setType(type);
				long earnings = cursor.getLong(cursor
						.getColumnIndex("earnings"));
				if (earnings == 0) {
					account.setEarnings(false);
				} else {
					account.setEarnings(true);
				}
				String remark = cursor.getString(cursor
						.getColumnIndex("remark"));
				account.setRemark(remark);
				accounts.add(account);
			}
			cursor.close();
			db.close();
		}
		return accounts;
	}

	/**
	 * ��ѯ������Ϣ
	 */
	public Cursor findAllbyCursor() {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			/*
			 * Cursor cursor = db.query("person", null, null, null, null, null,
			 * null);
			 */
			Cursor cursor = db
					.rawQuery(
							"select accountid  as _id ,name ,time ,money ,type ,earnings ,remark  from account",
							null);

			return cursor;
			// ע���� һ����Ҫ�����ݿ� �ر���
		}
		return null;
	}

	/**
	 * ��ѯ����������
	 */
	public float fillTotalInto(String name) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db
					.rawQuery(
							"select sum(money) as sumvalue from account where earnings=1 and name=?",
							new String[] { name });
			while (cursor.moveToNext()) {
				return cursor.getFloat(cursor.getColumnIndex("sumvalue"));
			}
			cursor.close();
			db.close();
		}
		return 0;
	}

	/**
	 * ��ѯ������֧��
	 */
	public float fillTotalOut(String name) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db
					.rawQuery(
							"select sum(money) as sumvalue from account where earnings=0 and name=?",
							new String[] { name });
			while (cursor.moveToNext()) {
				return cursor.getFloat(cursor.getColumnIndex("sumvalue"));
			}
			cursor.close();
			db.close();
		}
		return 0;
	}

	/**
	 * ��ѯ��������֧��
	 */
	public float fillTodayOut(String name, String time) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db
					.rawQuery(
							"select sum(money) as sumvalue from account where earnings=0 and name=? and time=?",
							new String[] { name, time });
			while (cursor.moveToNext()) {
				return cursor.getFloat(cursor.getColumnIndex("sumvalue"));
			}
			cursor.close();
			db.close();
		}
		return 0;
	}

	/**
	 * ��ѯ������������
	 */
	public float fillTodayInto(String name, String time) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db
					.rawQuery(
							"select sum(money) as sumvalue from account where earnings=1 and name=? and time=?",
							new String[] { name, time });
			while (cursor.moveToNext()) {
				return cursor.getFloat(cursor.getColumnIndex("sumvalue"));
			}
			cursor.close();
			db.close();
		}
		return 0;
	}

	/**
	 * ��ѯ��������֧��
	 */
	public float fillMonthOut(String name, String time) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db
					.rawQuery(
							"select sum(money) as sumvalue from account where earnings=0 and name=? and time like ?",
							new String[] { name, time });
			while (cursor.moveToNext()) {
				return cursor.getFloat(cursor.getColumnIndex("sumvalue"));
			}
			cursor.close();
			db.close();
		}
		return 0;
	}

	/**
	 * ��ѯ������������
	 */
	public float fillMonthInto(String name, String time) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db
					.rawQuery(
							"select sum(money) as sumvalue from account where earnings=1 and name=? and time like ?",
							new String[] { name, time });
			while (cursor.moveToNext()) {
				return cursor.getFloat(cursor.getColumnIndex("sumvalue"));
			}
			cursor.close();
			db.close();
		}
		return 0;
	}

	/**
	 * ��ѯ��������֧��
	 */
	public float fillYearOut(String name, String time) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db
					.rawQuery(
							"select sum(money) as sumvalue from account where earnings=0 and name=? and time like ?",
							new String[] { name, time });
			while (cursor.moveToNext()) {
				return cursor.getFloat(cursor.getColumnIndex("sumvalue"));
			}
			cursor.close();
			db.close();
		}
		return 0;
	}

	/**
	 * ��ѯ������������
	 */
	public float fillYearInto(String name, String time) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db
					.rawQuery(
							"select sum(money) as sumvalue from account where earnings=1 and name=? and time like ?",
							new String[] { name, time });
			while (cursor.moveToNext()) {
				return cursor.getFloat(cursor.getColumnIndex("sumvalue"));
			}
			cursor.close();
			db.close();
		}
		return 0;
	}
	
	/**
	 * ����id��ѯ��¼��Ϣ
	 */
	public Account findInfoById(String accountid) {
		Account account = null;
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from account where accountid=?",
					new String[] { accountid });
			while (cursor.moveToNext()) {
				account = new Account();
				int id = cursor.getInt(cursor.getColumnIndex("accountid"));
				account.setId(id);
				String name = cursor.getString(cursor.getColumnIndex("name"));
				account.setName(name);
				float money = Float.parseFloat(cursor.getString(cursor
						.getColumnIndex("money")));
				account.setMoney(money);
				String time = cursor.getString(cursor.getColumnIndex("time"));
				account.setTime(time);
				String type = cursor.getString(cursor.getColumnIndex("type"));
				account.setType(type);
				long earnings = cursor.getLong(cursor
						.getColumnIndex("earnings"));
				if (earnings == 0) {
					account.setEarnings(false);
				} else {
					account.setEarnings(true);
				}
				String remark = cursor.getString(cursor
						.getColumnIndex("remark"));
				account.setRemark(remark);
			}
			cursor.close();
			db.close();
		}
		return account;
	}
}
