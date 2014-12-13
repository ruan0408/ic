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
	JTextArea textArea;

	String inputFilePath;
	IndioSingleGraph graph;
	
	public Gui() throws Exception
	{
		super("Projeto dos índios");
		inputFilePath = null;
		
		/* ************ BOTOES E CAIXAS**************/
		inputFileButton = new JButton("Individuos");
		marriageButton = new JButton("Casamentos");
		consultButton = new JButton("Consultar");
		ind1 = new JTextField();
		ind2 = new JTextField();
		textArea = new JTextArea(4,4);
		
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
		options.add(textArea);
		
		//mainPanel.add(view);
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
		
	}
}
