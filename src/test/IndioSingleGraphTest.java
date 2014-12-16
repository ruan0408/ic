package test;
import static org.junit.Assert.*;
import graph.IndioSingleGraph;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IndioSingleGraphTest {

	private IndioSingleGraph graph;
	@Before
	public void setUp() {
		graph = new IndioSingleGraph("graph");
		graph.addNodesFromFilePath("/home/ruan0408/workspace/Indios/EN-31jul2011-individuos-g-(com-cla).txt");
		graph.addMarriagesFromFilePath("/home/ruan0408/workspace/Indios/EN-31jul2011-casamentos-g.txt");
		graph.computeRelations();
	}

	@After
	public void tearDown() throws Exception {
		graph = null;
	}

	@Test
	public void computeRelations() {
		assertEquals("mama-lo", 	graph.getRelation(1007, 1168));
		assertEquals("haha-re", 	graph.getRelation(1007, 1167));
		assertEquals("ahi-ro" , 	graph.getRelation(1007, 1486));
		assertEquals("ato-re", 		graph.getRelation(1007, 1485));
		assertEquals("yowa-re", 	graph.getRelation(1007, 1171));
		assertEquals("yowa-lo", 	graph.getRelation(1007, 1173));
		assertEquals("etaĩ", 		graph.getRelation(1007, 1264));
		assertEquals("noxi-weto",	graph.getRelation(1007, 1929));
		assertEquals("haha-re", 	graph.getRelation(1266, 1169));
		assertEquals("keke-ro", 	graph.getRelation(1266, 1173));
		assertEquals("yowa-lo", 	graph.getRelation(1266, 1543));
		assertEquals("yaya-re", 	graph.getRelation(1543, 1266));
		assertEquals("noheroĩ", 	graph.getRelation(1266, 1888));
		assertEquals("noheroĩ", 	graph.getRelation(1888, 1266));
		assertEquals("nodai-se",	graph.getRelation(1172, 1888));
		assertEquals("etaĩ", 		graph.getRelation(1172, 1600));
		assertEquals("tawi-ro", 	graph.getRelation(1173, 1600));
		assertEquals("keke-ro", 	graph.getRelation(1600, 1173));
		assertEquals("tawi-hĩ", 	graph.getRelation(1173, 1250));
		assertEquals("nodai-xo", 	graph.getRelation(1265, 1929));
		assertEquals("", 			graph.getRelation(1460, 1157));
		assertEquals("noxi-wete", 	graph.getRelation(1153, 1157));
		assertEquals("koko-re", 	graph.getRelation(1929, 1265));
		assertEquals("yaya-lo", 	graph.getRelation(1888, 1267));
		assertEquals("yowa-re", 	graph.getRelation(1753, 1806));
		assertEquals("yaya-re", 	graph.getRelation(1600, 1175));
		assertEquals("neraneto", 	graph.getRelation(1167, 1168));
		assertEquals("nerani", 		graph.getRelation(1168, 1167));
		assertEquals("nerani", 		graph.getRelation(1063, 1170));
		assertEquals("nerani", 		graph.getRelation(1156, 1007));
		assertEquals("neraneto", 	graph.getRelation(1007, 1156));
		assertEquals("neraneto", 	graph.getRelation(1170, 1145));
		assertEquals("nowatore", 	graph.getRelation(1265, 1095));
		assertEquals("nowatore", 	graph.getRelation(1095, 1266));
		assertEquals("nowatolo", 	graph.getRelation(1173, 1145));
		assertEquals("nowatolo", 	graph.getRelation(1063, 1173));
		assertEquals("nonatonawene",graph.getRelation(1167, 1153));
		assertEquals("nonatonawene-ro",graph.getRelation(1168, 1154));
		assertEquals("nonatonawene-ro",graph.getRelation(1167, 1154));
		assertEquals("nonatonawene",graph.getRelation(1168, 1153));
		assertEquals("niasero",		graph.getRelation(1063, 1168));
		assertEquals("niatokwe",	graph.getRelation(1155, 1153));
		assertEquals("notene",		graph.getRelation(1153, 1155));
		assertEquals("noxineto",	graph.getRelation(1168, 1145));
		assertEquals("",			graph.getRelation(1156, 1172));
		assertEquals("nodaexo-akero nodaese-akero neraneto",graph.getRelation(1265, 1094));
		assertEquals("tawiro-ekokwe nerani tawihi-ekokwe",graph.getRelation(1094, 1265));
		
		//composite relations
		assertEquals("yaya-re koko-re", graph.getRelation(1837, 1073));
		assertEquals("noheroĩ etaĩ", 	graph.getRelation(1837, 1770));
		assertEquals("yaya-re haha-re", graph.getRelation(1882, 1027));
		
		//graph.getAllCompositeRelations();
	}

}
