import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import java.util.*;


public class MyToolkit extends Toolkit 
{
	public MyToolkit() 
	{
		super();
	}
	
	public ArrayList<Node> topologicalSort(Graph dag)
	{	
		ArrayList<Node> list = new ArrayList<Node>();
		
		for(Iterator<Node> nodes = dag.getNodeIterator(); nodes.hasNext();)
			nodes.next().setAttribute("searched", false);
		
		for(Iterator<Node> nodes = dag.getNodeIterator(); nodes.hasNext();)
		{
			Node n = nodes.next();
			if(!(boolean)n.getAttribute("searched")) 
				dfsR(n, list);
		}
		return list;
	}
	
	private void dfsR(Node n, ArrayList<Node> list)
	{	
		Edge marriage = null;
		Edge e = null;
		Node op = null;
		n.setAttribute("searched", true);
		
		Iterator<? extends Edge> edges = n.getLeavingEdgeIterator();

		while(edges.hasNext()) 
		{	
			e = edges.next();
			op = e.getOpposite(n);
					
			if(!e.isDirected())
				marriage = e;
			else if(!(boolean)op.getAttribute("searched"))
				dfsR(e.getOpposite(n), list);
				
		}
		if(marriage != null)
			dfsR(e.getOpposite(n), list);
		
		list.add(0, n);
	}
	
	public boolean isTopologicallySorted(ArrayList<Node> ts)
	{
		Node node = null;
		for(Node n : ts)
			for(Iterator<? extends Edge> outEdges = n.getLeavingEdgeIterator(); outEdges.hasNext();)
			{	
				node = outEdges.next().getOpposite(n);
				if(ts.indexOf(n) > ts.indexOf(node))
					return false;
			}
		return true;
	}
	
	public void longestPath(Graph dag)
	{	
		Node maxPathNode;
		ArrayList<Node> ts = this.topologicalSort(dag);
		
		for(int i = 0; i < ts.size(); i++){
			ts.get(i).addAttribute("tsIndex", i);
			ts.get(i).addAttribute("maxPathLength", -1);//maxPathLength will be the layer of the node
		}
		
		maxPathNode = ts.get(0);//Just to get started
		for(Node currentNode : ts){
			
			if(currentNode.getInDegree() == 0)
				currentNode.setAttribute("maxPathLength", 0);
			else
				for(Iterator<Edge> inEdges = currentNode.getEnteringEdgeIterator(); inEdges.hasNext();)
				{
					Edge in = inEdges.next();
					if(!in.isDirected()) continue;
					
					Node back = in.getOpposite(currentNode);
					if((int)back.getAttribute("maxPathLength")+1 > (int)currentNode.getAttribute("maxPathLength"))
						currentNode.setAttribute("maxPathLength", (int)back.getAttribute("maxPathLength")+1);
				}
			if((int)currentNode.getAttribute("maxPathLength") > (int)maxPathNode.getAttribute("maxPathLength"))
				maxPathNode = currentNode;
		}
	}
	
	private void longestPathR(Node current, ArrayList<Node> lp)
	{	
		if(current.getInDegree() == 0) return;
		
		Node backReturn = null;//will be returned
		for(Iterator<Edge> inEdges = current.getEnteringEdgeIterator(); inEdges.hasNext();)
		{
			Edge in = inEdges.next();
			Node backCurrent = in.getOpposite(current);
			if(backReturn == null || (int)backCurrent.getAttribute("maxPathLength") > (int)backReturn.getAttribute("maxPathLength"))
				backReturn = backCurrent;
		}
		
		lp.add(0, backReturn);
		longestPathR(backReturn, lp);
	}
	
	public void insertDummyNodes(Graph graph)
	{	
		Edge edge;
		Node zero, one, aux;
		int diff, cnt, cnt2;
		cnt = cnt2 = 0;
		
		for(Iterator<Edge> edges = graph.getEdgeIterator(); edges.hasNext();)
		{
			edge = edges.next();
			if(!edge.isDirected())continue;
			
			zero = edge.getSourceNode();
			one = edge.getTargetNode();
			diff = (int)one.getAttribute("maxPathLength") - (int)zero.getAttribute("maxPathLength");
			
			if(diff > 1)
				graph.removeEdge(edge);
				
			while(diff > 1)
			{
				aux = graph.addNode("dummyNode"+cnt);
				aux.addAttribute("ui.style", "fill-color: rgb(0,0,0);");
				//aux.addAttribute("ui.hide");
				aux.addAttribute("dummy", true);
				aux.setAttribute("maxPathLength", (int)zero.getAttribute("maxPathLength")+1);
				graph.addEdge("dummyEdge"+cnt2, zero, aux, true).addAttribute("ui.style", "fill-color: rgb(0,100,0);");
				
				zero = aux;
				cnt++; cnt2++; diff--;
				
				if(diff == 1)
				{
					graph.addEdge("dummyEdge"+cnt2, zero, one, true).addAttribute("ui.style", "fill-color: rgb(0,100,0);");
					cnt2++;
					break;
				}
			}
		}			
	}
	
	public void checkDummy(Graph graph)
	{	
		for(Iterator<Edge> edges = graph.getEdgeIterator(); edges.hasNext();)
		{
			Edge edge = edges.next();
			Node zero = edge.getSourceNode();
			Node one = edge.getTargetNode();
			
			if((int)one.getAttribute("maxPathLength") - (int)zero.getAttribute("maxPathLength") > 1)
				System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
		}
	}
}
