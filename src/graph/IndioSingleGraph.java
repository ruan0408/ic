package graph;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.*;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;


public class IndioSingleGraph extends SingleGraph {
	
	private String[][] relations;//relations[ego][alter] tells how ego calls alter
	private boolean isReady;
	
	public IndioSingleGraph(String id) {
		super(id);
		super.setStrict(false);
		this.isReady = false;
	}
	
	public void setRelations(int tam) {
		this.relations = new String[tam+1][tam+1];
		for (String[] row: this.relations)
		    Arrays.fill(row, "");
	}
	
	public void computeRelations() {
		
		if(this.isReady) return;
		
		Iterator<Node> it = this.getNodeIterator();
		Iterator<Edge> it2, it3;
		Node ego, alter, viz, noEixo;
		Edge edge, e3;
		boolean cruzado = false;
		LinkedList<Node> queue;
		LinkedList<Node> aux;
		
		//para cada indivuduo
		while(it.hasNext()) {
			//System.out.println("NOVO INDIVIDUO");
			ego = it.next();		//não mexa nesse cara, vc vai usar ele em todas as addRelation
			queue = constructAxis(ego);
			//System.out.println("EIXO COMPLETO");
			
			//calculate upper relations
			while(!queue.isEmpty()) {
				noEixo = queue.removeFirst();
				it2 = noEixo.getLeavingEdgeIterator();
				
				while(it2.hasNext()) {
					edge = it2.next();
					aux = new LinkedList<Node>();
					
					//esposo não vale
					if(!edge.isDirected()) continue;
					alter = edge.getOpposite(noEixo);
					
					//Não desça pelo mesmo caminho que vc subiu
					if(noEixo != ego &&
							noEixo.getAttribute("cameFrom").equals(alter.getId())) continue;
						
					
					if(noEixo == ego || 
						noEixo.getAttribute("cameFromSex").toString().equals(alter.getAttribute("sexo").toString()))
						cruzado = false;
					else
						cruzado = true;
					
					alter.setAttribute("nivel", (int)noEixo.getAttribute("nivel")-1);
					
					aux.add(alter);
					while(!aux.isEmpty()) {
						alter = aux.removeLast();
						
						if((int)alter.getAttribute("nivel") < -2) continue;
						if(alter != ego) classifyRelatives(ego, alter, cruzado);
						
						it3 = alter.getLeavingEdgeIterator();
						while(it3.hasNext()) {
							e3 = it3.next();
							if(!e3.isDirected()) continue;
							
							viz = e3.getOpposite(alter);
							viz.setAttribute("nivel", (int)alter.getAttribute("nivel")-1);
							aux.add(viz);
						}
					}
				}
			}
			this.computeConsogros(ego);
			this.computeGenroNora(ego);
			this.computeNeraneto(ego);
			this.computeNerani(ego);
			this.computeNodaAkero(ego);
			this.computeNowatolo(ego);
			this.computeNowatore(ego);
			this.computeSogros(ego);
			this.computeTawiEkokwe(ego);
			this.cleanNodes();
		}
		this.isReady = true;
	}

