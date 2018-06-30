package com.test_1;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import javax.swing.JFrame;

public class CWTSP  {
	double[] disself=new double[init.N];
	double[][] dis = new double[init.N][init.N];  //距离矩阵
	double[][] S = new double[init.N][init.N];	 //节省值矩阵
	double[] e = new double[init.N*init.N];    	 //节省值降序排列
	int[][] route =new int [init.N][init.N+20];
	int[] x =new int[init.N*init.N];
	int[] y =new int[init.N*init.N];
	int[] ans =new int[init.N+20];
	int n = 0;
	int flag =0;


	public void CWTSP_init(int Origin) {

		function fun =new function();

		CW_init(Origin);

//		System.out.println("节约值排序");
//		for(int i =1;i<=n;i++) {
//			if(e[i]!=0) {
//				System.out.println(e[i]+"...("+locate[x[i]][0]+","+locate[x[i]][1]+")--("+locate[y[i]][0]+","+locate[y[i]][1]+")");
//			}
//		}

		for(int i=1; i<=n; i++) {
			int[] a = fun.findindex(x[i],route);
			int[] b = fun.findindex(y[i],route);
			if(a[0]!=b[0]) {
				int k = fun.Type(a,b,route);
				switch(k) {
				case 1:
					fun.inthequeue(a,b,route);
					break;
				case 2:
					fun.inthequeue2(a,b,route);
					break;
				case 3:
					fun.inthequeue3(a,b,route);
					break;
				case 4:
					fun.inthequeue4(a,b,route);
					break;
				default:
					break;
				}

			}

		}


		int cont =0;
		for(int i=0; i<init.N; i++) {
			if((route[i][0]==0)&&(route[i][1]==0)) {
				continue;
			}
			for(int j=0; j<init.N+20; j++) {
				ans[cont] = route[i][j];
				cont++;
				if((route[i][j]==0)&&(route[i][j+1]==0)) {
					break;
				}

			}
		}

		for(int i=0; i<init.N+20; i++) {
			System.out.print(ans[i]+"→");
		}
		System.out.println();

		//CWTSP ct =new CWTSP();

	}


//	public CWTSP() {
//
//	}

	public  void CW_init(int Origin) {
		//设置原点
		init.locate[0][0] = init.locate[Origin][0];
		init.locate[0][1] = init.locate[Origin][1];
		
		//每个点到原点的距离
		for(int i=1; i<init.N; i++) {
			disself[i] = sqrt(pow((init.locate[i][0])-(init.locate[0][0]),2)
						+pow((init.locate[i][1])-(init.locate[0][1]),2)); 
		}

		//每个点和每个点之间的距离，同时将节约值计算出来，
		for(int i=1; i<init.N; i++) {
			for(int j=i+1; j<init.N; j++) {
				n++;
				dis[i][j] = sqrt(pow((init.locate[i][0]-init.locate[j][0]),2)
							+pow((init.locate[i][1]-init.locate[j][1]),2));
				S[i][j] = disself[i]+disself[j]-dis[i][j];
				x[n] = i;
				y[n] = j;
				e[n] = S[i][j];
			}
		}

		//对节约值进行排序，利用冒泡排序法
		for(int i =1; i<=n; i++) {
			flag = 0;
			for(int j = 1; j<n+1-i; j++) {
				if(e[j]<e[j+1]) {
					double k=e[j];
					e[j] =e[j+1];
					e[j+1] = k;
					int a = x[j];
					x[j] = x[j+1];
					x[j+1] = a;
					int b = y[j];
					y[j] = y[j+1];
					y[j+1] = b;
					flag = 1;
				}
			}
			if(flag==0) {
				break;
			}
		}

		//构造路径
		for(int i=0; i<init.N-1; i++) {
			route[i][1] =i+1;
		}

	}
}
