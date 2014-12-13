import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class ConsultHandler implements ActionListener {
	
	private final Gui gui;

	ConsultHandler(Gui gui) {
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent event){	
		
		if(event.getSource() == this.gui.consultButton){
			if(!this.gui.ind1.getText().isEmpty() && !this.gui.ind2.getText().isEmpty()) {
				int t1 =Integer.parseInt(this.gui.ind1.getText());
				int t2 =Integer.parseInt(this.gui.ind2.getText());
				
				gui.graph.computeRelations();
				gui.textArea.setText(null);
				gui.textArea.insert(gui.graph.getRelation(t1, t2), 0);
			}
		}
	}
}