	private void classifyRelatives(Node ego, Node alter, boolean cruzado) {
		switch((int)alter.getAttribute("nivel")) {
			case -2:
				if(alter.getAttribute("sexo").toString().equals("f"))
					this.addRelation(ego, alter, "noxi-weto");
				else
					this.addRelation(ego, alter, "noxi-wete");
				break;
			case -1:
				if(!cruzado)
					this.addRelation(ego, alter, "etaĩ");
				else if(alter.getAttribute("sexo").toString().equals("f")) {
					if(ego.getAttribute("sexo").toString().equals("m"))
						this.addRelation(ego, alter, "nodai-xo");
					else
						this.addRelation(ego, alter, "tawi-ro");
				}
				else {
					if(ego.getAttribute("sexo").toString().equals("m"))
						this.addRelation(ego, alter, "nodai-se");
					else
						this.addRelation(ego, alter, "tawi-hĩ");
				}
				break;
			case 0:
				if(alter.getAttribute("sexo").toString().equals("f")) {
					if(this.isOlderThan(alter, ego))
						this.addRelation(ego, alter, "yaya-lo");
					else
						this.addRelation(ego, alter, "yowa-lo");
				}
				else {
					if(ego.getAttribute("sexo").toString().equals("f")) {
						if(this.isOlderThan(alter,  ego))
							this.addRelation(ego, alter, "yaya-re");
						else
							this.addRelation(ego, alter, "yowa-re");
					}
					else {
						if(cruzado) {
							if(ego.getAttribute("sexo").equals("m"))
								this.addRelation(ego, alter, "noheroĩ");
							else if(this.isOlderThan(ego, alter))
								this.addRelation(ego, alter, "yowa-re");
							else
								this.addRelation(ego, alter, "yoya-re");
						}
						else {
							if(this.isOlderThan(alter,  ego))
								this.addRelation(ego, alter, "yaya-re");
							else
								this.addRelation(ego, alter, "yowa-re");
						}
					}
				}
				break;
			case 1:
				if(!cruzado && alter.getAttribute("sexo").toString().equals("m"))
					this.addRelation(ego, alter, "haha-re");
				else if(!cruzado && alter.getAttribute("sexo").toString().equals("f"))
					this.addRelation(ego, alter, "mama-lo");
				else if(cruzado && alter.getAttribute("sexo").toString().equals("m"))
					this.addRelation(ego, alter, "koko-re");
				else if(cruzado && alter.getAttribute("sexo").toString().equals("f"))
					this.addRelation(ego, alter, "keke-ro");
				else
					System.out.println("Algo deu errado ao descer pelo eixo");
				break;
			case 2:
			case 3:
				if(alter.getAttribute("sexo").toString().equals("m"))
					this.addRelation(ego, alter, "ato-re");
				else if(alter.getAttribute("sexo").toString().equals("f"))
					this.addRelation(ego, alter, "ahi-ro");
				break;
			default:
				if((int)alter.getAttribute("nivel") != 4)
					System.out.println("Shouldn't happen");
		}
	}
	
	private LinkedList<Node> constructAxis(Node ego) {
		Node viz, alter;
		Iterator<Edge> it2;
		Edge edge;
		LinkedList<Node> eixo = new LinkedList<Node>();
		LinkedList<Node> aux = new LinkedList<Node>();
		
		//constrói eixo
		ego.addAttribute("nivel", 0);
		aux.add(ego);
		
		eixo.add(ego);
		while(!aux.isEmpty()) {
			viz = aux.removeLast();
			if((int)viz.getAttribute("nivel") > 3) continue;
			
			it2 = viz.getEnteringEdgeIterator();
			while(it2.hasNext()) {
				edge = it2.next();
				if(!edge.isDirected()) continue;
				
				alter = edge.getOpposite(viz);
				alter.addAttribute("cameFromSex", viz.getAttribute("sexo"));
				alter.addAttribute("cameFrom", viz.getId());
				
				alter.setAttribute("nivel", (int)viz.getAttribute("nivel") + 1);
				
				classifyRelatives(ego, alter, false);//ancestors are parallel by definition
				
				eixo.add(alter);
				aux.add(alter);
			}
		}
		return eixo;
	}
	
	public void getAllCompositeRelations() {
		int total = 0;
		for(int i = 0; i < this.relations.length; i++)
			for(int j = 0; j < this.relations.length; j++){
				if(this.getRelation(i, j).split(" ").length > 1) {
					System.out.print("Ego: "+i+ " Alter: "+j+" ");
					System.out.print(this.getRelation(i, j));
					System.out.println("");
					total++;
				}
			}
		System.out.println(total);
	}
	
