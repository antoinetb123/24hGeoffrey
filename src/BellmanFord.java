
// A Java program for Bellman-Ford's single source shortest path 
//algorithm.

//A class to represent a connected, directed and weighted graph 
class BellmanFord {
	// A class to represent a weighted edge in graph
	class Edge {
		int src, dest, weight;

		Edge() {
			src = dest = weight = 0;
		}

		Edge(int src, int dest, int weight) {
			this.src = src;
			this.dest = dest;
			this.weight = weight;
		}

	};

	int vertices, edges;
	Edge edgesArray[];

	// Creates a graph with V vertices and E edges
	BellmanFord(int v, int e) {
		vertices = v;
		edges = e;
		edgesArray = new Edge[e];
		for (int i = 0; i < e; ++i)
			edgesArray[i] = new Edge();
	}

	public BellmanFord(int[][] tab) {
		int length = tab.length;
		int height = tab[0].length;
		vertices = length * height;
		edges = (length - 1) * height * 4;
		edgesArray = new Edge[edges];
		int counter = 0;

		for (int x = 0; x < tab.length; x++) {
			for (int y = 0; y < tab[x].length; y++) {
				int temp = x * length + y;
				if (y != tab.length - 1) {
					edgesArray[counter++] = new Edge(temp, temp + 1, 1);
					edgesArray[counter++] = new Edge(temp + 1, temp, 1);
				}
				if (x != tab[x].length - 1) {
					edgesArray[counter++] = new Edge(temp, temp + length, 1);
					edgesArray[counter++] = new Edge(temp + length, temp, 1);
				}
			}
		}
	}

	// The main function that finds shortest distances from src
	// to all other vertices using Bellman-Ford algorithm. The
	// function also detects negative weight cycle
	int[] bellmanFord(BellmanFord graph, int src) {
		int V = graph.vertices, E = graph.edges;
		int dist[] = new int[V];

		// Step 1: Initialize distances from src to all other
		// vertices as INFINITE
		for (int i = 0; i < V; ++i)
			dist[i] = Integer.MAX_VALUE;
		dist[src] = 0;

		// Step 2: Relax all edges |V| - 1 times. A simple
		// shortest path from src to any other vertex can
		// have at-most |V| - 1 edges
		for (int i = 1; i < V; ++i) {
			for (int j = 0; j < E; ++j) {
				int u = graph.edgesArray[j].src;
				int v = graph.edgesArray[j].dest;
				int weight = graph.edgesArray[j].weight;
				if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v])
					dist[v] = dist[u] + weight;
			}
		}

		// Step 3: check for negative-weight cycles. The above
		// step guarantees shortest distances if graph doesn't
		// contain negative weight cycle. If we get a shorter
		// path, then there is a cycle.
		for (int j = 0; j < E; ++j) {
			int u = graph.edgesArray[j].src;
			int v = graph.edgesArray[j].dest;
			int weight = graph.edgesArray[j].weight;
			if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v])
				System.out.println("Graph contains negative weight cycle");
		}
		printArr(dist, V);
		return dist;
	}

	// A utility function used to print the solution
	void printArr(int dist[], int V) {
		System.out.println("Vertex   Distance from Source");
		for (int i = 0; i < V; ++i)
			System.out.println(i + "\t\t" + dist[i]);
	}

	void printBellManFord(int dist[], int V) {
		for (int i = 0; i < V; ++i)
			System.out.println(i + "\t\t" + dist[i]);
	}

	// Driver method to test above function
	public static void main(String[] args) throws InterruptedException {
		// int V = 5; // Number of vertices in graph
		// int E = 8; // Number of edges in graph
		int[][] tab = new int[75][75];
		BellmanFord graph = new BellmanFord(tab);
		// System.out.println(graph.edges);
		long startTime = System.nanoTime();
		graph.bellmanFord(graph, 0);
		System.out.println((System.nanoTime() - startTime) / 1000000);
	}
}
//Contributed by Aakash Hasija 
