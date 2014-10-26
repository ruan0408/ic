import java.awt.FlowLayout;
//import java.util.*;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
//import java.io.*;


import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

public class Gui extends JFrame 
{	
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	View view;
	private JPanel options;
	
	JButton inputFileButton;
	JButton marriageButton;
	JButton consultButton;
	JTextField ind1;
	JTextField ind2;

	String inputFilePath;
	String[][] relations;
	IndioSingleGraph graph;
	
	public Gui()
	{
		super("Projeto dos índios");
		inputFilePath = null;
		
		/* ************ BOTOES E CAIXAS**************/
		inputFileButton = new JButton("Individuos");
		marriageButton = new JButton("Casamentos");
		consultButton = new JButton("Consultar");
		ind1 = new JTextField("Individuo 1");
		ind2 = new JTextField("Individuo 2");
		
		inputFileButton.addActionListener(new InputFileHandler(this));
		marriageButton.addActionListener(new MarriageHandler(this));
		consultButton.addActionListener(new ConsultHandler(this));
		
		ind1.setColumns(6);
		ind2.setColumns(6);
		/* ******************************************/
		
		/* ********* PAINEIS********************* */
		mainPanel = new JPanel();
		options = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		options.setLayout(new FlowLayout());
		
		graph = new IndioSingleGraph("Graph");
		graph.setStrict(false);//evitar problemas
		
		Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		//viewer.enableAutoLayout(hl);
		viewer.disableAutoLayout();
		viewer.enableXYZfeedback(false);
		
		view = viewer.addDefaultView(false);
		view.setPreferredSize(new Dimension(1000, 600));
		
		ViewHandler h = new ViewHandler(this);
		view.addMouseWheelListener(h);
		view.addMouseMotionListener(h);
		view.getCamera().setViewPercent(0.01);
			
		options.add(marriageButton);
		options.add(inputFileButton);
		options.add(consultButton);
		options.add(ind1);
		options.add(ind2);
		
		mainPanel.add(view);
		mainPanel.add(options);
		
		this.add(mainPanel);
		/* ******************************************/
		
		/* ************ JFRAME ************************/
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);		
		this.pack();
		/* ******************************************/
		
		/* ************ GRAFO **********************/
		graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.stylesheet","edge { fill-color: gray; arrow-size: 4px, 4px;}");//shape: cubic-curve;
        graph.addAttribute("ui.stylesheet","node { fill-color: gray; size:10px;}");
		/* *****************************************/
		
		//this.setLayout(new FlowLayout());
		//HierarchicalLayout hl = new HierarchicalLayout();//**************************************
		//hl.setRoots("1584", "1561", "1560", "1558", "1519", "1518", "1517", "1516", "1515", "1514", "1513", "1512", "1510", "1509", "1508", "1505", "1504", "1503", "1502", "1501", "1500", "1496", "1495", "1494", "1492", "1491", "1490", "1489", "1487", "1486", "1485", "1482", "1481", "1480", "1479", "1478", 
		//		"1477", "1476", "1474", "1473", "1472", "1471", "1470", "1469", "1468", "1467", "1466", "1465", "1464", "1462", "1461", "1460", "1459", "1458", "1452", "1449", "1448", "1447", "1440", "1416", "1415", "1411", "1410", "1408", "1407", "1404", "1112");//***************************************************

		//Viewer v = g.display(false);***************************************
		//v.enableAutoLayout(hl);********************************************
	}
}
