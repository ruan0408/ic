package graph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.NodeFactory;

public class FabricaDeIndios implements NodeFactory<Indio> {

	@Override
	public Indio newInstance(String arg0, Graph arg1) {
		return new Indio((Tribo)arg1, arg0);
	}

}
