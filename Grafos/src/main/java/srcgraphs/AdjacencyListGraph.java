package srcgraphs;

import java.util.*;

abstract public class AdjacencyListGraph<V, E> implements GraphService<V, E> {

	private boolean isSimple;
	protected boolean isDirected;
	protected boolean acceptSelfLoop;
	private boolean isWeighted;
	protected String type;
	
	// HashMap no respeta el orden de insercion. En el testing considerar eso
	private Map<V,Collection<InternalEdge>> adjacencyList= new HashMap<>();
	
	// respeta el orden de llegada y facilita el testing
	//	private Map<V,Collection<InternalEdge>> adjacencyList= new LinkedHashMap<>();
	
	protected   Map<V,  Collection<InternalEdge>> getAdjacencyList() {
		return adjacencyList;
	}
	
	
	protected AdjacencyListGraph(boolean isSimple, boolean isDirected, boolean acceptSelfLoop, boolean isWeighted) {
		this.isSimple = isSimple;
		this.isDirected = isDirected;
		this.acceptSelfLoop= acceptSelfLoop;
		this.isWeighted = isWeighted;

		this.type = String.format("%s %s %sGraph %s",
				isSimple ? "Simple" : "Multi", isWeighted ? "Weighted" : "",
				isDirected ? "Di" : "", acceptSelfLoop? "with SelfLoop":"");
	}
	
	
	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public void addVertex(V aVertex) {
	
		if (aVertex == null ) throw new IllegalArgumentException(Messages.getString("addVertexParamCannotBeNull"));
	
		// no edges yet
		getAdjacencyList().putIfAbsent(aVertex, 
				new ArrayList<>());
	}

	
	@Override
	public int numberOfVertices() {
		return getVertices().size();
	}

	@Override
	public Collection<V> getVertices() {
		return getAdjacencyList().keySet() ;
	}
	
	@Override
	public int numberOfEdges() {
		// COMPLETAR
		throw new RuntimeException("not implemented yet");
	}

	

	@Override
	public void addEdge(V aVertex, V otherVertex, E theEdge) {

		// validation!!!!
		if (aVertex == null || otherVertex == null || theEdge == null)
			throw new IllegalArgumentException(Messages.getString("addEdgeParamCannotBeNull"));

		// es con peso? debe tener implementado el metodo double getWeight()
		if (isWeighted) {
			// reflection
			Class<? extends Object> c = theEdge.getClass();
			try {
				c.getDeclaredMethod("getWeight");
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(
						type + " is weighted but the method double getWeighed() is not declared in theEdge");
			}
		}
		
		if (! acceptSelfLoop && aVertex.equals(otherVertex)) {
			throw new RuntimeException(String.format("%s does not accept self loops between %s and %s" , 
					type, aVertex, otherVertex) );
		}

		// if any of the vertex is not presented, the node is created automatically
		addVertex(aVertex);
		addVertex(otherVertex);
		

	}

	@Override
	public boolean removeVertex(V aVertex) {
		if(aVertex == null || !adjacencyList.containsKey(aVertex)) return false;

		adjacencyList.remove(aVertex);
		Set<V> vertices = adjacencyList.keySet();
		Iterator<V> it = vertices.iterator();
		V current;
		InternalEdge edge = new InternalEdge(null, aVertex);
		while (it.hasNext()) {
			current = it.next();
			adjacencyList.get(current).remove(edge);
		}
		return true;
	}

	@Override
	public boolean removeEdge(V aVertex, V otherVertex) {
		if(!adjacencyList.containsKey(aVertex) || !adjacencyList.containsKey(otherVertex)) return false;

		adjacencyList.get(aVertex).remove(new InternalEdge(null, otherVertex));
		if(!isDirected) return true;

		adjacencyList.get(otherVertex).remove(new InternalEdge(null, aVertex));
		return true;
	}

	
	@Override
	public boolean removeEdge(V aVertex, V otherVertex, E theEdge) {
		if(!adjacencyList.containsKey(aVertex) || !adjacencyList.containsKey(otherVertex)) return false;

		adjacencyList.get(aVertex).remove(new InternalEdge(theEdge, otherVertex));
		if(!isDirected) return true;

		adjacencyList.get(otherVertex).remove(new InternalEdge(theEdge, aVertex));
		return true;
	}

	
	@Override
	public void dump() {
		// COMPLETAR
		throw new RuntimeException("not implemented yet");
	}

	
	@Override
	public int degree(V aVertex) {
		if(aVertex == null) throw new IllegalArgumentException();
		return adjacencyList.getOrDefault(aVertex, new ArrayList<>()).size();
	}



