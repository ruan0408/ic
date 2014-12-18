package comunidade;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class Tribo extends SingleGraph {
	
	public Tribo(String nome) {
		super(nome);
		super.setStrict(false);
		this.setNodeFactory(new FabricaDeIndios());
		this.addAttribute("ui.quality");
		this.addAttribute("ui.antialias");
		this.addAttribute("ui.stylesheet","edge { fill-color: gray; arrow-size: 4px, 4px;}");
		this.addAttribute("ui.stylesheet","node { fill-color: gray; size:10px;}");
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
	}
	
	public Indio addIndio(int n) {
		Indio indio = this.addNode(Integer.toString(n));
		indio.setNumero(n);
		return indio;
	}
	
	public void addFilho(Indio mae, Indio filho) {
		this.addEdge(mae.getId()+"_"+filho.getId(), mae, filho, true);
	}
	
	public void addCasamento(int conj1, int conj2) {
		Indio indio1 = this.addIndio(conj1);
		Indio indio2 = this.addIndio(conj2);
		indio1.addConjuge(indio2);
		indio2.addConjuge(indio1);
		this.addEdge(conj1+"_"+conj2, indio1, indio2, false).addAttribute("ui.hide");
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
