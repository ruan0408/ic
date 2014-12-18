package comunidade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TriboBuilder {
	
	public static Tribo constroi(String arquivoIndividuos) 
			throws NumberFormatException, IOException {
		String line = null;
		String[] s = null;
		Tribo tribo = new Tribo("Enawene-nawe");
		Indio novoIndio;
		
		BufferedReader reader = new BufferedReader(new FileReader(arquivoIndividuos));
		int numInd = Integer.parseInt(reader.readLine());
		
		for(int i = 0; i < numInd; i++) {
			line = reader.readLine();
			s = line.split("\\s+");
			novoIndio = tribo.addIndio(Integer.parseInt(s[0]));//cria ou retorna o individuo
			novoIndio.constroi(s[1], s[4], s[5], s[6], s[7], s[2], s[3]);
		}
		reader.close();
		
		return tribo;
	}
	
	//Este método ignora a data do casamento
	public static void adicionaCasamentos(Tribo tribo, String arquivoCasamentos) 
			throws NumberFormatException, IOException{
		
		BufferedReader reader;
		String line = null;
		String[] s = null;
		int numeroCasamentos;
		
		reader = new BufferedReader(new FileReader(arquivoCasamentos));
		numeroCasamentos = Integer.parseInt(reader.readLine());
		for(int i = 0; i < numeroCasamentos; i++) {
			line = reader.readLine();
			s = line.split("\\s+");
			tribo.addCasamento(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
		}
		reader.close();	
	}
}