	public void computeConsogros(Node ego) {
		
		for(Node child : this.getChildren(ego)) 
			for(Node conj : this.getConjugues(child)) {
				if(this.getFather(conj) != null)
					this.addRelation(ego, this.getFather(conj) , "nonatonawene");
				if(this.getMother(conj) != null)
					this.addRelation(ego, this.getMother(conj) , "nonatonawene-ro");
			}
	}
	
	public void computeNerani(Node ego) {
		if(ego.getAttribute("sexo").toString().equals("m"))
			return;
		
		this.computeNeraniNeraneto(ego, "nerani");
	}
	
	public void computeNeraneto(Node ego) {
		if(ego.getAttribute("sexo").toString().equals("f"))
			return;
		
		this.computeNeraniNeraneto(ego, "neraneto");
	}
	
	public void computeNowatolo(Node ego) {
		if(ego.getAttribute("sexo").toString().equals("f"))
			return;
		
		this.computeNowatoreNowatolo(ego, "nowatore");
	}
	
	public void computeNowatore(Node ego) {
		if(ego.getAttribute("sexo").toString().equals("m"))
			return;
		
		this.computeNowatoreNowatolo(ego, "nowatolo");
	}
	
	public void computeTawiEkokwe(Node ego) {
		if(ego.getAttribute("sexo").toString().equals("m"))
			return;
		this.computeEkokweAkero(ego, "ekokwe");
	}
	
	public void computeNodaAkero(Node ego) {
		if(ego.getAttribute("sexo").toString().equals("f"))
			return;
		this.computeEkokweAkero(ego, "akero");
	}
	
	private void computeEkokweAkero(Node ego, String relation) {
		Node parent;
		List<Node> parentSimblings;
		List<Node> simblings = relation.equals("ekokwe") ? this.getBrothers(ego) : this.getSisters(ego);
		
		for(Node simb : simblings)
			for(Node child : this.getChildren(simb)) {
				
				parent = this.getTheOtherParent(simb, child);
				if(parent == null) continue;
				parentSimblings = relation.equals("ekokwe") ? 
						this.getBrothers(parent) : this.getSisters(parent);
				
				for(Node pret : parentSimblings)
					switch(relation){
						case "ekokwe":
							if(child.getAttribute("sexo").toString().equals("m"))
								this.addRelation(ego, pret, "tawihi-ekokwe");
							else
								this.addRelation(ego, pret, "tawiro-ekokwe");
							break;
						case "akero":
							if(child.getAttribute("sexo").toString().equals("m"))
								this.addRelation(ego, pret, "nodaese-akero");
							else
								this.addRelation(ego, pret, "nodaexo-akero");
							break;
					}
			}
	}
	
	public void computeSogros(Node ego) {
		Node parent;
		List<Node> list = new ArrayList<Node>();
		list.addAll(this.getConjugues(ego));
		
		for(Node child : this.getChildren(ego)) {
			parent = this.getTheOtherParent(ego, child);
			if(parent == null) continue;
			list.add(parent);
		}
		
		for(Node n : list) {
			if(this.getFather(n) != null) 
				this.addRelation(ego, this.getFather(n), "niatokwe");
			if(this.getMother(n) != null) 
				this.addRelation(ego, this.getMother(n), "niasero");
		}
	}
	
	public void computeGenroNora(Node ego) {
		List<Node> list = new ArrayList<Node>();
		Node parent;
		//esposos dos filhos
		for(Node child : this.getChildren(ego)) list.addAll(this.getConjugues(child));
		
		//pais de netos que não são meu filho
		for(Node filho : this.getChildren(ego))
			for(Node neto : this.getChildren(filho)) {
				parent = this.getTheOtherParent(filho, neto);
				if(parent != null) list.add(parent);
			}
		//classifica em genro ou nora
		for(Node n : list) {
			if(n.getAttribute("sexo").toString().equals("f"))
				this.addRelation(ego, n, "noxineto");
			if(n.getAttribute("sexo").toString().equals("m"))
				this.addRelation(ego, n, "notene");
		}				
	}
			
