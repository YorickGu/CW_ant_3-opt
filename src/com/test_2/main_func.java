package com.test_2;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.ArrayList;

import javax.swing.JFrame;

public class main_func {

	Pattern_Display pd = null;
	ACOtest at = null;
	static double[][] min_locate = new double[init.N][2];
	static int[] min_route = new int[init.N + 20];
	static double min_dis = Integer.MAX_VALUE;
	public ArrayList<Double> array = new ArrayList<Double>();
	static int[][] result_route;
	static double globalBestLength;

	public void main_init() {

		for (int i = 1; i < init.N; i = i + 5) {
			CWTSP cwtsp = new CWTSP();
			cwtsp.CWTSP_init(i); // CW��Լ�㷨
			cal_dis(cwtsp);
			ACOmain acomain = new ACOmain();
			acomain.ant_main();
			result_route = acomain.getresult();
			globalBestLength = acomain.getglobalBestLength();
			new main_func().acopaint(i);
			// ACOmain acomain = new ACOmain();
			// rounum=acomain.ant_main();
			// array.add(rounum);
			// cal_distance(cwtsp);
		}
	}

	// ͨ����ÿһ�����е���ΪCW��Լֵ�㷨�ĳ�ʼ�㣬�������̵�·��
	public void cal_distance(CWTSP cwtsp) {
		double distance = 0;
		for (int i = 0; i < cwtsp.ans.length - 1; i++) {
			distance = distance + sqrt(pow((init.locate[cwtsp.ans[i]][0]) - (init.locate[cwtsp.ans[i + 1]][0]), 2)
					+ pow((init.locate[cwtsp.ans[i]][1]) - (init.locate[cwtsp.ans[i + 1]][1]), 2));
		}
		if (distance < min_dis) {

			min_route = cwtsp.ans.clone();
			// ���ڶ�ά�����clone��������Ҫ��ÿһ�е���clone������ÿһ�п�����һ��һά����
			// ��Ȼ�ͻ��൱�����ã��������齫������һ����ַ
			for (int i = 0; i < init.N; i++) {
				min_locate[i] = init.locate[i].clone();
			}
			min_dis = distance;
		}
		System.out.println(min_dis);
		// System.out.println(min_route[1]);
		// System.out.println("min_locate"+min_locate[0][0]+" "+min_locate[0][1]+"
		// "+min_locate[1][0]+" "+min_locate[1][1]);
	}

	public void cal_dis(CWTSP cwtsp) {
		double distance = 0;
		for (int i = 0; i < cwtsp.ans.length - 1; i++) {
			distance = distance + sqrt(pow((init.locate[cwtsp.ans[i]][0]) - (init.locate[cwtsp.ans[i + 1]][0]), 2)
					+ pow((init.locate[cwtsp.ans[i]][1]) - (init.locate[cwtsp.ans[i + 1]][1]), 2));
		}
		min_dis = distance;
		for (int i = 0; i < init.N; i++) {
			min_locate[i] = init.locate[i].clone();
		}
	}
 
	// private void main_paint() {
	// pd = new Pattern_Display(min_locate,min_route);
	// this.add(pd);
	// this.setSize(1366, 730);
	// this.setLocationRelativeTo(null);
	//// this.setExtendedState(JFrame.MAXIMIZED_BOTH); //���
	//// this.setResizable(false); //���ܸı��С
	//// this.setUndecorated(true);
	// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// this.setVisible(true);
	// }

	private void acopaint(int i) {
		JFrame jf = new JFrame();
		at = new ACOtest(result_route, globalBestLength, i, jf);

		jf.add(at);
		jf.setSize(1366, 730);
		jf.setTitle(i + "");
		// this.setBackground(Color.WHITE);
		// this.getContentPane().setVisible(false);
		// jf.getContentPane().setBackground(new Color(255,255,255));
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(false);
		at.init(globalBestLength);

	}

	public static void main(String[] args) {
		new main_func().main_init();

	}

}
