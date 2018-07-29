package com.test_2;

public class ACOmain {

	int[][] globalBestTour = null;
	double globalBestLength = 0;

	public double ant_main() {
		long startTime = System.currentTimeMillis();

		ACO aco = new ACO();
		double locate[][] = new double[init.N - 1][2];
		for (int i = 0; i < init.N - 1; i++) {
			locate[i] = init.locate[i + 1].clone();
		}

		for (int i = 0; i < locate.length; i++) {
			System.out.println(locate[i][0] + "  " + locate[i][1]);
		}

		int N = locate.length; // 城市数目
		// int M=locate.length;
		int M = (int)(N*0.666667); 
		int L = 2500;
		double value;   // 信息素的初值设定
		double beta = 5;
		double alpha = 2;
		double localRate = 0.1;
		double globalRate = 0.02;
		double teta_value = 0;

		aco.InitData(locate, N, M, L, beta, alpha, localRate, globalRate);
		aco.Length();
		value = 1 / (aco.Length * N);
		teta_value = 1 / 5 * (main_func.min_dis * N);
		aco.InitInfo(value, teta_value);

		Ant[] ant = new Ant[L]; // 记录每次循环过程中的最优蚂蚁

		aco.globalBestLength = aco.MAX_LENGTH;
		aco.globalBestTour = new int[N][2];

		for (int i = 0; i < L; i++) {
			
//			if(i>200) {
//				int a=0;
//				a=a+1;
//				a=a+2;
//				
//			}

			ant[i] = aco.OneIterator(i);
			// System.out.println(i);
			// 此处可以查看每次循环得到的最优解的情况
//			 System.out.println(ant[i].totalLength);
			System.out.println(aco.globalBestLength);
			// System.out.println("第"+i+"最优路径为：");
			// for(int j=0;j<locate.length;j++)
			// {
			// System.out.print("→"+aco.globalBestTour[j][0]);
			// }
			// System.out.println();

		}

		this.globalBestTour = aco.globalBestTour;
		this.globalBestLength = aco.globalBestLength;

		System.out.println();
		System.out.println("最优的路径长度为: " + aco.globalBestLength);
		System.out.println("最优路径为： ");
		for (int i = 0; i < aco.globalBestTour.length; i++) {
			System.out.print("→" + aco.globalBestTour[i][0]);
		}
		long endTime = System.currentTimeMillis();

		System.out.println();
		System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
		return aco.globalBestLength;
	}

	public int[][] getresult() {
		return globalBestTour;
	}

	public double getglobalBestLength() {
		return globalBestLength;
	}

}
