package main;

import graph.Tribo;
import graph.Indio;
import handlers.Gui;

public class Main {
	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		new Gui();
		Tribo tribo = new Tribo("Enawene-nawe");
		Indio i = tribo.addIndio(1);
		tribo.addIndio(2);
		i.setAnoNascimento(2013);
		tribo.addEdge("haha", "bla", "bla2", true);
		for(Indio indio : i.getFilhos())
			System.out.println(indio.getId());
	}
}