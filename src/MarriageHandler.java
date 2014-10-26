import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;


class MarriageHandler implements ActionListener
{
	/**
	 * 
	 */
	private final Gui gui;

	/**
	 * @param gui
	 */
	MarriageHandler(Gui gui) {
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent event)
	{	
		if(event.getSource() == this.gui.marriageButton)
		{
			JFileChooser chooser = new JFileChooser();
			int resp = chooser.showOpenDialog(this.gui.getParent());
			
			if(resp == JFileChooser.APPROVE_OPTION)
			{
				this.gui.inputFilePath = chooser.getSelectedFile().getAbsolutePath();
				try
				{
					this.gui.graph.addMarriagesFromFilePath(this.gui.inputFilePath);
				}
				catch(Exception e)
				{
					System.out.println("Arquivo de casamento não existe");
				}
			}
		}
	}
}