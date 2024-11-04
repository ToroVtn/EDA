package srcgraphs;

import java.lang.reflect.Method;
import java.util.*;

public class SimpleOrDefault<V,E> extends AdjacencyListGraph<V,E> {

	protected SimpleOrDefault(boolean isDirected, boolean acceptSelfLoops, boolean isWeighted) {
		super(true, isDirected, acceptSelfLoops, isWeighted);
	
	}
	
	
	@Override
	public void addEdge(V aVertex, V otherVertex, E theEdge) {

		// validacion y creacion de vertices si fuera necesario
		super.addEdge(aVertex, otherVertex, theEdge);

		if(getAdjacencyList().get(aVertex).contains(new InternalEdge(theEdge, otherVertex))) return;

		getAdjacencyList().get(aVertex).add(new InternalEdge(theEdge, otherVertex));
		if(!isDirected) {
			getAdjacencyList().get(otherVertex).add(new InternalEdge(theEdge, aVertex));
		}
	}

	@Override
	// Dijsktra exige que los pesos sean positivos!!!
	public DijkstraPath<V, E> dijsktra(V source) {
		if (source == null || !existsVertex(source) )
			throw new IllegalArgumentException(Messages.getString("vertexParamError"));

		PriorityQueue<NodePQ> pq= new PriorityQueue<>();

		//stores shortest distance from source to every vertex
		Map<V,Integer> costo = new HashMap<>();
		Map<V,V> prev= new HashMap<>();

		// empieza vacio
		Set<V> nodesVisisted= new HashSet<>();

		// inicializacion+
		for(V aV: getAdjacencyList().keySet() ) {
			if (aV.equals(source)) {
				pq.add(new NodePQ(source, 0));
				costo.put(source, 0);
			}
			else {
				costo.put(aV, Integer.MAX_VALUE);
			}
			prev.put(aV, null);
		}

		while( ! pq.isEmpty()) {
			NodePQ current = pq.poll(); // el menor
			if (nodesVisisted.contains(current.vertex)) // ya lo procese
				continue;

			// a procesarlo! Con esto tambien se ignora self-loop
			nodesVisisted.add(current.vertex);

			// ahora recorrer todos los ejes incidentes a current
			Collection<AdjacencyListGraph<V, E>.InternalEdge> adjList = getAdjacencyList().get(current.vertex);
			for(InternalEdge neighbor: adjList) {

				// si fue visitado seguir. Esto tambien excluye los self loops...
				if ( nodesVisisted.contains(neighbor.target)) {
					continue;
				}

				// invocando a getWeight (se ha validado en insercion)
				int weight=0;
				// peso de ese eje?
				try {
					Method fn = neighbor.edge.getClass().getMethod("getWeight");
					weight = (int) fn.invoke(neighbor.edge);
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
				// verificacion
				if (weight < 0 )
					throw new IllegalArgumentException(
							String.format(Messages.getString("dijkstraWithNegativeWeight"),
									current.vertex, neighbor.target, weight));
				// cual seria el costo de neighbor viniendo desde current?
				int newCosto = costo.get(current.vertex) + weight;
				// es una mejora?
				if (newCosto < costo.get(neighbor.target) ) {
					// insertar neighbor con ese valor mejorado
					costo.put(neighbor.target, newCosto);
					pq.add(new NodePQ(neighbor.target, newCosto));
					// armar camino
					prev.put(neighbor.target, current.vertex);
				}
			}
		}
		return new DijkstraPath<>(costo, prev);
	}

	private boolean existsVertex(V source) {
		return getVertices().contains(source);
	}

	class NodePQ implements Comparable<NodePQ> {
		V vertex;
		Double distance;
		public NodePQ(V vertex, double distance) {
			this.vertex= vertex;
			this.distance= distance;
		}
		@Override
		public int compareTo(NodePQ o2) {
			return Double.compare( distance, o2.distance);
		}
	}

	public void printAllPaths(V startNode, V endNode){
		if(startNode == null || endNode == null || !existsVertex(startNode) || !existsVertex(endNode)) throw new IllegalArgumentException();
		if(acceptSelfLoop) throw new NoSuchElementException("not implemented for self-loop");

		Set<V> visited= new HashSet<>();
		ArrayList<V> path= new ArrayList<>();
		printAllPaths(startNode, endNode, visited, path);
	}

	private void printAllPaths(V startNode, V endNode, Set<V> visited, ArrayList<V> path) {
		path.add(startNode);
		visited.add(startNode);

		if(startNode.equals(endNode)) {
			System.out.println(path);
			visited.remove(endNode);
			path.remove(endNode);
			return;
		}

		Collection<InternalEdge> adjListOther =  getAdjacencyList().get(startNode);
		for(InternalEdge neighbor: adjListOther) {
			if(!visited.contains(neighbor.target)) {
				printAllPaths(neighbor.target, endNode, visited, path);
			}
		}

		visited.remove(startNode);
		path.remove(startNode);
	}

	@Override
	public boolean isBipartite(){
		Map<V, Integer> ABNodes= new HashMap<>();
		Collection<V> vertices = getVertices();
		int label = 0;
		for(V vertex: vertices) {
			for(InternalEdge edge: getAdjacencyList().get(vertex)){
				if(ABNodes.get(edge.target) == null){
                    ABNodes.putIfAbsent(vertex, label);
					ABNodes.put(edge.target, (ABNodes.get(vertex) + 1) % 2);
				}

				if(ABNodes.get(edge.target).equals(ABNodes.get(vertex))){
					return false;
				}
			}
			label = (label + 1) % 2;
		}
		return true;
	}

	//with DFS
	@Override
	public boolean hasCicle(){
		Set<V> visited = new HashSet<>();
		Stack<V> stack = new Stack<>();
		stack.push(getVertices().iterator().next());
		V current, toStack;
		Iterator<InternalEdge> it;
		while(!stack.isEmpty()){
			current = stack.pop();

			if(visited.contains(current)) {
				return true;
			}

			it = getAdjacencyList().get(current).iterator();
			//add nodes to process
			while(it.hasNext()){
				toStack = it.next().target;
				if(!visited.contains(toStack)) {
					stack.push(toStack);
				}
			}

			visited.add(current);
			System.out.printf("%s ", current);
		}
		return false;
	}
}
