import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;


class InputFileHandler implements ActionListener
{	
	/**
	 * 
	 */
	private final Gui gui;

	/**
	 * @param gui
	 */
	InputFileHandler(Gui gui) {
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent event)
	{	
		if(event.getSource() == this.gui.inputFileButton)
		{
			JFileChooser chooser = new JFileChooser();
			int resp = chooser.showOpenDialog(this.gui.getParent());
			
			if(resp == JFileChooser.APPROVE_OPTION)
			{
				this.gui.inputFilePath = chooser.getSelectedFile().getAbsolutePath();
				try
				{
					this.gui.graph.addNodesFromFilePath(this.gui.inputFilePath);
				}
				catch(Exception e)
				{
					System.out.println("File does not exist");
				}
				this.gui.graph.performLayoutAlgorithm();
			}
		}
		
	}
	
}