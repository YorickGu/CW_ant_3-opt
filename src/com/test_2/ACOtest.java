package com.test_2;

import java.awt.Graphics;

import javax.swing.JPanel;

public class ACOtest extends JPanel {
	final static double k = 0.9;
	final static int m = 20;
	int[][] result = new int[init.N - 1][2];
	double globalBestLength;

	public ACOtest(int[][] result, double globalBestLength) {
		this.result = result;
		this.globalBestLength = globalBestLength;
	}

	public void paint(Graphics g) {

		super.paint(g);

		g.drawString(globalBestLength + "", 1000, 100);

		String[] str = new String[init.N];

		for (int i = 1; i < init.N; i++) {
			str[i] = String.valueOf(i);
			g.drawString(str[i], (int) (init.locate[i][0] * k) + 2 + m, (int) (init.locate[i][1] * k) + 2+ m);
		}

		for (int i = 0; i < init.N - 1; i++) {
			g.drawLine((int) (init.locate[result[i][0] + 1][0] * k+ m), (int) (init.locate[result[i][0] + 1][1] * k+ m),
					(int) (init.locate[result[i][1] + 1][0] * k+ m), (int) (init.locate[result[i][1] + 1][1] * k+ m));
		}

	}

}
