package srcgraphs;

public class Multi<V,E> extends AdjacencyListGraph<V,E> {

	protected Multi(boolean isDirected, boolean acceptSelfLoops, boolean isWeighted) {
		super(false, isDirected, acceptSelfLoops, isWeighted);
		
	}
	
	
	
	@Override
	public void addEdge(V aVertex, V otherVertex, E theEdge) {

		// validacion y creacion de vertices si fuera necesario
		super.addEdge(aVertex, otherVertex, theEdge);

		// TODO: Completar
	}
	
	
}
