package src.algorithm.MST;

import java.util.ArrayList;
import java.util.HashMap;

import src.algorithm.sorting.Sorting;
import src.datastructure.graph.*;
import src.datastructure.unionfind.*;

/**
 * This class contains the implementation of the Kruskal's algorithm for the construction of a Minimum Spanning Tree (MST) of a weighted graph.
 * 
 * @param <D> type of the data stored in the nodes of the graph
 */
public class Kruskal<D> implements MST<D> {

	// The WeightedGraph on which the MST is computed
	private WeightedGraph<D> t;
	
	// The total weight of the MST
	private double weight;

	/** 
	 * Computes the Minimum Spanning Tree (MST) of the specified weighted graph.
	 * 	
	 * @param g the weighted graph
	 */
    public void compute(WeightedGraph<D> g) {
		this.t = new WeightedGraphAL<D>();
		this.weight = 0;
		
		UnionFind<Vertex<D>, QUnode<Vertex<D>>, QUset> u = new QuickUnionRank<Vertex<D>>();
		ArrayList<Vertex<D>> vert = g.vertexes();
		HashMap<Vertex<D>, QUnode<Vertex<D>>> nodes = new HashMap<Vertex<D>, QUnode<Vertex<D>>>();
		HashMap<Vertex<D>, Vertex<D>> copyGtoT = new HashMap<Vertex<D>, Vertex<D>>(); 

		for(int i = 0; i < g.vertexNum(); i++) {
			nodes.put(vert.get(i), u.makeSet(vert.get(i)));
			copyGtoT.put(vert.get(i), this.t.addVertex(vert.get(i).data));
		}

		WeightedEdge<D>[] sortedEdges = g.edges().toArray(new WeightedEdge[g.edgeNum()]);
		Sorting.mergesort(sortedEdges);

		for(WeightedEdge<D> e : sortedEdges) {
			QURset Tu = (QURset) u.find(nodes.get(e.source));
			QURset Tv = (QURset) u.find(nodes.get(e.dest));
			if(!Tu.equals(Tv)) {
				WeightedEdge<D> eNew1 = new WeightedEdge<D>(copyGtoT.get(e.source), copyGtoT.get(e.dest), e.weight);
				WeightedEdge<D> eNew2 = new WeightedEdge<D>(copyGtoT.get(e.dest), copyGtoT.get(e.source), e.weight);
				this.t.addEdge(eNew1);
				this.t.addEdge(eNew2);
				u.union(Tu, Tv);
				this.weight += e.weight;
			}
		}
    }
	
	/**
	 * Returns the Minimum Spanning Tree (MST) of the weighted graph.
	 * 
	 * @return the Minimum Spanning Tree (MST) of the weighted graph
	 */
	public WeightedGraph<D> spanningTree() {
		return this.t;
	}
	
	/**
	 * Returns the total weight of the Minimum Spanning Tree (MST) of the weighted graph.
	 * 
	 * @return the total weight of the Minimum Spanning Tree (MST) of the weighted graph
	 */
	public double totalWeight() {
		return this.weight;	
	}
}