	@Override
	public int inDegree(V aVertex) {
		Set<V> vertices = adjacencyList.keySet();
		Iterator<V> it = vertices.iterator();
		V current;
		InternalEdge edge = new InternalEdge(null, aVertex);
		int deg = 0;
		while(it.hasNext()) {
			current = it.next();
			if(!current.equals(aVertex)) {
				if(getAdjacencyList().get(current).contains(edge)){
					deg++;
				}
			}
		}
		return deg;
	}



	@Override
	public int outDegree(V aVertex) {
		return adjacencyList.getOrDefault(aVertex, new ArrayList<>()).size();
	}

	@Override
	public void printBFS(V aVertex){
		if(!adjacencyList.containsKey(aVertex)) return;
		Map<V, Boolean> visited = new HashMap<>();
		Queue<V> queue = new LinkedList<>();
		queue.add(aVertex);
		V current, toQueue;
		Iterator<InternalEdge> it;
		while(!queue.isEmpty()){
			current = queue.remove();
			it = adjacencyList.get(current).iterator();

			//add nodes to process
			while(it.hasNext()){
				toQueue = it.next().target;
				if(!visited.getOrDefault(toQueue, false)) {
					queue.add(toQueue);
				}
			}

			visited.put(current, true);
			System.out.printf("%s ", current);
		}
	}

	@Override
	public void printDFS(V aVertex){
		if(!adjacencyList.containsKey(aVertex)) return;
		Set<V> visited = new HashSet<>();
		Stack<V> stack = new Stack<>();
		stack.push(aVertex);
		V current, toStack;
		Iterator<InternalEdge> it;
		while(!stack.isEmpty()){
			current = stack.pop();
			it = adjacencyList.get(current).iterator();

			//add nodes to process
			while(it.hasNext()) {
				toStack = it.next().target;
				if (!visited.contains(toStack)) {
					stack.push(toStack);
				}
			}

			if(!visited.contains(current)) {
				visited.add(current);
				System.out.printf("%s ", current);
			}
		}
	}

	@Override
	public Iterator<V> DFSIterator(V aVertex){
		if(!adjacencyList.containsKey(aVertex)) return null;

		Stack<V> stack = new Stack<>();
		stack.push(aVertex);

		return new Iterator<V>() {
			Set<V> visited = new HashSet<>();
			Iterator<InternalEdge> it;
			V current = aVertex;
			V toStack;
			@Override
			public boolean hasNext() {
				return !stack.isEmpty();
			}

			@Override
			public V next() {
				current = stack.pop();

				it = adjacencyList.get(current).iterator();
				while(it.hasNext()){
					toStack = it.next().target;
					if(!visited.contains(toStack)) {
						stack.push(toStack);
					}
				}

				visited.add(current);
				return current;
			}
		};
	}
	
	class InternalEdge {
		E edge;
		V target;

		InternalEdge(E propEdge, V target) {
			this.target = target;
			this.edge = propEdge;
		}

		@Override
		public boolean equals(Object obj) {
			@SuppressWarnings("unchecked")
			InternalEdge aux = (InternalEdge) obj;

			return ((edge == null && aux.edge == null) || (edge != null && edge.equals(aux.edge)))
					&& target.equals(aux.target);
		}

		@Override
		public int hashCode() {
			return target.hashCode();
		}

		@Override
		public String toString() {
			return String.format("-[%s]-(%s)", edge, target);
		}
	}
	
	
}
