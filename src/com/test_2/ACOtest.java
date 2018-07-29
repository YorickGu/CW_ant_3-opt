package com.test_2;

import java.awt.Graphics;

import javax.swing.JPanel;

public class ACOtest extends JPanel {
	static double k = 0;
	final static int m = 10;
	int[][] result = new int[init.N - 1][2];
	double globalBestLength;

	static double max_x = 0;
	static double max_y = 0;

	static double Expand_x = 0;
	static double Expand_y = 0;

	public ACOtest(int[][] result, double globalBestLength) {
		this.result = result;
		this.globalBestLength = globalBestLength;

		for (int x = 0; x < init.N; x++) {
			if (max_x < init.locate[x][0]) {
				max_x = init.locate[x][0];
			}
			if (max_y < init.locate[x][1]) {
				max_y = init.locate[x][1];
			}
		}

		Expand_x = 1300.0 / max_x;
		Expand_y = 650.0 / max_y;

		if (Expand_x < Expand_y) {
			k = Expand_x;
		} else {
			k = Expand_y;
		}

	}

	public void paint(Graphics g) {
		super.paint(g);
		
		g.drawString(globalBestLength + "", 1000, 100);

		String[] str = new String[init.N];

		for (int i = 1; i < init.N; i++) {
			str[i] = String.valueOf(i - 1);
			g.drawString(str[i], (int) (init.locate[i][0] * k) + m + 5, (int) (init.locate[i][1] * k) + m + 10 - 5);
		}

		for (int i = 0; i < init.N - 1; i++) {
			g.drawLine((int) (init.locate[result[i][0] + 1][0] * k + m),
					(int) (init.locate[result[i][0] + 1][1] * k + m + 10),
					(int) (init.locate[result[i][1] + 1][0] * k + m),
					(int) (init.locate[result[i][1] + 1][1] * k + m + 10));
		}

	}

}
