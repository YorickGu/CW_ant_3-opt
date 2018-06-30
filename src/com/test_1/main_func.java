package com.test_1;

import javax.swing.JFrame;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class main_func extends JFrame {
	
	Pattern_Display pd = null;
	static double[][] min_locate = new double[init.N][2];
	static int[] min_route = new int[init.N+20];
	double min_dis =Integer.MAX_VALUE;
	
	public void main_init() {
		
		for(int i =1;i<init.N;i++) {
			CWTSP cwtsp = new CWTSP();
			cwtsp.CWTSP_init(i);
			cal_distance(cwtsp);
			
		}
		
		new Newest().ant_init();
		new main_func().main_paint();
		
	}
	
	public void cal_distance(CWTSP cwtsp) {
		double distance =0;
		for(int i= 0;i<cwtsp.ans.length-1;i++) {
		distance = distance + sqrt(pow((init.locate[cwtsp.ans[i]][0])-(init.locate[cwtsp.ans[i+1]][0]),2)
				                  +pow((init.locate[cwtsp.ans[i]][1])-(init.locate[cwtsp.ans[i+1]][1]),2));
		}
		if(distance<min_dis) {
			min_locate = init.locate.clone();
			min_route = cwtsp.ans.clone();
			min_dis = distance;
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




	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new main_func().main_init();
		

	}
	
	

}
