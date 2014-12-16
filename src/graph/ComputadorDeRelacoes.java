package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.graphstream.graph.Node;

public class ComputadorDeRelacoes {
	
	private Tribo 	tribo;
	private String 	relacoes[][];
	private boolean jaEstaComputado;
	
	public ComputadorDeRelacoes(Tribo tribo) {
		this.tribo = tribo;
		this.setRelacoes();
		this.jaEstaComputado = false;
	}
	
	public void calculaTodasAsRelacoes() {
		
	}
	
	public String getRelacao(int ego, int alter) {
		return null;
	}
	
	private void computaNerani(Indio ego) {
		if(ego.eHomem())
			return;
		
		this.computaNeraniNeraneto(ego, Relacao.NERANI);
	}
	
	private void computaNeraneto(Indio ego) {
		if(ego.eMulher())
			return;
		
		this.computaNeraniNeraneto(ego, Relacao.NERANETO);
	}
	
	private void computaNowatolo(Indio ego) {
		if(ego.eMulher())
			return;
		
		this.computaNowatoreNowatolo(ego, Relacao.NOWATORE);
	}
	
	private void computaNowatore(Indio ego) {
		if(ego.eHomem())
			return;
		
		this.computaNowatoreNowatolo(ego, Relacao.NOWATOLO);
	}
	
	private void computaTawiEkokwe(Indio ego) {
		if(ego.eHomem())
			return;
		this.computeEkokweAkero(ego, "ekokwe");
	}
	
	private void computaNodaAkero(Indio ego) {
		if(ego.eMulher())
			return;
		this.computeEkokweAkero(ego, "akero");
	}

	private void computaConsogros(Indio ego) {
		
		for(Indio filho : ego.getFilhos()) 
			for(Indio conj : filho.getConjuges()) {
				if(conj.getPai() != null)
					this.addRelacao(ego, conj.getPai() , Relacao.NONATONAWENE);
				if(conj.getMae() != null)
					this.addRelacao(ego, conj.getMae() , Relacao.NONATONAWENERO);
			}
	}
	
	private void computaGenroNora(Indio ego) {
		List<Indio> lista = new ArrayList<Indio>();
		Indio parent;
		
		//esposos dos filhos
		for(Indio filho : ego.getFilhos()) lista.addAll(filho.getConjuges());
		
		//pais de netos que não são meu filho
		for(Indio filho : ego.getFilhos())
			for(Indio neto : filho.getFilhos()) {
				parent = neto.getOutroPai(filho);
				if(parent != null) lista.add(parent);
			}
		
		//classifica em genro ou nora
		for(Indio indio : lista)
			if(indio.eMulher()) 
				this.addRelacao(ego, indio, Relacao.NOXINETO);
			else 
				this.addRelacao(ego, indio, Relacao.NOTENE);
	}
	
	private void computaNowatoreNowatolo(Indio ego, Relacao relacao) {
		List<Indio> lista = new ArrayList<Indio>();
		List<Indio> irmaos = (relacao == Relacao.NOWATORE ? ego.getIrmas() : ego.getIrmaos());
		
		for(Indio irmao : irmaos) lista.addAll(irmao.getConjuges());
		
		for(Indio conj :  ego.getConjuges())
			lista.addAll(relacao == Relacao.NOWATORE ? conj.getIrmaos() : conj.getIrmas());
				
		for(Indio alter : lista) this.addRelacao(ego, alter, relacao);
		
	}
	
	private void computeEkokweAkero(Indio ego, String relacao) {
		Indio parent;
		List<Indio> irmaosDoPai;
		List<Indio> irmaos = relacao.equals("ekokwe") ? ego.getIrmaos() : ego.getIrmas();
		
		for(Indio irmao : irmaos)
			for(Indio filho : irmao.getFilhos()) {
				
				parent = filho.getOutroPai(irmao);
				if(parent == null) continue;
				irmaosDoPai = relacao.equals("ekokwe") ? 
						parent.getIrmaos() : parent.getIrmas();
				
				for(Indio pret : irmaosDoPai)
					switch(relacao){
						case "ekokwe":
							if(filho.eHomem())
								this.addRelacao(ego, pret, Relacao.TAWIHIEKOKWE);
							else
								this.addRelacao(ego, pret, Relacao.TAWIROEKOKWE);
							break;
						case "akero":
							if(filho.eHomem())
								this.addRelacao(ego, pret, Relacao.NODAESEAKERO);
							else
								this.addRelacao(ego, pret, Relacao.NODAEXOAKERO);
							break;
					}
			}
	}
	
	private void computaNeraniNeraneto(Indio ego, Relacao relacao) {
		List<Indio> irmaosDoConjuge;
		List<Indio> irmaos;
		
		for(Indio conj : ego.getConjuges()) {
			this.addRelacao(ego, conj, relacao);
			
			irmaosDoConjuge = relacao == Relacao.NERANI ? 
					conj.getIrmaos() : conj.getIrmas();
							
			for(Indio simb : irmaosDoConjuge)
				this.addRelacao(ego, simb, relacao);
		}
		
		irmaos = relacao == Relacao.NERANI ? ego.getIrmas() : ego.getIrmaos();
		
		for(Indio irmao : irmaos)
			for(Indio conj : irmao.getConjuges())
				this.addRelacao(ego, conj, relacao);
	}
	
	public void setRelacoes() {
		int tam = tribo.getMaximoId();
		this.relacoes = new String[tam+1][tam+1];
		for (String[] row: this.relacoes)
		    Arrays.fill(row, "");
	}
	
	private void addRelacao(Indio ego, Indio alter, Relacao relacao) {
		this.relacoes[ego.getNumero()][alter.getNumero()] += relacao.getNome()+" ";
	}

}
