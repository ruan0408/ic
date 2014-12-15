import java.util.*;
import org.graphstream.graph.*;

public class Layer {	
	private ArrayList<Node> nodes;
	private int layerIndex;
	
	public Layer(int index) {
		this.nodes = new ArrayList<Node>();
		this.layerIndex = index;
	}
	
	public void addNode(Node node) {
		this.nodes.add(node);
	}
	
	public void setCoordinates() {	
		double i = -this.nodes.size()/2;
		for(Node n : this.nodes) {
			n.setAttribute("x", i++);
			n.setAttribute("y", -10*layerIndex);
		}
	}
	//Ordena l1 usando o método do baricentro
	public void sortVertices() {
		double soma;
		int i;
		Node nei;
		Edge e;
		for(Node n : this.nodes) {
			soma = 0; i = 0;
			for(Iterator<Edge> viz = n.getLeavingEdgeIterator(); viz.hasNext();) {
				e = viz.next();
				if(!e.isDirected()) continue;
				
				nei = e.getTargetNode();
				soma += (double)nei.getAttribute("x");
				i++;
			}
			if(i != 0)
				n.setAttribute("x", soma/i);
			else {
				soma = 0; i = 0;
				for(Iterator<Edge> viz = n.getEnteringEdgeIterator(); viz.hasNext();) {
					e = viz.next();
					if(!e.isDirected()) continue;
					
					nei = e.getSourceNode();
					soma += (double)nei.getAttribute("x");
					i++;
				}
				if(i != 0)
					n.setAttribute("x", soma/i);
				else
					n.setAttribute("x", 0);	
			}
		}
	}
}
