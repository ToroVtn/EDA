package srcgraphs;

public class SimpleOrDefault<V,E> extends AdjacencyListGraph<V,E> {

	protected SimpleOrDefault(boolean isDirected, boolean acceptSelfLoops, boolean isWeighted) {
		super(true, isDirected, acceptSelfLoops, isWeighted);
	
	}
	
	
	@Override
	public void addEdge(V aVertex, V otherVertex, E theEdge) {

		// validacion y creacion de vertices si fuera necesario
		super.addEdge(aVertex, otherVertex, theEdge);

		// TODO: Completar	
	}


}
