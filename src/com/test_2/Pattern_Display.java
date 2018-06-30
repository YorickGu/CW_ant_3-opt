package com.test_2;

import java.awt.*;
import javax.swing.*;

public class Pattern_Display extends JPanel {
	
	final static double k =8;
	double[][] locate =new double[init.N][2];
	int[] route = new int[init.N+20];
	
	public Pattern_Display(double[][] locate,int[] route) {
		
		for(int i =0; i<init.N; i++) {
			for(int j=0; j<2; j++) {
				this.locate[i][j] =locate[i][j];
			}
		}
		
//		this.locate[0][0] = (locate[1][0]);
//		this.locate[0][1] = (locate[1][1]);

		for(int i=0; i<route.length; i++) {
			this.route[i] = route[i];
		}

	}

	public void paint(Graphics g) {

		super.paint(g);

		String[] str = new String[init.N];
		for(int i=0; i<init.N; i++) {
			str[i] = String.valueOf(i);
			g.drawString(str[i], (int)(this.locate[i][0]*k)+2, (int)(this.locate[i][1]*k)+2);
		}

		for(int i=0; i<init.N; i++) {
			g.drawLine((int)(this.locate[this.route[i]][0]*k),
			           (int)(this.locate[this.route[i]][1]*k),
			           (int)(this.locate[this.route[i+1]][0]*k),
			           (int)(this.locate[this.route[i+1]][1]*k));
		}

	}

}
