package srcgraphs;

import java.util.Collection;
import java.util.Map;

public class Multi<V,E> extends AdjacencyListGraph<V,E> {

	protected Multi(boolean isDirected, boolean acceptSelfLoops, boolean isWeighted) {
		super(false, isDirected, acceptSelfLoops, isWeighted);
	}
	
	
	
	@Override
	public void addEdge(V aVertex, V otherVertex, E theEdge) {

		// validacion y creacion de vertices si fuera necesario
		super.addEdge(aVertex, otherVertex, theEdge);

		getAdjacencyList().get(aVertex).add(new InternalEdge(theEdge, otherVertex));
		if(!isDirected){
			getAdjacencyList().get(otherVertex).add(new InternalEdge(theEdge, aVertex));
		}
	}

	public DijkstraPath<V,E> dijsktra(V source) {
		throw new RuntimeException(Messages.getString("dijkstraNotForMulti"));
	}

	@Override
	public boolean isBipartite(){
		throw new RuntimeException(Messages.getString("isBipartiteNotForMulti"));
	}

	@Override
	public boolean hasCicle(){
		throw new RuntimeException(Messages.getString("hasCicleNotForMulti"));
	}
}
