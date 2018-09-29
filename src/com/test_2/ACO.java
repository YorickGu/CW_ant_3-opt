package com.test_2;

import static java.lang.Math.pow;
import static java.lang.Math.random;
import static java.lang.Math.sqrt;

import java.util.Random;

public class ACO {

	private int N; // 城市的数量
	private int M; // 蚂蚁数量
	private int L; // 循环次数
	private double distance[][]; // 任意两城市间的距离
	private double info[][]; // 任意两城之间的信息量
	private double visible[][]; // 两城之间的可见度，即为两城距离的倒数
	public double MAX_LENGTH = 10000000000.0; // 用于比较，是个无限大的值
	double globalBestLength;
	int[][] globalBestTour;
	double kaifang;
	double K;
	double templength;
	static int numlength = 0;
	static int numlength_sum = 0;
	double aveinfo;

	private double alpha; // 期望启发式因子，表示蚂蚁运动对信息素影响的程度
	private double beta; // 期望启发式因子，表示贪心规则对信息素的影响程度
	private double localRate; // 局部信息素蒸发率
	private double globalRate; // 全局信息素蒸发率
	public double Length; // 此值为一常数，是计算信息素时需要的一常数，我这里取值为蚂蚁以贪心算法运动一周所走的路程总长度，计算处请见第四部分

	class City { // 定义了城市的x,y坐标以及城市编号，并实现了计算城市距离的函数

		int no; // 城市编号
		double x; // 城市x坐标
		double y; // 城市y坐标

		City(int no, double x, double y) { // 构造函数

			this.no = no;
			this.x = x;
			this.y = y;
		}

		private double getDistance(City city) {

			return sqrt(pow((x - city.x), 2) + pow((y - city.y), 2));
		}
	}

	City[] city; // 定义了City类的一个数组，该数组存储着编号城市的x,y坐标值

	/*---------------------------------------------第一部分，数据的初始化--------------------------------------------------------*/

	public void InitData(double[][] locate, int N, int M, int L, double beta, double alpha, double localRate,
			double globalRate) {

		// 初始化基本参数
		this.N = N;
		this.M = M;
		this.L = L;
		this.beta = beta;
		this.alpha = alpha;
		this.localRate = localRate;
		this.globalRate = globalRate;
		templength = 0;

		// 初始化City数组，保存城市编号以及城市坐标
		city = new City[N];
		for (int i = 0; i < N; i++) {
			city[i] = new City(i, locate[i][0], locate[i][1]);
		}

		// 初始化distance矩阵，保存任意两个城市间的距离
		distance = new double[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j <= i; j++) {
				distance[i][j] = city[i].getDistance(city[j]);
				distance[j][i] = distance[i][j];

			}

	}

	public void InitInfo(double value, double teta_value) {
		// 初始化信息素数组和可见度数组
		info = new double[N][N];
		visible = new double[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				info[i][j] = value;
				info[j][i] = value;
				if (i != j) {
					visible[i][j] = 1.0 / distance[i][j];
					visible[j][i] = visible[i][j];
				}
				/*
				 * info[i][j] = 1; if(i != j) { visible[i][j] = 1.0 / distance[i][j]; }
				 */
			}
		}

