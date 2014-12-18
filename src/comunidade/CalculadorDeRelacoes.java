package comunidade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.graphstream.graph.Node;

public class CalculadorDeRelacoes {
	
	private Tribo 	tribo;
	private String 	relacoes[][];
	private boolean estaCalculado;
	private Map<Indio, Indio> anterior;
	private Map<Indio, Integer>	 niveis;
	
	public CalculadorDeRelacoes(Tribo tribo) {
		this.tribo = tribo;
		this.anterior = new HashMap<Indio, Indio>();
		this.niveis = new HashMap<Indio, Integer>();
		this.setRelacoes();
		this.estaCalculado = false;
	}
	
	public String getRelacao(int ego, int alter) {
		if(!this.estaCalculado) {
			this.calculaTodasAsRelacoes();
			this.estaCalculado = true;
		}
		
		String resp = "";
		try {
			HashSet<String> h = new HashSet<String>(Arrays.asList(relacoes[ego][alter].split(" ")));
			for(String token : h.toArray(new String[0]))
				resp = resp.equals("") ? token : resp+" "+token;
		}
		catch(Exception e) {
			return "";
		}
		
		return resp;
	}
	
	//Gambiarra...
	public Tribo getTribo() {
		return this.tribo;
	}
	
	private void calculaTodasAsRelacoes() {
		
		for(Node indio : tribo.getNodeSet()) {
			this.calculaRelacoesCosanguineas((Indio)indio);
			this.calculaRelacoesDeAfinidade((Indio)indio);
		}
	}
	
	private void calculaRelacoesCosanguineas(Indio ego) {
		
		Indio filhoAnterior;
		boolean cruzado;
		Stack<Indio> pilha = new Stack<Indio>();
		
		for(Indio ancestral : this.getAncestrais(ego)) {
			
			filhoAnterior = this.anterior.get(ancestral);
			for(Indio alter : ancestral.getFilhosExceto(filhoAnterior)) {
				cruzado = false;
				pilha.clear();
				
				if(filhoAnterior != null && 
						alter.getSexo() != filhoAnterior.getSexo()) cruzado = true;
				
				this.niveis.put(alter, this.niveis.get(ancestral)-1);
				pilha.add(alter);
				while(!pilha.isEmpty()) {
					alter = pilha.pop();
					
					if(this.niveis.get(alter) < -2) continue;
					if(alter != ego) classificaCosanguineos(ego, alter, cruzado);
					
					for(Indio filho : alter.getFilhos()) {
						this.niveis.put(filho, this.niveis.get(alter)-1);
						pilha.add(filho);
					}
				}
			}
		}
		niveis.clear();
		anterior.clear();
	}
	
	private void classificaCosanguineos(Indio ego, Indio alter, boolean cruzado) {
		
		switch(this.niveis.get(alter)) {
			case -2:
				if(alter.eMulher())
					this.addRelacao(ego, alter, Relacao.NOXIWETO);
				else
					this.addRelacao(ego, alter, Relacao.NOXIWETE);
				break;
			case -1:
				if(!cruzado)
					this.addRelacao(ego, alter, Relacao.ETAI);
				else if(alter.eMulher()) {
					if(ego.eHomem())
						this.addRelacao(ego, alter, Relacao.NODAIXO);
					else
						this.addRelacao(ego, alter, Relacao.TAWIRO);
				}
				else {
					if(ego.eHomem())
						this.addRelacao(ego, alter, Relacao.NODAISE);
					else
						this.addRelacao(ego, alter, Relacao.TAWIHI);
				}
				break;
			case 0:
				if(alter.eMulher()) {
					if(alter.eMaisVelhoQue(ego))
						this.addRelacao(ego, alter, Relacao.YAYALO);
					else
						this.addRelacao(ego, alter, Relacao.YOWALO);
				}
				else {
					if(!cruzado) {
						if(alter.eMaisVelhoQue(ego))
							this.addRelacao(ego, alter, Relacao.YAYARE);
						else
							this.addRelacao(ego, alter, Relacao.YOWARE);
					}
					else {
						if(ego.eHomem())
							this.addRelacao(ego, alter, Relacao.NOHEROI);
						else if(alter.eMaisVelhoQue(ego))
							this.addRelacao(ego, alter, Relacao.YAYARE);
						else
							this.addRelacao(ego, alter, Relacao.YOWARE);
					}
				}
				break;
			case 1:
				if(!cruzado && alter.eHomem())
					this.addRelacao(ego, alter, Relacao.HAHARE);
				else if(!cruzado && alter.eMulher())
					this.addRelacao(ego, alter, Relacao.MAMALO);
				else if(cruzado && alter.eHomem())
					this.addRelacao(ego, alter, Relacao.KOKORE);
				else if(cruzado && alter.eMulher())
					this.addRelacao(ego, alter, Relacao.KEKERO);
				else
					System.out.println("Algo deu errado ao descer pelo eixo");
				break;
			case 2:
			case 3:
				if(alter.eHomem())
					this.addRelacao(ego, alter, Relacao.ATORE);
				else
					this.addRelacao(ego, alter, Relacao.AHIRO);
				break;
			default:
				if(this.niveis.get(alter) != 4)
					System.out.println("Shouldn't happen");
		}
	}
	
