import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class ConsultHandler implements ActionListener
{
	/**
	 * 
	 */
	private final Gui gui;

	/**
	 * @param gui
	 */
	ConsultHandler(Gui gui) {
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent event){	
		
		if(event.getSource() == this.gui.consultButton){
			int t1 =Integer.parseInt(this.gui.ind1.getText());
			int t2 =Integer.parseInt(this.gui.ind2.getText());
			System.out.println(t1);
			System.out.println(t2);
			//transforme em int e procure a relação do segundo com o primeiro
			
			gui.graph.computeRelations();
			gui.graph.getRelation(t1, t2);
		}
	}
}