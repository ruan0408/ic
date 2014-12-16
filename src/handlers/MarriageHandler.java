package handlers;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;

class MarriageHandler implements ActionListener {
	
	private final Gui gui;

	MarriageHandler(Gui gui) {
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == this.gui.marriageButton) {
			JFileChooser chooser = new JFileChooser();
			int resp = chooser.showOpenDialog(this.gui.getParent());
			
			if(resp == JFileChooser.APPROVE_OPTION) {
				this.gui.inputFilePath = chooser.getSelectedFile().getAbsolutePath();
				//this.gui.inputFilePath = "/home/ruan0408/workspace/Indios/EN-31jul2011-casamentos-g.txt";
				try {
					this.gui.graph.addMarriagesFromFilePath(this.gui.inputFilePath);
				}
				catch(Exception e) {
					e.printStackTrace(System.out);
					System.out.println("Arquivo de casamento não existe");
				}
			}
		}
	}
}