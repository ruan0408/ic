package aparencia;

import java.awt.*;
import javax.swing.*;

import comunidade.CalculadorDeRelacoes;

//import org.graphstream.ui.swingViewer.View;
//import org.graphstream.ui.swingViewer.Viewer;
public class Gui extends JFrame 
{	
	private static final long serialVersionUID = 1L;
	private JButton botaoConsulta;
	private JButton botaoIndividuos;
	private JButton botaoCasamentos;
	private JTextField 	campoEgo;
	private JTextField 	campoAlter;
	private JTextArea 	campoTexto;
	private CalculadorDeRelacoes calc;
	
	public Gui() {
		super("Projeto dos Enawene-nawe");
		super.setResizable(false);
	}
	
	public void inicializa() {
		JPanel painelPrincipal, painelEntrada, painelTexto;
		//View visao;
		this.botaoConsulta = new JButton("Consultar");
		this.botaoIndividuos = new JButton("Indivíduos");
		this.botaoCasamentos = new JButton("Casamentos");
		
		this.campoEgo = new JTextField(6);
		this.campoAlter = new JTextField(6);
		this.campoTexto = new JTextArea(4,30);
		this.campoTexto.setPreferredSize(new Dimension(4, 30));
		this.campoTexto.setLineWrap(true);
		this.campoTexto.setEditable(false);
		this.campoTexto.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		painelPrincipal = new JPanel();
		painelEntrada = new JPanel();
		painelTexto = new JPanel();
		painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
		painelEntrada.setLayout(new FlowLayout());
		painelTexto.setLayout(new FlowLayout());
		
		/*Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.disableAutoLayout();
		viewer.enableXYZfeedback(false);
		visao = viewer.addDefaultView(false);
		visao.setPreferredSize(new Dimension(1000, 600));
		ViewHandler visaoHandler = new ViewHandler(this);
		visao.addMouseWheelListener(visaoHandler);
		visao.addMouseMotionListener(visaoHandler);
		visao.getCamera().setViewPercent(0.01);*/
		
		botaoIndividuos.addActionListener(new IndividuosHandler(this));
		botaoCasamentos.addActionListener(new CasamentosHandler(this));
		botaoConsulta.addActionListener(new ConsultasHandler(this));
		
		painelEntrada.add(botaoIndividuos);
		painelEntrada.add(botaoCasamentos);
		painelEntrada.add(botaoConsulta);
		painelEntrada.add(new JLabel("Ego:"));
		painelEntrada.add(this.campoEgo);
		painelEntrada.add(new JLabel("Alter:"));
		painelEntrada.add(this.campoAlter);
		
		painelTexto.add(this.campoTexto);
		
		//painelPrincipal.add(view);
		painelPrincipal.add(painelEntrada);
		painelPrincipal.add(painelTexto);
		
		this.add(painelPrincipal);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	protected JButton getBotaoIndividuos() {
		return this.botaoIndividuos;
	}
	
	protected JButton getBotaoCasamentos() {
		return this.botaoCasamentos;
	}
	
	protected JButton getBotaoConsulta() {
		return this.botaoConsulta;
	}
	
	protected void setCalculadorDeRelacoes(CalculadorDeRelacoes calc) {
		this.calc = calc;
	}
	
	protected CalculadorDeRelacoes getCalculadorDeRelacoes() {
		return this.calc;
	}

	protected int getEgoId() {
		int resp;
		try {
			resp = Integer.parseInt(this.campoEgo.getText());
		}
		catch(Exception e) { resp = -1;}
		return resp;
	}
	
	protected int getAlterId() {
		int resp;
		try {
			resp = Integer.parseInt(this.campoAlter.getText());
		}
		catch(Exception e) { resp = -1;}
		return resp;
	}
	
	protected void mostraResposta(String resposta) {
		this.campoTexto.setText(null);
		this.campoTexto.setText(resposta);
	}
}
