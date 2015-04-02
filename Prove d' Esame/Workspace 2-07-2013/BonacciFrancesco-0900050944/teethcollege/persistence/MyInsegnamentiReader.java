package teethcollege.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import teethcollege.model.Categoria;
import teethcollege.model.Insegnamento;
import teethcollege.model.Semestre;

public class MyInsegnamentiReader implements InsegnamentiReader {

	private BufferedReader reader;	//n.b è sempre bufferedreader che incapsula un reader(es filereader o altri)
	
	public MyInsegnamentiReader(Reader reader) {

		if (reader == null)
			throw new IllegalArgumentException();
	
		this.reader = new BufferedReader(reader);
		
	}
	
	@Override
	public Map<Long, Insegnamento> caricaInsegnamenti() throws MalformedFileException, IOException {
		
		try {

			Map<Long, Insegnamento> mappaInsegnamenti = new HashMap<Long, Insegnamento>();
			ArrayList<Insegnamento> listaIns = new ArrayList<Insegnamento>();
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				
				if(line.trim().isEmpty()) continue;
				
				StringTokenizer tokenizer = new StringTokenizer(line, ",");

				//27991, Analisi matematica 1,		MAT/05, 	 9, OBBLIGATORIO, S1
				
				String codiceIns = tokenizer.nextToken().trim();
				long codiceInsL = Long.parseLong(codiceIns);
				
				String nomeIns = tokenizer.nextToken().trim();
				
				String settore = tokenizer.nextToken().trim();
				
				int cfu = Integer.parseInt(tokenizer.nextToken().trim());
				
				String categoria = tokenizer.nextToken().trim();
				if (!(categoria.equals("A SCELTA") || categoria.equals("OBBLIGATORIO")))
					throw new MalformedFileException("Categoria non valida");
				Categoria cat = categoria.equals("OBBLIGATORIO") 
						? Categoria.OBBLIGATORIO : Categoria.A_SCELTA;
				
				String semestre = tokenizer.nextToken().trim();
				if (!(semestre.equals("S1") || semestre.equals("S2")))
					throw new MalformedFileException("Semestre non valido");
						
				Semestre sem = semestre.equals("S1") ? Semestre.PRIMO : Semestre.SECONDO;
				
				Insegnamento ins = new Insegnamento(codiceInsL, nomeIns, settore, cfu, sem, cat);
		
				listaIns.add(ins);
				
				for(Insegnamento i : listaIns) {
					
					mappaInsegnamenti.put(i.getCodice(), i);
					
				}
			}
			
			close();
			return mappaInsegnamenti;
			
		} catch (NumberFormatException e) {
			
			close();
			throw new MalformedFileException();
		}
		
		
	}

	@Override
	public void close() {
		
		try {
			reader.close();
		}
		catch (Exception e) {
			
		}
	}

}
