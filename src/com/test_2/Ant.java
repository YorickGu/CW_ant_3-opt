package com.test_2;
public class Ant {
		public int startCity,currentCity;
		public int[] visited;			//禁忌表，记录一次周游过程中，已经走过的节点
		public int[][] tour;				//已经访问城市的编号顺序表
	    public double totalLength=0;		//一次周游过程中，走过的距离和
	    public int visitednum;			//记录已经访问过城市的数目
		  
	}