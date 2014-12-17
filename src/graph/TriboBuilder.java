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
	
	public static void adicionaCasamentos(Tribo tribo, String arquivoCasamentos) {
		
		BufferedReader reader;
		String line = null;
		String[] s = null;
		int numeroCasamentos;
		try {
			reader = new BufferedReader(new FileReader(arquivoCasamentos));
			numeroCasamentos = Integer.parseInt(reader.readLine());
			for(int i = 0; i < numeroCasamentos; i++) {
				line = reader.readLine();
				s = line.split("\\s+");
				//estou ignorando a data do casamento
				tribo.addCasamento(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
			}
			reader.close();
		} 
		catch (Exception e) {
			System.out.println("Erro na leitura do arquivo de casamentos");
		}
	}
}
