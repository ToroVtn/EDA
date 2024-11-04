import srcgraphs.EmptyEdgeProp;
import srcgraphs.GraphBuilder;
import srcgraphs.GraphService;

import java.util.Iterator;

public class Proof {
    public static void main(String[] args) {
        GraphService<String, EmptyEdgeProp> graph = new GraphBuilder<String, EmptyEdgeProp>().build();

        graph.addEdge("A", "B", new EmptyEdgeProp());
        graph.addEdge("B", "C", new EmptyEdgeProp());
        graph.addEdge("C", "D", new EmptyEdgeProp());
        graph.addEdge("D", "E", new EmptyEdgeProp());
        graph.addEdge("E", "A", new EmptyEdgeProp());

        System.out.println(graph.hasCicle());

        GraphService<String, EmptyEdgeProp> graph2 = new GraphBuilder<String, EmptyEdgeProp>().build();
        graph2.addEdge("A", "B", new EmptyEdgeProp());
        graph2.addEdge("B", "C", new EmptyEdgeProp());
        graph2.addEdge("C", "D", new EmptyEdgeProp());

        System.out.println(graph2.hasCicle());
    }
}
