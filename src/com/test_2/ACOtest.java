package com.test_2;

/*
 * �ڽ���GU����ͼ�������ʱ�������ʹ�õ���Graphics����ô����Ҫ��paint�������¶��庯���ģ�
 * �����ʹ�õ���Graphics2D����ô�Ͳ�Ҫ���¶���һ��paint������ֱ�ӾͿ���ʹ��Graphics2D������Ĺ��ܡ�
 * */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ACOtest extends JPanel {

	// Graphics g = null;
	// BufferedImage bi;

	static double k = 0;
	final static int m = 10;
	int[][] result = new int[init.N - 1][2];
	double globalBestLength;

	static double max_x = 0;
	static double max_y = 0;

	static double Expand_x = 0;
	static double Expand_y = 0;

	public void init(double globalBestLength) {
		// Container content = jf.getContentPane();
		BufferedImage bi = new BufferedImage(1366, 730, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) bi.getGraphics();
		g2d.setBackground(Color.WHITE);
		g2d.clearRect(0, 0, 1366, 730);
		g2d.setPaint(Color.BLACK);
//		g2d.drawString(globalBestLength + "", 1200, 12);
		g2d.setStroke(new BasicStroke(8f));
		for (int x = 0; x < init.N - 1; x++) {
			g2d.drawLine((int) (init.locate[result[x][0] + 1][0] * Expand_x + m),
					(int) (init.locate[result[x][0] + 1][1] * Expand_y + m + 10),
					(int) (init.locate[result[x][1] + 1][0] * Expand_x + m),
					(int) (init.locate[result[x][1] + 1][1] * Expand_y + m + 10));
		}
		// content.paintAll(g2d);
		// g = bi.getGraphics();
		// paint(g2d);
		String Filepath = "C:\\Users\\guyao\\Desktop\\����TSP\\ʵ������\\tsp225_3916\\";
		String n1 = globalBestLength + "";
		Filepath = Filepath + n1 + ".jpg";
		File oFile = new File(Filepath);
		try {
			ImageIO.write(bi, "jpg", oFile);
		} catch (IOException e) {
			e.printStackTrace();
		} // ����ͼ���ļ�
	}

	public ACOtest() {
		// this.setBackground(Color.WHITE);
	}

	public ACOtest(int[][] result, double globalBestLength, int i, JFrame jf) {

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

		Expand_x = 670.0 / max_x;
		Expand_y = 670.0 / max_y;

//		if (Expand_x < Expand_y) {
//			k = Expand_x;
//		} else {
//			k = Expand_y;
//		}

	}

	public void paint(Graphics g) {

		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawString(globalBestLength + "", 1200, 12);
		g2d.setStroke(new BasicStroke(8f));

		for (int x = 0; x < init.N - 1; x++) {
			g2d.drawLine((int) (init.locate[result[x][0] + 1][0] * Expand_x + m),
					(int) (init.locate[result[x][0] + 1][1] * Expand_y + m + 10),
					(int) (init.locate[result[x][1] + 1][0] * Expand_x + m),
					(int) (init.locate[result[x][1] + 1][1] * Expand_y + m + 10));
		}

		// String[] str = new String[init.N];
		// for (int i = 1; i < init.N; i++) {
		// str[i] = String.valueOf(i - 1);
		// g.drawString(str[i], (int) (init.locate[i ][0] * k) + m + 5, (int)
		// (init.locate[i][1] * k) + m + 10 - 5);
		// }
	}

}
