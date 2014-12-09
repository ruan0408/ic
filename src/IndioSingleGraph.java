import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;


public class IndioSingleGraph extends SingleGraph 
{
	String[][] relations;//relations[ego][alter] diz o que o alter é para o ego (como o ego chama o alter)
	
	public IndioSingleGraph(String id) 
	{
		super(id);
		super.setStrict(false);
	}
	
	public void setRelations(int tam){
		this.relations = new String[tam+1][tam+1];
		for (String[] row: this.relations)
		    Arrays.fill(row, "");
	}
	
	public void computeRelations()
	{
		Iterator<Node> it = this.getNodeIterator();
		Iterator<Edge> it2, it3;
		Node ego, alter, viz, noEixo;
		Edge edge, e3;
		boolean cruzado = false;
		LinkedList<Node> queue;
		LinkedList<Node> aux;
		
		//para cada indivuduo
		while(it.hasNext())
		{
			//System.out.println("NOVO INDIVIDUO");
			ego = it.next();		//não mexa nesse cara, vc vai usar ele em todas as addRelation
			queue = constructAxis(ego);
			//System.out.println("EIXO COMPLETO");
			
			while(!queue.isEmpty()) //calculate upper relations
			{
				noEixo = queue.removeFirst();
				it2 = noEixo.getLeavingEdgeIterator();
				
				while(it2.hasNext())
				{
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
					while(!aux.isEmpty())
					{
						alter = aux.removeLast();
						
						if((int)alter.getAttribute("nivel") < -2) break;
						if(alter != ego) classifyRelatives(ego, alter, cruzado);
						
						it3 = alter.getLeavingEdgeIterator();
						while(it3.hasNext())
						{
							e3 = it3.next();
							if(!e3.isDirected()) continue;
							
							viz = e3.getOpposite(alter);
							viz.setAttribute("nivel", (int)alter.getAttribute("nivel")-1);
							aux.add(viz);
						}
					}
				}
			}
			this.cleanNodes();
		}
	}

