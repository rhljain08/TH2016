package com.b.trap;

import java.util.Arrays;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class Mazetopbottom {
	private static int graph[][];
	private static int graph1[][];
	private static int rowSize;
	private static int colSize;
	private static int row,colm;
	private static int level;
	private static int matrix[][];

	private static int matrix_hard[][] = new int[][]{

		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}

	};

	private static int matrix_easy[][] = new int[][]{

		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}

	};

	private static int matrix_medium[][] = new int[][]{

		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1}

	};

	private static int matrix_difficult[][] = new int[][]{

		{1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1}

	};



	static void printPath(int sol[][]){
		level  = HexagonView.level;
		for(int i = 0; i<sol.length;i++){
			for(int j = 0;j<sol[0].length;j++){
				System.out.print(sol[i][j]+" ");
			}
			System.out.println();
		}
	}

	public static boolean updateMatrix(int id){
		level  = HexagonView.level;
		switch (level%3) {
		case 0:
			matrix = matrix_easy;
			break;
		case 1:
			matrix = matrix_medium;
			break;
		case 2:
			matrix = matrix_difficult;
			break;
		case 3:
			matrix = matrix_hard;
			break;
		}

		row = id/matrix[0].length;
		colm = (id%matrix[0].length) -1;
		if(colm == -1){
			colm = matrix[0].length -1;
			row = row-1;
		}
		//Log.d("RAJAT"," Set to zero updateMatrix :  row :  "+row+" colmn : "+colm);
		if(matrix[row][colm] == 1) {
			matrix[row][colm] = 0;
			generateMatrix();
			return true;
		}else
			return false;
	}


	public static void updateInitialMatrix(int[] initialBlocks){
		level  = HexagonView.level;
		switch (level%3) {
		case 0:
			matrix = matrix_easy;
			break;
		case 1:
			matrix = matrix_medium;
			break;
		case 2:
			matrix = matrix_difficult;
			break;
		case 3:
			matrix = matrix_hard;
			break;
		}

		for(int i=0;i<matrix.length;i++)
			Arrays.fill(matrix[i], 1);
		//printPath(matrix);
		for(int id: initialBlocks) {
			row = id/matrix[0].length;
			colm = (id%matrix[0].length) -1;
			if(colm == -1){
				colm = matrix[0].length -1;
				row = row-1;
			}
			//Log.d("RAJAT"," Set to zero updateInitialMatrix :  row :  "+row+" colmn : "+colm);
			matrix[row][colm] = 0;
		}
		generateMatrix();
	}

	public  static void generateMatrix(){
		int v=matrix.length * matrix[0].length;
		graph = new int[v][v];
		graph1 = new int[v][v];

		rowSize = matrix.length;
		colSize = matrix[0].length;
		int current =-1;
		//int source,source1;
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				current = colSize*i +j;
				if(j-1>=0 && matrix[i][j-1]==1 && matrix[i][j]==1){
					graph[current][colSize*i + j-1]=1;
				}
				if(j+1<colSize  && matrix[i][j+1]==1 && matrix[i][j]==1){
					graph[current][colSize*i + j+1]=1;
				}

				if(i-1>=0  && matrix[i-1][j]==1 && matrix[i][j]==1){
					graph[current][colSize*(i-1) + j]=1;
				}
				if(i+1<rowSize  && matrix[i+1][j]==1 && matrix[i][j]==1){
					graph[current][colSize*(i+1) + j]=1;
				}
				if(i%2==0){
					if(i-1>=0  && j-1>=0 && matrix[i-1][j-1]==1 && matrix[i][j]==1){
						graph[current][colSize*(i-1) + j-1]=1;
					}

					if(i+1<rowSize  && j-1>=0 && matrix[i+1][j-1]==1 && matrix[i][j]==1){
						graph[current][colSize*(i+1) + j-1]=1;
					}
				}

				if(i%2==1){
					if(i-1>=0  && j+1<colSize && matrix[i-1][j+1]==1 && matrix[i][j]==1){
						graph[current][colSize*(i-1) + j+1]=1;
					}

					if(i+1<rowSize  && j+1<colSize && matrix[i+1][j+1]==1 && matrix[i][j]==1){
						graph[current][colSize*(i+1) + j+1]=1;
					}
				}
			}
		}
		graph1= graph;

		int source = HexagonView.beeOneCurrPosition;
		DijkstraAlgorithmSet.startDijk(rowSize, colSize, source, graph);

		if(HexagonView.beeCount == 1)
		{
			int source1 = HexagonView.beeTwoCurrPosition;
			DijkstraAlgorithmSet_1.startDijk(rowSize, colSize, source1, graph1);
		}

	}
	public static void startAlgorithm(int firstBeeNextStep) {
		int source = HexagonView.beeOneCurrPosition;
		int source1 = HexagonView.beeTwoCurrPosition;
		
		//Log.d("RAJAT","Value of source :"+firstBeeNextStep+"      source"+source+"            source1"+source1);
		
		//printPath(graph1);
		
		//Log.d("RAJAT"," **************************************************************************** ");
		
		graph1[source1-1][firstBeeNextStep-1] = 0;
		
		//printPath(graph1);
		
		DijkstraAlgorithmSet_1.startDijk(rowSize, colSize, source1, graph1);
		
		//Log.d("RAJAT","DijkstraAlgorithmSet_1.getNextStep() 1 : "+DijkstraAlgorithmSet_1.getNextStep());
	}
}
