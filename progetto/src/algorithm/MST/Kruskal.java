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
		UnionFind u = new QuickUnionRank<Vertex<D>>();
		ArrayList<Vertex<D>> vert = g.vertexes();
		HashMap<Vertex<D>, QUnode<Vertex<D>>> nodes = new HashMap<Vertex<D>, QUnode<Vertex<D>>>();

		for(int i = 0; i < g.vertexNum(); i++) {
			nodes.put(vert.get(i), (QUnode)u.makeSet(vert.get(i)));
		}

		WeightedEdge<D>[] sortedEdges = g.edges().toArray(new WeightedEdge[g.edgeNum()]);
		Sorting.mergesort(sortedEdges);

		for(WeightedEdge<D> e : sortedEdges) {
			QUset Tu = (QUset) u.find(nodes.get((VertexAL<D>)e.source));
			QUset Tv = (QUset) u.find(nodes.get((VertexAL<D>)e.dest));
			if(!Tu.equals(Tv)) {
				this.t.addVertex(e.source.data);
				this.t.addVertex(e.dest.data);
				this.t.addEdge(e);
				u.union(Tu, Tv);
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