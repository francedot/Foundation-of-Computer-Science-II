package teethcollege.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import teethcollege.model.Insegnamento;
import teethcollege.model.PianoDiStudi;

public class MyPianiReader implements PianiReader {
	
	private final String startRecord = "STUDENTE";
	private final String endRecord = "FINE STUDENTE";
	
	private BufferedReader reader;	//n.b è sempre bufferedreader che incapsula un reader(es filereader o altri)
	
	public MyPianiReader(Reader reader) {

		if (reader == null)
			throw new IllegalArgumentException();
		
		this.reader = new BufferedReader(reader);
		
	}

	@Override
	public List<PianoDiStudi> caricaPianiDiStudi(
			Map<Long, Insegnamento> listaInsegnamenti) throws IOException, MalformedFileException {
		
		ArrayList<PianoDiStudi> target = new ArrayList<PianoDiStudi>();
		PianoDiStudi plan;
		
		while ((plan = readPlan(listaInsegnamenti)) != null)
		{
			target.add(plan);
		}
		
		reader.close();	//chiudi il reader qui!!
		return target;
		
	}

	private PianoDiStudi readPlan(Map<Long, Insegnamento> listaInsegnamenti) throws IOException {
		
		String line = null;
		
		try {
			
			line = reader.readLine();		
			if (line == null || line.isEmpty())
				return null;
			
			StringTokenizer stk = new StringTokenizer(line, "\t\r\n");

			//STUDENTE	0000987654	Giuseppe	Rossi
				
			if (!stk.nextToken().trim().equals(startRecord))
				throw new MalformedFileException("Record d'inizio assente");
	
			String matricola = stk.nextToken().trim();
			Long.parseLong(matricola);	//così l'eccezione viene catturata nel catch
				
			String nome = stk.nextToken().trim();
			String cognome = stk.nextToken().trim();
				
			//PIANO DI STUDI 
			
			line = reader.readLine();
			stk = new StringTokenizer(line, ",\t\r\n");
			String token = stk.nextToken().trim();
				
			if (!token.equals("PIANO DI STUDI"))
				throw new MalformedFileException("Manca il record PIANO DI STUDI");
				
			/*  27991, 28004, 29228, 27993, 27996, 28006, 28011, 26338
				28012, 28000, 28032, 28027, 28030, 28029, 28014, 28020
				28024, 28016, 28015, 28021, 17268
				28072, 32099, 38378
				FINE STUDENTE */
			
			List<Long> listaCodiciIns = getCodiciIns(listaInsegnamenti);
			
			PianoDiStudi currentPlan = new PianoDiStudi(nome, cognome, matricola);	//piano di studi creato
			
			//mi ricavo l'insegnamento a partire dal codice
			
			for (Long codice : listaCodiciIns) {
				
				Insegnamento ins = listaInsegnamenti.get(codice);
				
				if (ins == null)
					throw new MalformedFileException("codice insegnamento non trovato");
				
				//currentPlan.aggiungiInsegnamento(ins);
				currentPlan.getInsegnamenti().add(ins);
			}
			return currentPlan;

		} catch (NumberFormatException | NoSuchElementException e) {

			throw new MalformedFileException(e);
		}
		
	}

	private List<Long> getCodiciIns(Map<Long, Insegnamento> listaInsegnamenti) throws IOException, MalformedFileException {
		
		//Consente di ricavare tutti i codici long degli insegnamenti -- reader già aperto nella posizione giusta --
		//leggo la linea -- memorizzo il token -- se il token è FINE STUDENTE termino la lettura
		
		//leggo tutte le righe fino a che non ce n'è una che contiene FINE STUDENTE (Ultima riga)
		//Ad ogni riga associo il suo stk -- ciclo fino a che ci sono token disponibili -- ogni token 
		//va convertito in codice long
		
		ArrayList<Long> codici = new ArrayList<Long>();
		
		String line = null;
		
		while(!(line = reader.readLine()).equals(endRecord)) {
			
			StringTokenizer stk = new StringTokenizer(line, "\n\r,");
			
			while(stk.hasMoreTokens()) {
				
				String token = stk.nextToken().trim();
				Long codice = Long.parseLong(token);
				codici.add(codice);
				
			}
		}
		
		return codici;
	}

	@Override
	public void close() {
		
		try
		{
			reader.close();
		}catch (Exception e) {
			
		}
		
	}

}
