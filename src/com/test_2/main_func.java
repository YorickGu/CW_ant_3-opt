package com.test_2;

import javax.swing.JFrame;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.ArrayList;

public class main_func extends JFrame {
	
	Pattern_Display pd = null;
	ACOtest at = null;
	static double[][] min_locate = new double[init.N][2]; 
	static int[] min_route = new int[init.N+20];
	static double min_dis =Integer.MAX_VALUE;
	public  ArrayList<Double> array = new ArrayList<Double>();
	static int[][] result_route;
	static double globalBestLength;
	
	public void main_init() {
//		double rounum=0;
		
		for(int i =1;i<init.N;i=i+1) {
//		int i=1;
			CWTSP cwtsp = new CWTSP();
			cwtsp.CWTSP_init(i);   	//CW节约算法
			
			cal_dis(cwtsp);
			
			ACOmain acomain = new ACOmain();
			acomain.ant_main();
			result_route = acomain.getresult();
			globalBestLength = acomain.getglobalBestLength();
			new main_func().acopaint(i);
//			ACOmain acomain = new ACOmain();
//			rounum=acomain.ant_main();
//			array.add(rounum);
//			cal_distance(cwtsp);
			
		}
		
//		for(int i = 0;i<init.N;i++) {
//			System.out.println(min_locate[i][0]+"   "+min_locate[i][1]);
//		}
		
//		System.out.println(array.size());
//		for(int i =0;i<array.size();i++) {
//			System.out.println(array.get(i));
//		}
		
//		ACOmain acomain = new ACOmain();
//		acomain.ant_main();
//		result_route = acomain.getresult();
//		
//		new main_func().main_paint();
//		new main_func().acopaint();
		
	}
	
	//通过将每一个城市点设为CW节约值算法的初始点，算出其最短的路程
	public void cal_distance(CWTSP cwtsp) {
		double distance =0;
		for(int i= 0;i<cwtsp.ans.length-1;i++) {
		distance = distance + sqrt(pow((init.locate[cwtsp.ans[i]][0])-(init.locate[cwtsp.ans[i+1]][0]),2)
				                  +pow((init.locate[cwtsp.ans[i]][1])-(init.locate[cwtsp.ans[i+1]][1]),2));
		}
		if(distance<min_dis) {
			
			min_route = cwtsp.ans.clone();
			//对于二维数组的clone方法，需要将每一行单独clone，将其每一行看作是一个一维数组
			//不然就会相当于引用，两个数组将会有用一个地址
			for(int i = 0;i<init.N;i++) {
				min_locate[i] = init.locate[i].clone();
			}
			min_dis = distance;
		}
		System.out.println(min_dis);
//		System.out.println(min_route[1]);
//		System.out.println("min_locate"+min_locate[0][0]+" "+min_locate[0][1]+"  "+min_locate[1][0]+" "+min_locate[1][1]);
	}
	
	public void cal_dis(CWTSP cwtsp) {
		double distance =0;
		for(int i= 0;i<cwtsp.ans.length-1;i++) {
		distance = distance + sqrt(pow((init.locate[cwtsp.ans[i]][0])-(init.locate[cwtsp.ans[i+1]][0]),2)
				                  +pow((init.locate[cwtsp.ans[i]][1])-(init.locate[cwtsp.ans[i+1]][1]),2));
		}
		min_dis = distance;
		for(int i = 0;i<init.N;i++) {
			min_locate[i] = init.locate[i].clone();
		}
	}
	

	private void main_paint() {
		// TODO Auto-generated method stub
		pd = new Pattern_Display(min_locate,min_route);
		this.add(pd);
		this.setSize(1366, 730);
		this.setLocationRelativeTo(null);
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH);  	  //最大化
//		this.setResizable(false);       				  //不能改变大小
//		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void acopaint(int i) {
		at = new ACOtest(result_route,globalBestLength);
		this.add(at);
		this.setSize(1366, 730);
		this.setTitle(i+"");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}




	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new main_func().main_init();
		

	}

}
