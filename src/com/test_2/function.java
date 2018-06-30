package com.test_2;

public class function {
	public int[] findindex(int x,int[][] route) {
		int[] index = new int[2];
		for(int m=0; m<init.N; m++) {
			for(int k=1; k<init.N+20; k++) {
				if(x==route[m][k]) {
					index[0] = m;
					index[1] = k;
					return index;
				}
				if((route[m][k-1]==0)&&(route[m][k]==0)) {
					break;
				}
			}
		}
		return index;
	}

	public int Type(int[] a,int[] b,int[][] route) {
		int k = 0;
		if(((route[a[0]][a[1]+1])+(route[b[0]][b[1]+1]))==0) {
			k=1;
			return k;
		}
		if(((route[a[0]][a[1]+1])+(route[b[0]][b[1]-1]))==0) {
			k=2;
			return k;
		}
		if(((route[a[0]][a[1]-1])+(route[b[0]][b[1]+1]))==0) {
			k=3;
			return k;
		}
		if(((route[a[0]][a[1]-1])+(route[b[0]][b[1]-1]))==0) {
			k=4;
			return k;
		}
		return k;
	}

	public void inthequeue(int[] a,int[] b,int[][] route) {

		for(int i = b[1]; i>=0; i--) {
			route[a[0]][a[1]+b[1]-i+1] = route[b[0]][i];
		}

		if(a[0]>b[0]) {
			for(int i=0; i<init.N+20; i++) {
				route[b[0]][i] = route[a[0]][i];
				route[a[0]][i] = 0;
			}
		} else {
			for(int i=0; i<init.N+20; i++) {
				route[b[0]][i] = 0;
			}
		}

	}

	public void inthequeue2(int[] a,int[] b,int[][] route) {
		for(int i=a[1]+1; i<init.N+20; i++) {
			route[a[0]][i] = route[b[0]][i-a[1]];
		}
		if(a[0]>b[0]) {
			for(int i=0; i<init.N+20; i++) {
				route[b[0]][i] = route[a[0]][i];
				route[a[0]][i] = 0;
			}
		} else {
			for(int i=0; i<init.N+20; i++) {
				route[b[0]][i] = 0;
			}
		}
	}

	public void inthequeue3(int[] a,int[] b,int[][] route) {
		for(int i =b[1]+1; i<init.N+20; i++) {
			route[b[0]][i] = route[a[0]][i-b[1]];
		}
		if(a[0]<b[0]) {
			for(int i=0; i<init.N+20; i++) {
				route[a[0]][i] = route[b[0]][i];
				route[b[0]][i] = 0;
			}
		} else {
			for(int i=0; i<init.N+20; i++) {
				route[a[0]][i] = 0;
			}
		}
	}

	public void inthequeue4(int[] a,int[] b,int[][] route) {

		int i=1;
		while(route[a[0]][i]!=0) {
			i++;
		}

		if(i%2==0) {
			for(int m=i; m>=i/2; m--) {
				int k = route[a[0]][m];
				route[a[0]][m] = route[a[0]][i-m];
				route[a[0]][i-m] = k;
			}
		} else {
			for(int m =i; m>=(i+1)/2; m--) {
				int k = route[a[0]][m];
				route[a[0]][m] = route[a[0]][i-m];
				route[a[0]][i-m] = k;
			}
		}

		a[1] = i-1;
		inthequeue2(a,b,route);

	}

}
