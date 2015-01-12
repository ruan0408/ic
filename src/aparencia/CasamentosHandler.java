package aparencia;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import comunidade.CalculadorDeRelacoes;
import comunidade.TriboBuilder;

class CasamentosHandler implements ActionListener {
	
	private final Gui gui;

	CasamentosHandler(Gui gui) {
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == this.gui.getBotaoCasamentos()) {
			JFileChooser chooser = new JFileChooser();
			int resp = chooser.showOpenDialog(this.gui.getParent());
			
			if(resp == JFileChooser.APPROVE_OPTION) {
				
				String arquivoCasamentos = chooser.getSelectedFile().getAbsolutePath();
				CalculadorDeRelacoes calc = this.gui.getCalculadorDeRelacoes();
				
				try {
					TriboBuilder.adicionaCasamentos(calc.getTribo(), arquivoCasamentos);
					//faz o calculador recalcular, pois agora casamentos foram adicionados
					calc.setEstaCalculado(false);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, 
							"Arquivo fornecido está no formato errado!",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, 
							"Arquivo não existe!",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null, 
							"Você deve inserir o arquivo de indivíduos antes",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}