	private void classifyRelatives(Node ego, Node alter, boolean cruzado) {
		switch((int)alter.getAttribute("nivel"))
		{
			case -2:
				if(alter.getAttribute("sexo").toString().equals("f"))
					this.addRelation(ego, alter, "noxi-weto");
				else
					this.addRelation(ego, alter, "noxi-wete");
				break;
			case -1:
				if(!cruzado)
					this.addRelation(ego, alter, "etaĩ");
				else if(alter.getAttribute("sexo").toString().equals("f"))
				{
					if(ego.getAttribute("sexo").toString().equals("m"))
						this.addRelation(ego, alter, "nodai-xo");
					else
						this.addRelation(ego, alter, "tawi-ro");
				}
				else
				{
					if(ego.getAttribute("sexo").toString().equals("m"))
						this.addRelation(ego, alter, "nodai-se");
					else
						this.addRelation(ego, alter, "tawi-hĩ");
				}
				break;
			case 0:
				if(alter.getAttribute("sexo").toString().equals("f"))
				{
					if(this.isOlderThan(alter, ego))
						this.addRelation(ego, alter, "yaya-lo");
					else
						this.addRelation(ego, alter, "yowa-lo");
				}
				else
				{
					if(ego.getAttribute("sexo").toString().equals("f"))
					{
						if(this.isOlderThan(alter,  ego))
							this.addRelation(ego, alter, "yaya-re");
						else
							this.addRelation(ego, alter, "yowa-re");
					}
					else
					{
						if(cruzado) {
							if(ego.getAttribute("sexo").equals("m"))
								this.addRelation(ego, alter, "noheroĩ");
							else if(this.isOlderThan(ego, alter))
								this.addRelation(ego, alter, "yowa-re");
							else
								this.addRelation(ego, alter, "yoya-re");
						}
						else
						{
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
	
	private LinkedList<Node> constructAxis(Node ego)
	{
		Node viz, alter;
		Iterator<Edge> it2;
		Edge edge;
		LinkedList<Node> eixo = new LinkedList<Node>();
		LinkedList<Node> aux = new LinkedList<Node>();
		
		//constrói eixo
		ego.addAttribute("nivel", 0);
		aux.add(ego);
		
		eixo.add(ego);
		while(!aux.isEmpty())
		{
			viz = aux.removeLast();
			if((int)viz.getAttribute("nivel") > 3) continue;
			
			it2 = viz.getEnteringEdgeIterator();
			while(it2.hasNext())
			{
				edge = it2.next();
				if(!edge.isDirected()) continue;
				
				alter = edge.getOpposite(viz);
				//if(viz.getAttribute("sexo") == null) System.out.println("sexo nulo no construct axis");
				//if(viz.getAttribute("nivel") == null) System.out.println("nivel nulo no construct axis");
				alter.addAttribute("cameFromSex", viz.getAttribute("sexo"));
				//if(viz.getId() == null) System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
				alter.addAttribute("cameFrom", viz.getId());
				if(alter.getAttribute("cameFrom") == null)
					System.out.println("lalalla");
				alter.setAttribute("nivel", (int)viz.getAttribute("nivel") + 1);
				
				classifyRelatives(ego, alter, false);//ancestors are parallel by definition
				
				if(alter.getAttribute("cameFrom") == null)
					System.out.println("lalalla");
				eixo.add(alter);
				aux.add(alter);
			}
		}
		return eixo;
	}
	
	//incomplete...
	private void findNerani(Node ego)
	{
		Node hus, father;
		hus = null;
		Iterator<Edge> it = ego.getEdgeIterator();
		Edge e;
		
		if(!ego.getAttribute("sexo").toString().equals("f"))
			return;
		
		while(it.hasNext())
		{
			e = it.next();
			if(!e.isDirected())
			{
				hus = e.getOpposite(ego);
				this.addRelation(ego, hus, "nerani");
				
				it = hus.getEnteringEdgeIterator();
				while(it.hasNext())
				{
					e = it.next();
					if(e.getOpposite(hus).getAttribute("sexo").toString().equals("m"))
						father = e.getOpposite(hus);
					
				}
			}
				
		}
	}
	
	private void cleanNodes(){
		Iterator<Node> it = this.getNodeIterator();
		Node n;
		while(it.hasNext()){
			n = it.next();
			n.removeAttribute("cameFromSex");
			n.removeAttribute("cameFrom");
			n.removeAttribute("nivel");
		}
	}
	 
	public String getRelation(int ego, int alter){
		String resp = "";
		HashSet<String> h = new HashSet<String>(Arrays.asList(relations[ego][alter].split(" ")));
		for(String token : h.toArray(new String[0]))
			resp = token+""+resp;
		return resp;
	}
	//returns true if n1 is older than n2, false otherwise
	private boolean isOlderThan(Node n1, Node n2)
	{
		if((int)n1.getAttribute("dataNascimento") > (int)n2.getAttribute("dataNascimento"))
			return false;
		
		return true;
	}
	private void addRelation(Node ego, Node alter, String relation)
	{
		this.relations[Integer.parseInt(ego.getId())][Integer.parseInt(alter.getId())] += relation+" ";
	}
	
	public void performLayoutAlgorithm()
	{
		ArrayList<Layer> layers = new ArrayList<Layer>();
		MyToolkit kit = new MyToolkit();
		
		kit.longestPath(this);
		kit.insertDummyNodes(this);
		ArrayList<Node> ts = kit.topologicalSort(this);
			
		int indexLayer;
		Layer layer;
		boolean[] bitmap = new boolean[10];
		Arrays.fill(bitmap, false);
		
		for(Node n : ts)
		{
			indexLayer = n.getAttribute("maxPathLength");
			if(!bitmap[indexLayer])
			{
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
			int numCas = Integer.parseInt(reader.readLine());
			
			for(int i = 0; i < numCas; i++)
			{
				line = reader.readLine();
				s = line.split("\\s+");
											
				this.addNode(s[0]);//cria ou retorna o homem
				this.addNode(s[1]);//cria ou retorna a mulher
				Edge e = this.addEdge(s[0]+" "+s[1], s[0], s[1], false);
				e.addAttribute("data_cas", Integer.parseInt(s[2]));
				e.addAttribute("ui.hide");
			}
			reader.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	public void addNodesFromFilePath(String inputFilePath) {
		try{
			BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
			String line = null;
			String[] s = null;
			int numInd = Integer.parseInt(reader.readLine());
			int maxId = 0;
			
			for(int i = 0; i < numInd; i++)
			{
				line = reader.readLine();
				s = line.split("\\s+");
				if(Integer.parseInt(s[0]) > maxId) maxId = Integer.parseInt(s[0]);
				
				Node n = this.addNode(s[0]);//cria ou retorna o individuo
				n.addAttribute("sexo", s[1]);
				n.addAttribute("cla", s[4]);
				n.addAttribute("subcla", s[5]);
				n.addAttribute("dataNascimento", Integer.parseInt(s[6]));
				n.addAttribute("dataMorte", Integer.parseInt(s[7]));
				
				if(Integer.parseInt(s[2]) != 0){
					this.addNode(s[2]);//cria pai caso ele nao exista
					this.addEdge(s[2]+"_"+s[0], s[2], s[0], true);
				}
				
				if(Integer.parseInt(s[3]) != 0){
					this.addNode(s[3]);//cria mae caso ele nao exista
					this.addEdge(s[3]+"_"+s[0], s[3], s[0], true);
				}
			}
			reader.close();
			this.setRelations(maxId);
		}catch(Exception e){
			
		}
	}

}
