package com.seventh.view;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class PieChart {
	/**
	 * ����״ͼ
	 */
	public void paintingPieChart(Context context, LinearLayout piechar,int varlue1,int varlue2) {
		// ��״ͼÿ�������ռ�ı���
		double[] values = { varlue1, varlue2};
		// ��״ͼÿ��������ɫ
		int[] colors = { Color.GREEN, Color.RED};
		// ����״ͼ
		GraphicalView mPieChartView;
		CategorySeries series = new CategorySeries("title");
		
		// ֧����ռ�ı���Ϊvalues[0]��ֵ
		series.add("����", values[0]);
		// ��������ռ�ı���Ϊvalues[1]��ֵ
		series.add("֧��", values[1]);

		DefaultRenderer renderer = new DefaultRenderer();
		// ���ñ�����ɫ
		renderer.setBackgroundColor(0);
		// �����Ƿ�ʹ�ñ�����ɫ
		renderer.setApplyBackgroundColor(true);
		// ���� ���ǩ�����С
		renderer.setLabelsTextSize(8);
		// ���� ���ǩ������ɫ
		renderer.setLabelsColor(Color.BLACK);
		// ͼ�������С
		renderer.setLegendTextSize(8);
		// ͼ�� 4�ߵı߾�
		renderer.setMargins(new int[] { 0, 20, 0, 0 }); 
		
		renderer.setChartTitleTextSize(10);//����ͼ���������ִ�С
		renderer.setChartTitle("֧���������");//����ͼ��ı���  Ĭ���Ǿ��ж�����ʾ
		// Ϊ��״ͼ������ɫ
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		// ���ϱ�״ͼ����������mPieChartView
		mPieChartView = ChartFactory.getPieChartView(context, series, renderer);
		//ȥ��piechar������view
		piechar.removeAllViews();
		// ��mPieChartView�ӵ�piechar��ͼ��	
	}
}
