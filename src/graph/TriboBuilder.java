package graph;

import java.io.BufferedReader;
import java.io.FileReader;

public class TriboBuilder {
	
	public static Tribo constroi(String arquivoIndividuos) {
		String line = null;
		String[] s = null;
		Tribo tribo = new Tribo("Enawene-nawe");
		Indio novoIndio;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(arquivoIndividuos));
			int numInd = Integer.parseInt(reader.readLine());
			
			for(int i = 0; i < numInd; i++) {
				line = reader.readLine();
				s = line.split("\\s+");
				novoIndio = tribo.addIndio(Integer.parseInt(s[0]));//cria ou retorna o individuo
				novoIndio.constroi(s[1], s[4], s[5], s[6], s[7], s[2], s[3]);
			}
			reader.close();
		}
		catch(Exception e) {
			System.out.println("Erro na leitura do arquvo de indivíduos");
		}
		return tribo;
	}
}
