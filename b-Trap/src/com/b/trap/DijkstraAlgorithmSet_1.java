package com.b.trap;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import android.util.Log;

public class DijkstraAlgorithmSet_1
{
	//private ArrayList<DistnaceFirstStep> mdistnaceFirstStep;
	private int distances[];
	private Set<Integer> settled;
	private Set<Integer> unsettled;
	private int number_of_nodes;
	private static int number_of_rows;
	private static int number_of_columns;
	private static int adjacencyMatrix[][];
	private static int parent[];
	//private Stack<Integer> path = new Stack<Integer>();
	private static int nextStep = -1;
	public static ArrayList<Integer> exits;
	public static int nearestExit;

	public static int getNextStep() {
		return nextStep;
	}

	public DijkstraAlgorithmSet_1(int number_of_nodes)
	{
		this.number_of_nodes = number_of_nodes;
		distances = new int[number_of_nodes + 1];
		settled = new HashSet<Integer>();
		unsettled = new HashSet<Integer>();
		adjacencyMatrix = new int[number_of_nodes + 1][number_of_nodes + 1];
		parent = new int[number_of_nodes + 1];
		//mdistnaceFirstStep = new ArrayList<DistnaceFirstStep>();
	}

	public void dijkstra_algorithm(int adjacency_matrix[][], int source)
	{
		int evaluationNode;
		for (int i = 1; i <= number_of_nodes; i++)
			for (int j = 1; j <= number_of_nodes; j++)
				adjacencyMatrix[i][j] = adjacency_matrix[i][j];

		for (int i = 1; i <= number_of_nodes; i++)
		{
			distances[i] = Integer.MAX_VALUE;
			//mdistnaceFirstStep.add(new DistnaceFirstStep(Integer.MAX_VALUE, -1, i));
		}

		unsettled.add(source);
		distances[source] = 0;
		//mdistnaceFirstStep.add(new DistnaceFirstStep(0, -1, source));
		while (!unsettled.isEmpty())
		{
			evaluationNode = getNodeWithMinimumDistanceFromUnsettled();
			unsettled.remove(evaluationNode);
			settled.add(evaluationNode);
			evaluateNeighbours(evaluationNode);
		} 
	}


	private void evaluateNeighbours(int evaluationNode)
	{
		int edgeDistance = -1;
		int newDistance = -1;

		for (int destinationNode = 1; destinationNode <= number_of_nodes; destinationNode++)
		{
			//Log.d("RAJAT"," adjacencyMatrix["+evaluationNode+"]["+destinationNode+"] : "+adjacencyMatrix[evaluationNode][destinationNode]);
			if (!settled.contains(destinationNode))
			{	
				//Log.d("RAJAT","destinationNode node is in settled ");
				if (adjacencyMatrix[evaluationNode][destinationNode] > 0 /*!= Integer.MAX_VALUE*/)
				{
					//Log.d("RAJAT","destinationNode node is not infinite ");
					edgeDistance = adjacencyMatrix[evaluationNode][destinationNode];
					newDistance = distances[evaluationNode] + edgeDistance;
					if (newDistance < distances[destinationNode])
					{
						distances[destinationNode] = newDistance;
						parent[destinationNode] = evaluationNode;
						//mdistnaceFirstStep.add(new DistnaceFirstStep(newDistance, evaluationNode, destinationNode));
					}
					unsettled.add(destinationNode);
				}
			}
		}
	}


	public void findShortestPath(int adjacency_matrix[][], int source , int dest) {
		//Log.d("RAJAT","findShortestPath");
		if(dest == source){
			//System.out.println(source);
			//Log.d("RAJAT","dest == source ");
		}
		else if(parent[dest] == Integer.MAX_VALUE) {
			//System.out.println("no path from” s “to”  “exists");
			//Log.d("RAJAT","Integer.MAX_VALUE ");
		}
		else {
			findShortestPath(adjacency_matrix, source , parent[dest]);
			//System.out.println(dest);
			//Log.d("RAJAT","nextStep in path : "+dest);
			if(nextStep == -1){
				nextStep = dest;

			}
		}
	}


