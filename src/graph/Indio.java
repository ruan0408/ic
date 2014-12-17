package graph;

import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleNode;

public class Indio extends SingleNode{
	
	public static final int MASCULINO = 0;
	public static final int FEMININO = 1;
	
	private int 	numero;
	private Tribo 	tribo;
	private Indio 	mae;
	private Indio 	pai;
	private String 	cla;
	private String 	subCla;
	private List<Indio> conjuges;
	private int 	anoNascimento;
	private int 	anoMorte;
	private int 	sexo;
	
	public Indio(Tribo tribo, String id) {
		super(tribo, id);
		this.conjuges = new ArrayList<Indio>();
		this.anoNascimento = 0;
		this.tribo = tribo;
	}

	public Indio getMae() {
		return this.mae;
	}
	
	public Indio getPai() {
		return this.pai;
	}
	
	public String getCla() {
		return this.cla;
	}
	
	public String getSubCla() {
		return this.subCla;
	}
	
	public int getAnoNascimento() {
		return this.anoNascimento;
	}
	
	public int getAnoMorte() {
		return this.anoMorte;
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
	
	public void setMae(int mae) {
		this.setMae(this.tribo.addIndio(mae));
		this.tribo.addFilho(this.mae, this);
	}
	
	public void setPai(int pai) {
		this.setPai(this.tribo.addIndio(pai));
		this.tribo.addFilho(this.pai, this);
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
	
	public void setCla(String cla) {
		this.cla = cla;
	}
	
	public void setSubCla(String subCla) {
		this.subCla = subCla;
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
	
	public void setAnoMorte(int ano) {
		this.anoMorte = ano;
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
	
	public List<Indio> getFilhosExceto(Indio filho) {
		List<Indio> filhos = this.getFilhos();
		filhos.remove(filho);
		return filhos;
	}
	
	public List<Indio> getPais() {
		List<Indio> pais = new ArrayList<Indio>();
		if(this.getPai() != null) pais.add(this.getPai());
		if(this.getMae() != null) pais.add(this.getMae());
		return pais;
	}
	
	public void constroi(String sexo, String cla, 
			String subCla, String anoNascimento, String anoMorte, String pai, String mae) {
		
		int meuPai, minhaMae;
		if(sexo.equals("f")) this.setSexo(FEMININO);
		else this.setSexo(MASCULINO);
		
		this.setCla(cla);
		this.setSubCla(subCla);
		this.setAnoNascimento(Integer.parseInt(anoNascimento));
		this.setAnoMorte(Integer.parseInt(anoMorte));
		
		meuPai = Integer.parseInt(pai);
		minhaMae = Integer.parseInt(mae);
		if(meuPai != 0) this.setPai(meuPai);
		if(minhaMae != 0) this.setMae(minhaMae);
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


