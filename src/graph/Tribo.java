package graph;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class Tribo extends SingleGraph {
	
	public Tribo(String nome) {
		super(nome);
		super.setStrict(false);
		this.setNodeFactory(new FabricaDeIndios());
	}
	
	public Indio addIndio(int n) {
		Indio indio = this.addNode(Integer.toString(n));
		indio.setNumero(n);
		return indio;
	}
	
	public void addFilho(Indio mae, Indio filho) {
		this.addEdge(mae.getId()+"_"+filho.getId(), mae, filho, true);
	}
	
	public Indio getIndio(int index) {
		return this.getNode(index);
	}
	
	public Indio getIndio(String id) {
		return this.getNode(id);
	}
	
	public int getMaximoId() {
		int max = 0;
		for(Node indio : this.getEachNode())
			max = ((Indio)indio).getNumero() > max ?
					((Indio)indio).getNumero() : max;
					
		return max;
	}
}
