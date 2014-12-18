package aparencia;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import comunidade.CalculadorDeRelacoes;
import comunidade.Tribo;
import comunidade.TriboBuilder;

class IndividuosHandler implements ActionListener {	
	
	private final Gui gui;

	IndividuosHandler(Gui gui) {
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent event) {	
		if(event.getSource() == this.gui.getBotaoIndividuos()) {
			String caminhoArquivo;
			JFileChooser chooser = new JFileChooser();
			int resp = chooser.showOpenDialog(this.gui.getParent());
			
			if(resp == JFileChooser.APPROVE_OPTION) {
				
				caminhoArquivo = chooser.getSelectedFile().getAbsolutePath();
				try {
					Tribo novaTribo = TriboBuilder.constroi(caminhoArquivo); 
					this.gui.setCalculadorDeRelacoes(new CalculadorDeRelacoes(novaTribo));
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
				} 
			}
		}
	}
}