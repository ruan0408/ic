import static org.junit.Assert.*;

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
		assertEquals("mama-lo", graph.getRelation(1007, 1168));
		assertEquals("haha-re", graph.getRelation(1007, 1167));
		assertEquals("ahi-ro" , graph.getRelation(1007, 1486));
		assertEquals("ato-re", graph.getRelation(1007, 1485));
		assertEquals("yowa-re", graph.getRelation(1007, 1171));
	}

}
