package graph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.NodeFactory;
import org.graphstream.graph.implementations.SingleGraph;

public class FabricaDeIndios implements NodeFactory<Indio> {

	@Override
	public Indio newInstance(String arg0, Graph arg1) {
		return new Indio((SingleGraph)arg1, arg0);
	}

}
