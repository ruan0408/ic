package graph;

import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.implementations.SingleNode;

public class Indio extends SingleNode{
	
	public static final int MASCULINO = 0;
	public static final int FEMININO = 1;
	
	private int numero;
	private Indio 	mae;
	private Indio 	pai;
	private List<Indio> conjuges;
	private int 	anoNascimento;
	private int sexo;
	
	public Indio(SingleGraph graph, String id) {
		super(graph, id);
		this.conjuges = new ArrayList<Indio>();
		this.anoNascimento = 0;
	}

	public Indio getMae() {
		return this.mae;
	}
	
	public Indio getPai() {
		return this.pai;
	}
	
	public int getAnoNascimento() {
		return this.anoNascimento;
	}
	
	public List<Indio> getConjuges() {
		return this.conjuges;
	}
	
	public int getNumero() {
		return this.numero;
	}
	
	public int getSexo() {
		return this.sexo;
	}
	
	public void setMae(Indio mae) {
		this.mae = mae;
	}
	
	public void setPai(Indio pai) {
		this.pai = pai;
	}
	
	public void setSexo(int sexo) {
		this.sexo = sexo;
	}
	
	public void setNumero(int n) {
		this.numero = n;
	}
	
	public boolean eHomem() {
		return this.sexo == Indio.MASCULINO;
	}
	
	public boolean eMulher() {
		return this.sexo == Indio.FEMININO;
	}
	
	public void setAnoNascimento(int ano) {
		this.anoNascimento = ano;
	}
	
	public void addConjuge(Indio conjugue) {
		this.conjuges.add(conjugue);
	}
	
	public boolean eMaisVelhoQue(Indio indio) {
		return this.anoNascimento > indio.getAnoNascimento() ? true : false;
	}
	
	public Indio getOutroPai(Indio parent) {
		return parent == this.getMae() ? this.getPai() : this.getMae();
	}
	
	public List<Indio> getIrmas() {
		return this.getSimblingsOfSex(FEMININO);
	}
	
	public List<Indio> getIrmaos() {
		return this.getSimblingsOfSex(MASCULINO);
	}
	
	public List<Indio> getFilhos() {
		List<Indio> filhos = new ArrayList<Indio>();
		for(Edge e : this.getLeavingEdgeSet()) { 
			if(!e.isDirected()) continue;
			filhos.add((Indio)e.getOpposite(this));
		}		
		return filhos;
	}
	
	private List<Indio> getSimblingsOfSex(int sexo) {
		
		List<Indio> irmaos = new ArrayList<Indio>();
		Indio pai = this.getPai();
		Indio mae = this.getMae();
		Indio indio;
		List<Edge> edges = new ArrayList<Edge>(); 
		if(pai != null) edges.addAll(pai.getLeavingEdgeSet());
		if(mae != null) edges.addAll(mae.getLeavingEdgeSet());
		
		for(Edge e : edges) {
			indio = e.getTargetNode();
			if(!e.isDirected() || indio == this || !(indio.getSexo() == sexo)) 
				continue;
			if(irmaos.contains(indio)) continue;
			irmaos.add(indio);
		}
		return irmaos;
	}
}


