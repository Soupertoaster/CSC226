/* MWST.java
   CSC 226 - Winter 2017
   Assignment 2 - Template for a Minimum Weight Spanning Tree algorithm

   Original:
   CSC 225 - Spring 2012
   Assignment 5 - Template for a Minimum Weight Spanning Tree algorithm

   The assignment is to implement the mwst() method below, using any of the algorithms
   studied in the course (Kruskal, Prim-Jarnik or Baruvka). The mwst() method computes
   a minimum weight spanning tree of the provided graph and returns the total weight
   of the tree. To receive full marks, the implementation must run in O(mlog(n)) time
   on a graph with n vertices and m edges.

   This template includes some testing code to help verify the implementation.
   Input graphs can be provided with standard input or read from a file.
   
   To provide test inputs with standard input, run the program with
	java MWST
   To terminate the input, use Ctrl-D (which signals EOF).
   
   To read test inputs from a file (e.g. graphs.txt), run the program with
    java MWST graphs.txt
	
   The input format for both methods is the same. Input consists
   of a series of graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   For example, a path on 3 vertices where one edge has weight 1 and the other
   edge has weight 2 would be represented by the following
   
    3
	0 1 0
	1 0 2
	0 2 0
	
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
   B. Bird - 03/11/2012
*/

import java.util.Scanner;
import java.io.File;
import java.util.PriorityQueue;

public class MWST2{


	/* mwst(G)
		Given an adjacency matrix for graph G, return the total weight
		of all edges in a minimum weight spanning tree.
		
		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static int mwst(int[][] G){
		int numVerts = G.length;
		int numEdges = numVerts-1;
		int[] visited = new int[numVerts];
		int min;
		int u = 0; 
		int v = 0;
		int totalWeight = 0;

		for(int i = 0; i < numVerts; i++) {
			visited[i] = 0;
			for(int j = 0; j < numVerts; j++) {
				if(G[i][j] == 0) {
					G[i][j] = Integer.MAX_VALUE;
				}
			}
		}
		visited[0] = 1;
		for(int counter = 0; counter < numEdges; counter++) {
			min = Integer.MAX_VALUE;
			for(int i = 0; i < numVerts; i++) {
				if(visited[i] == 1) {
					for(int j = 0; j < numVerts; j++) {
						if(visited[j] != 1) {
							if(min > G[i][j]) {
								min = G[i][j];
								u = i;
								v = j;
							}
						}
					}
				}
			}
			visited[v] = 1;
			totalWeight += min;
		}
		return totalWeight;	
	}


	public static void main(String[] args){
		/* Code to test your implementation */
		/* You may modify this, but nothing in this function will be marked */

		int graphNum = 0;
		Scanner s;

		if (args.length > 0){
			//If a file argument was provided on the command line, read from the file
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			//Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(!s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				G[i] = new int[n];
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			if (!isConnected(G)){
				System.out.printf("Graph %d is not connected (no spanning trees exist...)\n",graphNum);
				continue;
			}
			int totalWeight = mwst(G);
			System.out.printf("Graph %d: Total weight is %d\n",graphNum,totalWeight);
				
		}
	}

	/* isConnectedDFS(G, covered, v)
	   Used by the isConnected function below.
	   You may modify this, but nothing in this function will be marked.
	*/
	static void isConnectedDFS(int[][] G, boolean[] covered, int v){
		covered[v] = true;
		for (int i = 0; i < G.length; i++)
			if (G[v][i] > 0 && !covered[i])
				isConnectedDFS(G,covered,i);
	}
	   
	/* isConnected(G)
	   Test whether G is connected.
	   You may modify this, but nothing in this function will be marked.
	*/
	static boolean isConnected(int[][] G){
		boolean[] covered = new boolean[G.length];
		for (int i = 0; i < covered.length; i++)
			covered[i] = false;
		isConnectedDFS(G,covered,0);
		for (int i = 0; i < covered.length; i++)
			if (!covered[i])
				return false;
		return true;
	}

}