	private List<Indio> getAncestrais(Indio ego) {
		Indio ancestral;
		List<Indio> eixo = new ArrayList<Indio>();
		
		this.niveis.put(ego, 0);
		eixo.add(ego);
		for(int i = 0; i < eixo.size(); i++) {
			ancestral = eixo.get(i);
			if(this.niveis.get(ancestral) > 3) continue;
			
			for(Indio paiDoAncestral : ancestral.getPais()) {
				
				this.anterior.put(paiDoAncestral, ancestral);
				this.niveis.put(paiDoAncestral, this.niveis.get(ancestral)+1);
				classificaCosanguineos(ego, paiDoAncestral, false);//ancestrais são paralelos por definição
				eixo.add(paiDoAncestral);
			}
		}
		return eixo;
	}
	
	private void calculaRelacoesDeAfinidade(Indio ego) {
		this.calculaConsogros(ego);
		this.calculaGenroNora(ego);
		this.calculaNeraneto(ego);
		this.calculaNerani(ego);
		this.calculaNodaAkero(ego);
		this.calculaNowatolo(ego);
		this.calculaNowatore(ego);
		this.calculaSogros(ego);
		this.calculaTawiEkokwe(ego);
	}
	
	private void calculaNerani(Indio ego) {
		if(ego.eHomem())
			return;
		
		this.calculaNeraniNeraneto(ego, Relacao.NERANI);
	}
	
	private void calculaNeraneto(Indio ego) {
		if(ego.eMulher())
			return;
		
		this.calculaNeraniNeraneto(ego, Relacao.NERANETO);
	}
	
	private void calculaNowatolo(Indio ego) {
		if(ego.eMulher())
			return;
		
		this.calculaNowatoreNowatolo(ego, Relacao.NOWATORE);
	}
	
	private void calculaNowatore(Indio ego) {
		if(ego.eHomem())
			return;
		
		this.calculaNowatoreNowatolo(ego, Relacao.NOWATOLO);
	}
	
	private void calculaTawiEkokwe(Indio ego) {
		if(ego.eHomem())
			return;
		this.calculaEkokweAkero(ego, "ekokwe");
	}
	
	private void calculaNodaAkero(Indio ego) {
		if(ego.eMulher())
			return;
		this.calculaEkokweAkero(ego, "akero");
	}

	private void calculaConsogros(Indio ego) {
		
		for(Indio filho : ego.getFilhos()) 
			for(Indio conj : filho.getConjuges()) {
				if(conj.getPai() != null)
					this.addRelacao(ego, conj.getPai() , Relacao.NONATONAWENE);
				if(conj.getMae() != null)
					this.addRelacao(ego, conj.getMae() , Relacao.NONATONAWENERO);
			}
	}
	
	private void calculaGenroNora(Indio ego) {
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
	
	private void calculaNowatoreNowatolo(Indio ego, Relacao relacao) {
		List<Indio> lista = new ArrayList<Indio>();
		List<Indio> irmaos = (relacao == Relacao.NOWATORE ? ego.getIrmas() : ego.getIrmaos());
		
		for(Indio irmao : irmaos) lista.addAll(irmao.getConjuges());
		
		for(Indio conj :  ego.getConjuges())
			lista.addAll(relacao == Relacao.NOWATORE ? conj.getIrmaos() : conj.getIrmas());
				
		for(Indio alter : lista) this.addRelacao(ego, alter, relacao);
		
	}
	
	private void calculaEkokweAkero(Indio ego, String relacao) {
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
	
	private void calculaNeraniNeraneto(Indio ego, Relacao relacao) {
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
	
	private void calculaSogros(Indio ego) {
		Indio parent;
		List<Indio> lista = new ArrayList<Indio>();
		lista.addAll(ego.getConjuges());
		
		for(Indio child : ego.getFilhos()) {
			parent = child.getOutroPai(ego);
			if(parent == null) continue;
			lista.add(parent);
		}
		
		for(Indio indio : lista) {
			if(indio.getPai() != null) 
				this.addRelacao(ego, indio.getPai(), Relacao.NIATOKWE);
			if(indio.getMae() != null) 
				this.addRelacao(ego, indio.getMae(), Relacao.NIASERO);
		}
	}
	
	private void setRelacoes() {
		int tam = tribo.getMaximoId();
		this.relacoes = new String[tam+1][tam+1];
		for (String[] row: this.relacoes)
		    Arrays.fill(row, "");
	}
	
	private void addRelacao(Indio ego, Indio alter, Relacao relacao) {
		this.relacoes[ego.getNumero()][alter.getNumero()] += relacao.getNome()+" ";
	}

}
