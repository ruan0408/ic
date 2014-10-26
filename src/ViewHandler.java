import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.graphstream.ui.swingViewer.util.Camera;


class ViewHandler implements MouseWheelListener, MouseMotionListener
{	
	/**
	 * 
	 */
	private final Gui gui;

	/**
	 * @param gui
	 */
	ViewHandler(Gui gui) {
		this.gui = gui;
	}

	public void mouseWheelMoved(MouseWheelEvent event)
	{	
		int units = event.getWheelRotation();
		Camera cam = this.gui.view.getCamera();
		if(units > 0)
		{
			System.out.println("down");
			cam.setViewPercent(cam.getViewPercent()+0.1);
		}
		else
		{
			System.out.println("up "+units);
			cam.setViewPercent(cam.getViewPercent()-0.1);
		}
	}	
	
	public void mouseDragged(MouseEvent event)
	{
		System.out.println("ROLA");
		/*Camera cam = view.getCamera();
		org.graphstream.ui.geom.Point3 p = cam.getViewCenter();
		
		cam.setViewCenter(p.x+(p.x-lastX), p.y+(p.y-lastY),0);
		lastX = p.x;
		lastY = p.y;*/
	}
	
	public void mouseMoved(MouseEvent event)
	{
		//System.out.println("UMBA UMBA UMBAE");
	}
}