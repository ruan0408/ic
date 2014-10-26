import java.util.*;
import java.io.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.swingViewer.Viewer;
//import static org.graphstream.algorithm.Toolkit.*;

/*Monto o grafo, descubro o nível de todos os nós, dou um jeito de arrumas as coordenadas com isso, printo*/

public class Main
{
	public static void main(String[] args) throws Exception
	{
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Gui prog = new Gui();
	}
}