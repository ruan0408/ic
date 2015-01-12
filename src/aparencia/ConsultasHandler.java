package aparencia;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import comunidade.CalculadorDeRelacoes;

class ConsultasHandler implements ActionListener {
	
	private final Gui gui;

	ConsultasHandler(Gui gui) {
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent event){	
		
		if(event.getSource() == this.gui.getBotaoConsulta()){
			
			CalculadorDeRelacoes calc = this.gui.getCalculadorDeRelacoes();
			if(calc != null) {
				int egoId 	=	this.gui.getEgoId();
				int alterId =	this.gui.getAlterId();
				
				if(this.gui.getMaxSubida() != calc.getMaxNivel()) {
					calc.setMaxNivel(this.gui.getMaxSubida());
					calc.setEstaCalculado(false);
				}
				
				this.gui.mostraResposta(calc.getRelacao(egoId, alterId));	
			}
		}
	}
}