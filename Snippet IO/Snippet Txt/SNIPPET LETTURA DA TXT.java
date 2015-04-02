CLASSE DI LETTURA DI LISTA_DI_OGGETTI_MODEL

	private BufferedReader reader;

	public COSTRUTTORE(Reader reader) {
		if (reader == null)
			throw new IllegalArgumentException();
		
		this.reader = new BufferedReader(reader);
	}


METODO_DI_CARICAMENTO {
		try {

			ISTANZIA_LISTA RISULTATO
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				
				if(line.trim().isEmpty()) continue;
				
				StringTokenizer tokenizer = new StringTokenizer(line, "SEPARATORI");

				(... LEGGI ED AGGIUNGI AL RISULTATO)
				
			}
			close();

			return RISULTATO;

		} catch (Exception e) {
			close();
			throw new ECCEZIONESPECIALIZZATA(e);
		}
	}
	
	private void close() {
		try {
			reader.close();
		} catch (Exception e) {
			
		}
	}
	
	
	
Possibile implementazione corretta: caso meno complesso

public List<PianoDiStudi> caricaPianiDiStudi(
			Map<Long, Insegnamento> listaInsegnamenti) throws IOException, MalformedFileException {
		
		ArrayList<PianoDiStudi> target = null;
		
		try {

			target = new ArrayList<PianoDiStudi>();
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				
				if(line.trim().isEmpty()) continue;
				
				StringTokenizer stk = new StringTokenizer(line, "\\s+");

				stk.nextToken();	//a vuoto
				String matricola = stk.nextToken();
				String cognome = stk.nextToken();
				String nome = stk.nextToken();
				
				PianoDiStudi currentPlan = new PianoDiStudi(nome, cognome, matricola);
				
				while((line = reader.readLine()).startsWith("FINE") && line != null) {
				
					if(line.trim().isEmpty()) continue;
					
					String codiceInsegnamento = stk.nextToken(",\n").trim();
					
					long codiceInsLong = Long.parseLong(codiceInsegnamento);
					
					Insegnamento insCorrente = listaInsegnamenti.get(codiceInsLong);	//recuperato da mappa
					currentPlan.getInsegnamenti().add(insCorrente);
					
				}
				
				target.add(currentPlan);
				
			}
			close();

			return target;

		} catch (NumberFormatException e) {
			
			close();
			throw new MalformedFileException("Errore conversione matricola", e);
		}
}		
	
	
	
	
	
	
	