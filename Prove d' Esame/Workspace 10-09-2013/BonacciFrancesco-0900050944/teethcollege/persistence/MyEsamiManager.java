package teethcollege.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import teethcollege.model.Esame;
import teethcollege.model.Insegnamento;

public class MyEsamiManager implements EsamiManager {

	//n.b chiusure, malformed specializzato
	@Override
	public List<Esame> caricaEsami(Reader reader,
			Map<Long, Insegnamento> mappaInsegnamenti) throws IOException, MalformedFileException {
		
		if (reader == null)
			throw new IllegalArgumentException();
		
		BufferedReader br = new BufferedReader(reader);
		
		/*
		 *  27991, 20, 20/06/13
			27993, 20, 20/06/13
			27996, 1, 20/06/13
			27996, 6, 20/06/13
			27996, 21, 21/06/13
			28004, 1, 20/06/13
			28004, 21, 20/06/13
		 */
		
		try {
			
			List<Esame> esamiCaricati = new ArrayList<Esame>();
			
			String line = null;
			
			while ((line = br.readLine()) != null) {
				
				if(line.trim().isEmpty()) continue;
				
				StringTokenizer stk = new StringTokenizer(line, "/, \n\r");
				
				long codiceIns = Long.parseLong(stk.nextToken().trim());
				Insegnamento insegnamento = mappaInsegnamenti.get(codiceIns);
				if (insegnamento == null)
					throw new MalformedFileException();
				
				String votoRaw = stk.nextToken();	//occhio a non bruciarti i token!!!
				int voto = Integer.parseInt(votoRaw.trim()) == 31 ? 30
							: Integer.parseInt(votoRaw.trim());
				
				Date data = getDate(stk);
				
				esamiCaricati.add(new Esame(insegnamento, voto, data));
				
			}
			
			br.close();
			return esamiCaricati;
		}
		catch (Exception e) {
			
			br.close();
			throw new MalformedFileException("Errore di formato del file di lettura",e);
			
		}
	}

	private Date getDate(StringTokenizer stk) throws MalformedFileException {
		
		String giorno = stk.nextToken().trim();
		String mese = stk.nextToken().trim();
		
		String rawAnno = stk.nextToken().trim();
		String anno = rawAnno.length() == 4 ? rawAnno.substring(2, 3): rawAnno;
		
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
		
		String data = giorno + "/" + mese + "/" + anno;
		
		Date date = null;
		
		try {
			
			date = df.parse(data);
			
		} catch (ParseException e) {

			throw new MalformedFileException("Data non valida", e);
		}
		
		return date;
	}

	//n.b canonicaltostring
	@Override
	public void salvaEsami(Writer writer, List<Esame> esami) throws IOException {
		
		PrintWriter pw = new PrintWriter(writer, true);	//gestisce da solo il flush ad ogni line -- è l'ideale incapsulamento di un Writer(Filewriter, StringWriter eccc)
		
		for (Esame esame : esami) {
			
			pw.write(esame.toCanonicalString());
			
		}
		
		pw.close();

	}

}
