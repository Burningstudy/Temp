package com.seventh.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import android.content.Context;

import com.seventh.db.AccountDBdao;

public class MainActivityService {

	/**
	 * �����б�1����
	 */
	public static List<Map<String, String>> getDataSource1(float totalInto,float totalOut) {
		List<Map<String, String>> map_list1 = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();

		String textRevenue = "��" + totalInto;
		map.put("txtCalculationName", "�����ܶ�:");
		map.put("txtMoney", textRevenue);
		map_list1.add(map);

		String textExpenditure = "��" + totalOut;
		map = new HashMap<String, String>();
		map.put("txtCalculationName", "֧���ܶ�:");
		map.put("txtMoney", textExpenditure);
		map_list1.add(map);

		String textBalance = "��" + (totalInto - totalOut);
		map = new HashMap<String, String>();
		map.put("txtCalculationName", "Ԥ�����:");
		map.put("txtMoney", textBalance);
		map_list1.add(map);

		return map_list1;
	}

	/**
	 * �����˵�һ��������
	 */
	public static List<DataRange> getDataSource2(String name,Context context) {
		AccountDBdao accountDBdao;
		accountDBdao = new AccountDBdao(context);
		List<DataRange> dataRanges = new ArrayList<DataRange>();
		DataRange dataRange = null;
		dataRange = new DataRange();

		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")); // ��ȡ������ʱ��
		int year = c.get(Calendar.YEAR); // ��ȡ��
		int month = c.get(Calendar.MONTH) + 1; // ��ȡ�·ݣ�0��ʾ1�·�
		int day = c.get(Calendar.DAY_OF_MONTH); // ��ȡ��ǰ����
		String time1 = year + "/" + month + "/" + day;
		String time2 = year + "/" + month + "%";
		String time3 = year + "%";

		dataRange.setText1("������Ŀһ����");
		accountDBdao.fillTodayInto(name, time1);
		dataRange.setText2("���룤" + accountDBdao.fillTodayInto(name, time1));
		dataRange.setText3("֧�� ��" + accountDBdao.fillTodayOut(name, time1));
		dataRanges.add(dataRange);


		return dataRanges;
	}	
}