		for (int i = 1; i < init.N + 20; i++) {
			if (main_func.min_route[i] == 0 || main_func.min_route[i + 1] == 0) {
				break;
			}
			info[main_func.min_route[i] - 1][main_func.min_route[i + 1]
					- 1] = info[main_func.min_route[i] - 1][main_func.min_route[i + 1] - 1] + teta_value;
			info[main_func.min_route[i + 1] - 1][main_func.min_route[i]
					- 1] = info[main_func.min_route[i] - 1][main_func.min_route[i + 1] - 1];
		}

	}
	/*--------------------------------------第二部分，一只蚂蚁运动过程涉及到的处理函数函数------------------------------------*/

	// 计算当前节点到下一个节点的转移的概率
	public double Possibility(int i, int j) {
		if (i != j) {
			return (pow(info[i][j], alpha) * pow(visible[i][j], beta)); // 这是计算转移概率的公式
		} else
			return 0.0;
	}

	// 一只蚂蚁搜索路径并完一周旅行的函数
	Ant SearchGo(Ant ant, int num) {

		int nextCity;
		int endCity;
		ant.currentCity = ant.startCity;
		ant.visitednum = 0; // 记录已经访问过城市的数目
		ant.visited = new int[N];// 禁忌表，记录一次周游过程中，已经走过的节点
		ant.tour = new int[N][2];// 已经访问城市的编号顺序表
		ant.totalLength = 0;
		for (int i = 0; i < N; i++) { // N the number of the cities
			ant.visited[i] = 1;
		}
		ant.visited[ant.currentCity] = 0;
		do {

			ant.visitednum++;
			endCity = ant.currentCity;
			nextCity = Choose(ant, num);
			if (nextCity >= 0) {
				ant.visited[nextCity] = 0;
				ant.tour[ant.visitednum - 1][0] = ant.currentCity;
				ant.tour[ant.visitednum - 1][1] = nextCity;
				UpdateLocal(endCity, nextCity);
				ant.currentCity = nextCity;

			}

		} while (nextCity >= 0);
		ant.tour[ant.visitednum - 1][0] = endCity;
		ant.tour[ant.visitednum - 1][1] = ant.startCity;
		UpdateLocal(endCity, ant.startCity);

		return ant;
	}

	// 上述过程中的局部更新原则
	void UpdateLocal(int i, int j) {
		info[i][j] = (1.0 - localRate) * info[i][j] + localRate * (1.0 / (N * Length));
		info[j][i] = info[i][j];
	}

	// 选择下一个城市
	int Choose(Ant ant, int num) {
		int nextCity = -1;
		// double e = Math.E;
		// double Pro = 1-0.99*(1-pow(e,(-num/(0.05*L))));
		double q = random();
		// 测试可知，q 小于0.01的几率很小，既是为了满足蚁群算法中的小概率出错事件，为了满足蚁群创新性
		if (q < 0.01) {
			double pros = -1.0;
			for (int i = 0; i < N; i++) {
				if (ant.visited[i] == 1) {
					double p = Possibility(ant.currentCity, i);
					if (pros < p) {
						nextCity = i;
						pros = p;
					}

				}
			}
		} // 按概率转移
		else {
			double m = 0;
			// 获得当前所在的城市号放入i,如果和i相邻的城市没有被访问，那么加入m
			for (int i = 0; i < N; i++) {

				if (ant.visited[i] == 1) {
					m += Possibility(ant.currentCity, i);
				}
			}

			double k = 0;

			for (int i = 0; i < N; i++) {

				if (ant.visited[i] == 1 && m > 0) {

					k += Possibility(ant.currentCity, i) / m;
					// System.out.println(q);
					if (k >= q) {
						nextCity = i;
						break;
					}
					nextCity = i;// 如果k最后等于1了，也不大于q的话，就直接将j赋值给nextCity
				}

			}
		}
		// System.out.println("nextCity"+nextCity);
		return nextCity;
	}

	/*-------------------------------第三部分，全局计算，找出一次循环结束后的最优路径和最小长度，并进行信息素的全局更新---------------------------*/

	// 蚁群在一次循环过程中的最优路径并进行信息素的更新
	public Ant OneIterator(int num) {

		int[] nums = new int[N];
		for (int i = 0; i < N; i++) {
			nums[i] = i;
		}
		randSelect(nums, M);

		Ant bestAnt = new Ant();
		// 找到走了最优路径的蚂蚁
		Ant[] ant = new Ant[M];
		for (int i = 0; i < M; i++) {

			ant[i] = new Ant();
			// ant[i].startCity = i % N; // 将M只蚂蚁均匀放在N个城市上
			ant[i].startCity = nums[i];

			// System.out.println("ant"+i+"startCity"+ant[i].startCity);
			ant[i] = SearchGo(ant[i], num);
		}

		// 选择出当前循环中的最好的路径
		double BestTourInOne = MAX_LENGTH;
		int a;
		int b;

		for (int j = 0; j < M; j++) { // 对每只蚂蚁而言
			for (int i = 0; i < N; i++) { // 每只蚂蚁所走的路径长度
				a = ant[j].tour[i][0]; // tour是蚂蚁行走的城市顺序
				b = ant[j].tour[i][1];
				ant[j].totalLength += distance[a][b];
			}

			if (ant[j].totalLength < BestTourInOne) {
				bestAnt = ant[j];
				BestTourInOne = ant[j].totalLength;
				// System.out.println("BestTourInOne"+BestTourInOne);

			}

		}

		// 进行opt-3转换
		// System.out.println();
//		 int Number = 10;
//		 int x,y;
		// double[] number = new double[2];
		for (int i = 0; i < N; i++) {
			for (int j = i + 2; j < (N); j++) {

//				 if ((j - i - 1) <= Number) {
//				if (j >= N) {
//					x = j - N;
//				}
//				else {
//					x = j;
//				}
				if (distance[bestAnt.tour[i][0]][bestAnt.tour[j][0]] < (bestAnt.totalLength / 2)) {

					for (int k = j + 2; k < (N); k++) {
						// if ((k - j - 1) <= Number) {
//						if (k >= N) {
//							y = k - N;
//						}
//						else {
//							y = k;
//						}
						if (distance[bestAnt.tour[j][0]][bestAnt.tour[k][0]] < (bestAnt.totalLength / 2)) {

							int number = switch_mindistance(bestAnt, i, j, k);

							// DecimalFormat df2 = new DecimalFormat("000.000");
							// System.out.print(df2.format(number[1])+" "+(int)number[0]+" ");
							// System.out.print(number);
							// for(int x=0;x<N;x++) {
							// bestAnt.tour[x] = switch_mindistance(bestAnt,i,j,k).tour[x].clone();
							// }

							switch (number) {
							case 0:
								break;
							case 1:
								rever(bestAnt, i + 1, j);
								// bestAnt.totalLength = bestAnt.totalLength - number[1];
								break;
							case 2:
								rever(bestAnt, j + 1, k);
								// bestAnt.totalLength = bestAnt.totalLength - number[1];
								break;
							case 3:
								rever(bestAnt, k + 1, i);
								// bestAnt.totalLength = bestAnt.totalLength - number[1];
								break;
							case 4:
								rever(bestAnt, j + 1, k);
								rever(bestAnt, k + 1, i);
								// bestAnt.totalLength = bestAnt.totalLength - number[1];
								break;
							case 5:
								rever(bestAnt, k + 1, i);
								rever(bestAnt, i + 1, j);
								// bestAnt.totalLength = bestAnt.totalLength - number[1];
								break;
							case 6:
								rever(bestAnt, i + 1, j);
								rever(bestAnt, j + 1, k);
								// bestAnt.totalLength = bestAnt.totalLength - number[1];
								break;
							case 7:
								rever(bestAnt, i + 1, j);
								rever(bestAnt, j + 1, k);
								rever(bestAnt, k + 1, i);
								// bestAnt.totalLength = bestAnt.totalLength - number[1];
								break;

							}
						} else {
							break;
						}
					}
				} else {
					break;
				}
			}
			// System.out.println();
		}
		// System.out.println();
		bestAnt.totalLength = 0;
		for (int i = 0; i < N; i++) { // 每只蚂蚁所走的路径长度
			a = bestAnt.tour[i][0]; // tour是蚂蚁行走的城市顺序
			b = bestAnt.tour[i][1];
			bestAnt.totalLength += distance[a][b];
		}

		// 进行全局信息素更新
		// kaifang = pow(0.05, 1 / N);
		// K = (1 - kaifang) / ((N / 2 - 1) * kaifang);
		kaifang = pow(globalBestLength, 1 / N);
		K = (10 * (1 - kaifang)) / ((N - 2) * kaifang);
		aveinfo = ((1 - globalRate) * globalBestLength) / 10;

		templength = globalBestLength;

		if (bestAnt.totalLength < globalBestLength) {
			globalBestLength = bestAnt.totalLength;
			globalBestTour = bestAnt.tour;
		}

		for (int i = 0; i < N; i++) {
			a = globalBestTour[i][0];
			b = globalBestTour[i][1];
			info[a][b] = (1.0 - globalRate) * info[a][b] + globalRate * (1.0 / globalBestLength);
			info[b][a] = info[a][b];

			if (info[a][b] > (1 / ((1 - globalRate) * globalBestLength))) {
				info[a][b] = (1 / ((1 - globalRate) * globalBestLength));
				info[b][a] = (1 / ((1 - globalRate) * globalBestLength));
			}

			if (info[a][b] < (K * (1 / ((1 - globalRate) * globalBestLength)))) {
				info[a][b] = (K * (1 / ((1 - globalRate) * globalBestLength)));
				info[b][a] = (K * (1 / ((1 - globalRate) * globalBestLength)));
			}

		}

		if (templength == globalBestLength) {
			numlength++;
			numlength_sum++;
			if (numlength > 10) {
				beta = 1 * (Length) / globalBestLength;
//				if (globalRate <= 0.3 && localRate <= 0.3) {
//					globalRate = globalRate * 1.1;
//					localRate = localRate * 1.1;
//				}
				// alpha = (globalBestLength/Length)*2;
				numlength = 0;
			}
			if (numlength_sum > 30) {
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						if (info[i][j] > aveinfo) {
							info[i][j] = 0.4 * info[i][j];
						}
					}
				}
				numlength_sum = 0;
			}
		} else {
			numlength = 0;
			numlength_sum = 0;
		}

		return bestAnt;
	}

	/*--------------------------------------第四部分，辅助函数计算,用于给信息素设定初始值--------------------------------------------------*/

	// 以下是用贪心策略，计算出运动一周蚂蚁走过的路程，在此程序中，只是为了设定初始值Length而用，既是为了得到全局长度的一个大致估值
	int ChooseNearestNext(Ant ant) {
		int next = -1;
		double dist = 0;
		for (int i = 0; i < N; i++) {
			if (ant.visited[i] == 1) {
				if (dist == 0.0) {
					dist = distance[ant.currentCity][i];
					next = i;
				}
				if (distance[ant.currentCity][i] > dist) {
					// dist=distance[ant.currentCity][i];
					next = i;
				}
			}
		}
		ant.visited[next] = 0;
		// System.out.println(next);
		return next;
	}

	void Length() {
		Ant ant = new Ant();
		ant.visited = new int[N];
		for (int i = 0; i < N; i++) {

			ant.visited[i] = 1;
		}
		int star = 0;
		int to;
		Length = 0;
		ant.visited[star] = 0;
		ant.currentCity = star;
		for (int i = 1; i < N; i++) {
			to = ChooseNearestNext(ant);
			Length += distance[ant.currentCity][to];
			ant.currentCity = to;
		}
		Length += distance[ant.currentCity][star];
		System.out.println();
	}

	public int switch_mindistance(Ant bestAnt, int i, int j, int k) {
		if (j >= N) {
			j = j - N;
		}
		if (k >= N) {
			k = k - N;
		}
		int number = 0;
		double number_8 = 99999999;
		double[] number_num = new double[8];

		if ((i != N - 1) && (j != N - 1) && (k != N - 1)) {
			int a = bestAnt.tour[i][0];
			int a_1 = bestAnt.tour[i + 1][0];
			int b = bestAnt.tour[j][0];
			int b_1 = bestAnt.tour[j + 1][0];
			int c = bestAnt.tour[k][0];
			int c_1 = bestAnt.tour[k + 1][0];
			number_num[0] = distance[a][a_1] + distance[b][b_1] + distance[c][c_1];
			number_num[1] = distance[a][b] + distance[a_1][b_1] + distance[c][c_1];
			number_num[2] = distance[a][a_1] + distance[b][c] + distance[b_1][c_1];
			number_num[3] = distance[a][c] + distance[b][b_1] + distance[c_1][a_1];
			number_num[4] = distance[a][b_1] + distance[c][b] + distance[a_1][c_1];
			number_num[5] = distance[a][c] + distance[a_1][b_1] + distance[b][c_1];
			number_num[6] = distance[a][b] + distance[a_1][c] + distance[b_1][c_1];
			number_num[7] = distance[a][b_1] + distance[c][a_1] + distance[b][c_1];
		} else if (i == N - 1) {
			int a = bestAnt.tour[i][0];
			int a_1 = bestAnt.tour[0][0];
			int b = bestAnt.tour[j][0];
			int b_1 = bestAnt.tour[j + 1][0];
			int c = bestAnt.tour[k][0];
			int c_1 = bestAnt.tour[k + 1][0];
			number_num[0] = distance[a][a_1] + distance[b][b_1] + distance[c][c_1];
			number_num[1] = distance[a][b] + distance[a_1][b_1] + distance[c][c_1];
			number_num[2] = distance[a][a_1] + distance[b][c] + distance[b_1][c_1];
			number_num[3] = distance[a][c] + distance[b][b_1] + distance[c_1][a_1];
			number_num[4] = distance[a][b_1] + distance[c][b] + distance[a_1][c_1];
			number_num[5] = distance[a][c] + distance[a_1][b_1] + distance[b][c_1];
			number_num[6] = distance[a][b] + distance[a_1][c] + distance[b_1][c_1];
			number_num[7] = distance[a][b_1] + distance[c][a_1] + distance[b][c_1];
		} else if (j == N - 1) {
			int a = bestAnt.tour[i][0];
			int a_1 = bestAnt.tour[i + 1][0];
			int b = bestAnt.tour[j][0];
			int b_1 = bestAnt.tour[0][0];
			int c = bestAnt.tour[k][0];
			int c_1 = bestAnt.tour[k + 1][0];
			number_num[0] = distance[a][a_1] + distance[b][b_1] + distance[c][c_1];
			number_num[1] = distance[a][b] + distance[a_1][b_1] + distance[c][c_1];
			number_num[2] = distance[a][a_1] + distance[b][c] + distance[b_1][c_1];
			number_num[3] = distance[a][c] + distance[b][b_1] + distance[c_1][a_1];
			number_num[4] = distance[a][b_1] + distance[c][b] + distance[a_1][c_1];
			number_num[5] = distance[a][c] + distance[a_1][b_1] + distance[b][c_1];
			number_num[6] = distance[a][b] + distance[a_1][c] + distance[b_1][c_1];
			number_num[7] = distance[a][b_1] + distance[c][a_1] + distance[b][c_1];
		} else if (k == N - 1) {
			int a = bestAnt.tour[i][0];
			int a_1 = bestAnt.tour[i + 1][0];
			int b = bestAnt.tour[j][0];
			int b_1 = bestAnt.tour[j + 1][0];
			int c = bestAnt.tour[k][0];
			int c_1 = bestAnt.tour[0][0];
			number_num[0] = distance[a][a_1] + distance[b][b_1] + distance[c][c_1];
			number_num[1] = distance[a][b] + distance[a_1][b_1] + distance[c][c_1];
			number_num[2] = distance[a][a_1] + distance[b][c] + distance[b_1][c_1];
			number_num[3] = distance[a][c] + distance[b][b_1] + distance[c_1][a_1];
			number_num[4] = distance[a][b_1] + distance[c][b] + distance[a_1][c_1];
			number_num[5] = distance[a][c] + distance[a_1][b_1] + distance[b][c_1];
			number_num[6] = distance[a][b] + distance[a_1][c] + distance[b_1][c_1];
			number_num[7] = distance[a][b_1] + distance[c][a_1] + distance[b][c_1];
		}

		for (int num = 0; num < 8; num++) {
			if (number_num[num] < number_8) {
				number_8 = number_num[num];
				number = num;
			}
		}

		// number[1] = number_num[0] - number_num[(int)number[0]];
		// bestAnt.totalLength = bestAnt.totalLength - number_num[0] + number_8;
		return number;

		// int a, b;
		// // boolean flag = false;
		// double min = bestAnt.totalLength;
		// int number = 0;
		// int[][] tour1 = new int[N][2];
		//
		// // bestAnt.totalLength;
		// for (int x = 0; x < N; x++) {
		// tour1[x] = bestAnt.tour[x].clone();
		// }
		// // Ant min_dis = new Ant();
		// // min_dis.tour = new int[N][2];
		// // for (int x = 0; x < N; x++) {
		// // min_dis.tour[x] = bestAnt.tour[x].clone();
		// // }
		// // min_dis.totalLength = bestAnt.totalLength;
		// Ant[] ant1 = new Ant[7];
		//
		// for (int num = 0; num < 7; num++) {
		// ant1[num] = new Ant();
		// ant1[num].tour = new int[N][2];
		// }
		//
		// for (int num = 0; num < 7; num++) {
		// for (int x = 0; x < N; x++) {
		// ant1[num].tour[x] = bestAnt.tour[x].clone();
		// }
		// }
		//
		// rever(ant1[0], j);
		//
		// rever(ant1[1], k);
		//
		// rever(ant1[2], i);
		//
		// rever(ant1[3], i);
		// rever(ant1[3], k);
		//
		// rever(ant1[4], i);
		// rever(ant1[4], j);
		//
		// rever(ant1[5], j);
		// rever(ant1[5], k);
		//
		// rever(ant1[6], i);
		// rever(ant1[6], j);
		// rever(ant1[6], k);
		//
		// for (int num = 0; num < 7; num++) {
		// for (int x = 0; x < N; x++) { // 每只蚂蚁所走的路径长度
		// a = ant1[num].tour[x][0]; // tour是蚂蚁行走的城市顺序
		// b = ant1[num].tour[x][1];
		// ant1[num].totalLength += distance[a][b];
		// }
		// }
		//
		// for (int num = 0; num < 7; num++) {
		// if (ant1[num].totalLength < min) {
		// min = ant1[num].totalLength;
		// number = num + 1;
		// }
		// }

		// return number;

	}

	public void rever(Ant Ant1, int i, int j) {
		if (i >= N) {
			i = i - N;
		}
		if (j >= N) {
			j = j - N;
		}

		if (i > j) {
			int temp = i;
			i = j;
			j = temp;
		}

		if ((i > 0) && (j <= N - 1) && (i < j)) {
			if ((j - i) % 2 == 1) {
				for (int x = 0; x < (j - i + 1) / 2; x++) {

					int temp = Ant1.tour[j - x][0];
					Ant1.tour[j - x][0] = Ant1.tour[i + x][0];
					Ant1.tour[i + x][0] = temp;

					temp = Ant1.tour[j - 1 - x][1];
					Ant1.tour[j - 1 - x][1] = Ant1.tour[i - 1 + x][1];
					Ant1.tour[i - 1 + x][1] = temp;
				}
			} else {
				for (int x = 0; x < (j - i) / 2; x++) {

					int temp = Ant1.tour[j - x][0];
					Ant1.tour[j - x][0] = Ant1.tour[i + x][0];
					Ant1.tour[i + x][0] = temp;

					temp = Ant1.tour[j - 1 - x][1];
					Ant1.tour[j - 1 - x][1] = Ant1.tour[i - 1 + x][1];
					Ant1.tour[i - 1 + x][1] = temp;
				}

			}
		}

		else if ((i == 0) && (j <= N - 1)) {
			if ((j - i) % 2 == 1) {
				for (int x = 0; x < (j - i + 1) / 2; x++) {

					int temp = Ant1.tour[j - x][0];
					Ant1.tour[j - x][0] = Ant1.tour[i + x][0];
					Ant1.tour[i + x][0] = temp;

					if (x == 0) {
						int temp_1 = Ant1.tour[j - 1][1];
						Ant1.tour[j - 1][1] = Ant1.tour[N - 1][1];
						Ant1.tour[N - 1][1] = temp_1;
					} else {
						temp = Ant1.tour[j - 1 - x][1];
						Ant1.tour[j - 1 - x][1] = Ant1.tour[i - 1 + x][1];
						Ant1.tour[i - 1 + x][1] = temp;
					}
				}
			} else {
				for (int x = 0; x < (j - i) / 2; x++) {

					int temp = Ant1.tour[j - x][0];
					Ant1.tour[j - x][0] = Ant1.tour[i + x][0];
					Ant1.tour[i + x][0] = temp;

					if (x == 0) {
						int temp_1 = Ant1.tour[j - 1][1];
						Ant1.tour[j - 1][1] = Ant1.tour[N - 1][1];
						Ant1.tour[N - 1][1] = temp_1;
					} else {
						temp = Ant1.tour[j - 1 - x][1];
						Ant1.tour[j - 1 - x][1] = Ant1.tour[i - 1 + x][1];
						Ant1.tour[i - 1 + x][1] = temp;
					}
				}

			}

		}
		// else if(i>j) {
		//
		// }

		// if (i >= N) {
		// i = i - N;
		// }
		// int temp = Ant1.tour[i][0];
		// Ant1.tour[i][0] = Ant1.tour[i][1];
		// if (i > 0) {
		// Ant1.tour[i - 1][1] = Ant1.tour[i][1];
		// }
		// if (i == 0) {
		// Ant1.tour[N - 1][1] = Ant1.tour[0][1];
		// }
		// if (i == N - 1) {
		// Ant1.tour[i][1] = temp;
		// Ant1.tour[0][0] = temp;
		// } else {
		// Ant1.tour[i][1] = temp;
		// Ant1.tour[i + 1][0] = temp;
		// }

	}

	public void randSelect(int[] nums, int n) {
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			swap(nums, i, rand.nextInt(nums.length - i) + i);
		}
	}

	public void swap(int[] nums, int m, int n) {
		int temp = nums[n];
		nums[n] = nums[m];
		nums[m] = temp;
	}

}