	private void computeNowatoreNowatolo(Node ego, String relation) {
		List<Node> list = new ArrayList<Node>();
		List<Node> simblings = relation.equals("nowatore") ? this.getSisters(ego) : this.getBrothers(ego);
		
		for(Node simb : simblings) list.addAll(this.getConjugues(simb));
		
		for(Node conj : this.getConjugues(ego))
			list.addAll(relation.equals("nowatore") ? this.getBrothers(conj) : this.getSisters(conj));
				
		for(Node alter : list) this.addRelation(ego, alter, relation);
		
	}
	
	private void computeNeraniNeraneto(Node ego, String relation) {
		List<Node> conjugueSimblings;
		List<Node> simblings;
		
		for(Node conj : this.getConjugues(ego)) {
			this.addRelation(ego, conj, relation);
			
			conjugueSimblings = relation.equals("nerani") ? 
					this.getBrothers(conj) : this.getSisters(conj);
							
			for(Node simb : conjugueSimblings)
				this.addRelation(ego, simb, relation);
		}
		
		simblings = relation.equals("nerani") ? this.getSisters(ego) : this.getBrothers(ego);
		
		for(Node simb : simblings)
			for(Node conj : this.getConjugues(simb))
				this.addRelation(ego, conj, relation);
	}
	
	private void cleanNodes() {
		Iterator<Node> it = this.getNodeIterator();
		Node n;
		while(it.hasNext()) {
			n = it.next();
			n.removeAttribute("cameFromSex");
			n.removeAttribute("cameFrom");
			n.removeAttribute("nivel");
		}
	}
	 
	public String getRelation(int ego, int alter) {
		String resp = "";
		try {
			HashSet<String> h = new HashSet<String>(Arrays.asList(relations[ego][alter].split(" ")));
			for(String token : h.toArray(new String[0]))
				resp = resp.equals("") ? token : resp+" "+token;
		}
		catch(Exception e) {
			return "";
		}
		
		return resp;
	}
	
	private Node getTheOtherParent(Node parent, Node child) {
		if(parent == this.getFather(child)) 
			return this.getMother(child);
	
		return this.getFather(child);	
	}
	
	private List<Node> getBrothers(Node ego) {
		return this.getSimblingsOfSex(ego, "m");
	}
	
	private List<Node> getSisters(Node ego) {
		return this.getSimblingsOfSex(ego, "f");
	}
	
	private List<Node> getSimblingsOfSex(Node ego, String sexo) {
		
		List<Node> brothers = new ArrayList<Node>();
		Node father = this.getFather(ego);
		Node mother = this.getMother(ego);
		Node n;
		Collection<Edge> edges = new ArrayList<Edge>(); 
		if(father != null)edges.addAll(father.getLeavingEdgeSet());
		if(mother != null)edges.addAll(mother.getLeavingEdgeSet());
		
		for(Edge e : edges) {
			n = e.getTargetNode();
			if(!e.isDirected() || n == ego || !n.getAttribute("sexo").toString().equals(sexo)) 
				continue;
			if(brothers.contains(n)) continue;
			brothers.add(n);
		}
		return brothers;
	}
	
	private List<Node> getChildren(Node parent) {
		List<Node> children = new ArrayList<Node>();
		
		for(Edge e : parent.getLeavingEdgeSet()) {
			if(!e.isDirected()) continue;
			children.add(e.getTargetNode());
		}
		return children;
	}
	
	@SuppressWarnings("unchecked")
	private List<Node> getConjugues(Node ego) {
		if((List<Node>)ego.getAttribute("conjugues") == null)
			ego.setAttribute("conjugues", new ArrayList<Node>());
		
		return (List<Node>)ego.getAttribute("conjugues");
	}
	