	private int getNodeWithMinimumDistanceFromUnsettled()
	{
		int min ;
		int node = 0;

		Iterator<Integer> iterator = unsettled.iterator();
		node = iterator.next();
		min = distances[node];
		for (int i = 1; i <= distances.length; i++)
		{
			if (unsettled.contains(i))
			{
				if (distances[i] <= min)
				{
					min = distances[i];
					node = i;			
				}
			}
		}
		return node;
	}


	public static void startDijk(int row, int column, int source, int [][] graph)
	{
		int adjacency_matrix[][];
		int number_of_vertices;
		int mousePosition;
		int min;
		
		exits = new ArrayList<Integer>();
		//Scanner scan = new Scanner(System.in);

		mousePosition =  source;

		/*System.out.println("Enter the number of rows");
        number_of_rows = scan.nextInt();*/

		number_of_rows = row;

		/*System.out.println("Enter the number of Columns");
        number_of_columns = scan.nextInt();*/

		number_of_columns = column;

		number_of_vertices = number_of_rows*number_of_columns;

		for(int i=1;i<=number_of_vertices;i++){

			if(i<=number_of_columns || i%number_of_columns == 0 || i%number_of_columns == 1 || i/number_of_columns == (number_of_rows-1))
				exits.add(i);

		}

		adjacency_matrix = new int[number_of_vertices +1 ][number_of_vertices + 1];



		//System.out.println("Enter the Weighted Matrix for the graph");
				for (int i = 1; i <= number_of_vertices; i++)
				{
					for (int j = 1; j <= number_of_vertices; j++)
					{
						adjacency_matrix[i][j] = graph[i-1][j-1];
						/*if (i == j)
						{
							adjacency_matrix[i][j] = 0;
							continue;
						}
						if (adjacency_matrix[i][j] == 0)
						{
							adjacency_matrix[i][j] =  Integer.MAX_VALUE;
						}*/
					}
				}

				//System.out.println("Enter the source ");
				//source = scan.nextInt();

				DijkstraAlgorithmSet_1 dijkstrasAlgorithm = new DijkstraAlgorithmSet_1(number_of_vertices);
				dijkstrasAlgorithm.dijkstra_algorithm(adjacency_matrix, mousePosition);

				//System.out.println("The Shorted Path to all nodes are ");
				min = Integer.MAX_VALUE;
				nearestExit = -1;
				for (int i = 1; i <= dijkstrasAlgorithm.distances.length - 1; i++)
				{
					//System.out.println(source + " to " + i + " is "+ dijkstrasAlgorithm.distances[i]);

					if (dijkstrasAlgorithm.distances[i] < min && exits.contains(i))
					{
						//Log.d("RAJAT","dijkstrasAlgorithm.distances[i] < min && exits.contains(i) ");
						//System.out.println(source + " to " + i + " is "+ dijkstrasAlgorithm.distances[i]);
						min = dijkstrasAlgorithm.distances[i];
						nearestExit = i;
					}

				}
				//System.out.println("Nearest exit "+nearestExit);

				if(nearestExit!=-1) {
					nextStep = -1;
					dijkstrasAlgorithm.findShortestPath(adjacency_matrix, source, nearestExit);
				}

				if(nearestExit == -1){
					/*for (int i = 1; i <= dijkstrasAlgorithm.distances.length - 1; i++)
						//System.out.println(source + " to " + i + " is "+ dijkstrasAlgorithm.distances[i]);
						Log.d("RAJAT","  "+source+  "  to  " +i+"  is  "+dijkstrasAlgorithm.distances[i]);
					for (int i = 1; i <= exits.size() - 1; i++)
						Log.d("RAJAT"," It is one of the exits:   "+exits.get(i));*/

				}

				//scan.close();
	}



	class DistnaceFirstStep {
		int distance;
		int firstStep;
		int destination;
		public DistnaceFirstStep(int distance, int firstStep, int destination) {
			super();
			this.distance = distance;
			this.firstStep = firstStep;
			this.destination = destination;
		}
	}
}
