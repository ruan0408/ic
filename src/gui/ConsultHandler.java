package gui;

import graph.CalculadorDeRelacoes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ConsultHandler implements ActionListener {
	
	private final Gui gui;

	ConsultHandler(Gui gui) {
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent event){	
		
		if(event.getSource() == this.gui.getBotaoConsulta()){
			
			CalculadorDeRelacoes calc = this.gui.getCalculadorDeRelacoes();
			if(calc != null) {
				int egoId 	=	this.gui.getEgoId();
				int alterId =	this.gui.getAlterId();
				
				this.gui.mostraResposta(calc.getRelacao(egoId, alterId));	
			}
		}
	}
}