	//returns true if n1 is older than n2, false otherwise
	private boolean isOlderThan(Node n1, Node n2) {
		if((int)n1.getAttribute("dataNascimento") > (int)n2.getAttribute("dataNascimento"))
			return false;
		
		return true;
	}
	
	private Node getFather(Node ego) {
		return (Node)ego.getAttribute("pai");
	}
	
	private Node getMother(Node ego) {
		return (Node)ego.getAttribute("mae");
	}
	
	private void addRelation(Node ego, Node alter, String relation) {
		this.relations[Integer.parseInt(ego.getId())][Integer.parseInt(alter.getId())] += relation+" ";
	}
	
	public void performLayoutAlgorithm() {
		ArrayList<Layer> layers = new ArrayList<Layer>();
		MyToolkit kit = new MyToolkit();
		
		kit.longestPath(this);
		kit.insertDummyNodes(this);
		ArrayList<Node> ts = kit.topologicalSort(this);
			
		int indexLayer;
		Layer layer;
		boolean[] bitmap = new boolean[10];
		Arrays.fill(bitmap, false);
		
		for(Node n : ts) {
			indexLayer = n.getAttribute("maxPathLength");
			if(!bitmap[indexLayer]) {
				layers.add(indexLayer, layer = new Layer(indexLayer));
				bitmap[indexLayer] = true;
			}
			
			layer = layers.get(indexLayer);
			layer.addNode(n);
		}
		for(Layer l : layers) 
			l.setCoordinates();
		for(Layer l : layers)
			l.sortVertices();
		/*for(Iterator<Node> nodes = this.getNodeIterator(); nodes.hasNext();)
		{
			Node n = nodes.next();
			System.out.println(n.getAttribute("x"));
		}*/
	}
	
	public void addMarriagesFromFilePath(String inputFilePath) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
			String line = null;
			String[] s = null;
			Node man, woman;
			
			int numCas = Integer.parseInt(reader.readLine());
			
			for(int i = 0; i < numCas; i++) {
				line = reader.readLine();
				s = line.split("\\s+");
											
				man = this.addNode(s[0]);//cria ou retorna o homem
				woman = this.addNode(s[1]);//cria ou retorna a mulher
				
				//individuos podem ter mais de um casamento
				this.getConjugues(man).add(woman);
				this.getConjugues(woman).add(man);
				
				Edge e = this.addEdge(s[0]+" "+s[1], s[0], s[1], false);
				
				e.addAttribute("data_cas", Integer.parseInt(s[2]));
				e.addAttribute("ui.hide");
			}
			reader.close();
			this.isReady = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addNodesFromFilePath(String inputFilePath) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
			String line = null;
			String[] s = null;
			int numInd = Integer.parseInt(reader.readLine());
			int maxId = 0;
			
			for(int i = 0; i < numInd; i++) {
				line = reader.readLine();
				s = line.split("\\s+");
				if(Integer.parseInt(s[0]) > maxId) maxId = Integer.parseInt(s[0]);
				
				Node n = this.addNode(s[0]);//cria ou retorna o individuo
				n.addAttribute("sexo", s[1]);
				n.addAttribute("cla", s[4]);
				n.addAttribute("subcla", s[5]);
				n.addAttribute("dataNascimento", Integer.parseInt(s[6]));
				n.addAttribute("dataMorte", Integer.parseInt(s[7]));
				
				if(Integer.parseInt(s[2]) != 0) {
					n.addAttribute("pai", this.addNode(s[2]));//cria pai caso ele nao exista
					this.addEdge(s[2]+"_"+s[0], s[2], s[0], true);
				}
				
				if(Integer.parseInt(s[3]) != 0) {
					n.addAttribute("mae", this.addNode(s[3]));//cria mae caso ele nao exista
					this.addEdge(s[3]+"_"+s[0], s[3], s[0], true);
				}
			}
			reader.close();
			this.setRelations(maxId);
			//this.performLayoutAlgorithm();
			this.isReady = false;
		}
		catch(Exception e) {
			
		}
	}

}

