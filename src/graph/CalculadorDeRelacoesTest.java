package graph;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CalculadorDeRelacoesTest {

	private CalculadorDeRelacoes calc;
	
	@Before
	public void setUp() throws Exception {
		Tribo tribo = TriboBuilder.constroi("/home/ruan0408/workspace/Indios/EN-31jul2011-individuos-g-(com-cla).txt");
		calc = new CalculadorDeRelacoes(tribo);
		calc.calculaTodasAsRelacoes();
	}

	@After
	public void tearDown() throws Exception {
		this.calc = null;
	}

	@Test
	public void test() {
		
		assertEquals("mama-lo", 	calc.getRelacao(1007, 1168));
		assertEquals("haha-re", 	calc.getRelacao(1007, 1167));
		assertEquals("ahi-ro" , 	calc.getRelacao(1007, 1486));
		assertEquals("ato-re", 		calc.getRelacao(1007, 1485));
		assertEquals("yowa-re", 	calc.getRelacao(1007, 1171));
		assertEquals("yowa-lo", 	calc.getRelacao(1007, 1173));
		assertEquals("etaĩ", 		calc.getRelacao(1007, 1264));
		assertEquals("noxi-weto",	calc.getRelacao(1007, 1929));
		assertEquals("haha-re", 	calc.getRelacao(1266, 1169));
		assertEquals("keke-ro", 	calc.getRelacao(1266, 1173));
		assertEquals("yowa-lo", 	calc.getRelacao(1266, 1543));
		assertEquals("yaya-re", 	calc.getRelacao(1543, 1266));
		assertEquals("noheroĩ", 	calc.getRelacao(1266, 1888));
		assertEquals("noheroĩ", 	calc.getRelacao(1888, 1266));
		assertEquals("nodai-se",	calc.getRelacao(1172, 1888));
		assertEquals("etaĩ", 		calc.getRelacao(1172, 1600));
		assertEquals("tawi-ro", 	calc.getRelacao(1173, 1600));
		assertEquals("keke-ro", 	calc.getRelacao(1600, 1173));
		assertEquals("tawi-hĩ", 	calc.getRelacao(1173, 1250));
		assertEquals("nodai-xo", 	calc.getRelacao(1265, 1929));
		assertEquals("", 			calc.getRelacao(1460, 1157));
		assertEquals("noxi-wete", 	calc.getRelacao(1153, 1157));
		assertEquals("koko-re", 	calc.getRelacao(1929, 1265));
		assertEquals("yaya-lo", 	calc.getRelacao(1888, 1267));
		assertEquals("yowa-re", 	calc.getRelacao(1753, 1806));
		assertEquals("yaya-re", 	calc.getRelacao(1600, 1175));
		assertEquals("neraneto", 	calc.getRelacao(1167, 1168));
		assertEquals("nerani", 		calc.getRelacao(1168, 1167));
		assertEquals("nerani", 		calc.getRelacao(1063, 1170));
		assertEquals("nerani", 		calc.getRelacao(1156, 1007));
		assertEquals("neraneto", 	calc.getRelacao(1007, 1156));
		assertEquals("neraneto", 	calc.getRelacao(1170, 1145));
		assertEquals("nowatore", 	calc.getRelacao(1265, 1095));
		assertEquals("nowatore", 	calc.getRelacao(1095, 1266));
		assertEquals("nowatolo", 	calc.getRelacao(1173, 1145));
		assertEquals("nowatolo", 	calc.getRelacao(1063, 1173));
		assertEquals("nonatonawene",calc.getRelacao(1167, 1153));
		assertEquals("nonatonawene-ro",calc.getRelacao(1168, 1154));
		assertEquals("nonatonawene-ro",calc.getRelacao(1167, 1154));
		assertEquals("nonatonawene",calc.getRelacao(1168, 1153));
		assertEquals("niasero",		calc.getRelacao(1063, 1168));
		assertEquals("niatokwe",	calc.getRelacao(1155, 1153));
		assertEquals("notene",		calc.getRelacao(1153, 1155));
		assertEquals("noxineto",	calc.getRelacao(1168, 1145));
		assertEquals("",			calc.getRelacao(1156, 1172));
		assertEquals("nodaexo-akero nodaese-akero neraneto",calc.getRelacao(1265, 1094));
		assertEquals("tawiro-ekokwe nerani tawihi-ekokwe",calc.getRelacao(1094, 1265));
		
		//composite relations
		assertEquals("yaya-re koko-re", calc.getRelacao(1837, 1073));
		assertEquals("noheroĩ etaĩ", 	calc.getRelacao(1837, 1770));
		assertEquals("yaya-re haha-re", calc.getRelacao(1882, 1027));
		
		//calc.getAllCompositeRelations();
	}

}
