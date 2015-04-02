package zannonia.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import zannonia.model.Autostrada;
import zannonia.model.Tratta;

public class MyAutostradeReader implements AutostradeReader {
	/**
	 * Z1 pc pc-pr pr-mo mo mo-bo bo bo-fi fi
     * Z14 bo bo-rn
     * Z22 mo mo-vr vr vr-tn tn-bz- bz-bre
     * Z4E vr vr-pd pd pd-ve
     * Z13 pd pd-ro ro-bo bo
	 */
	@Override
	public List<Autostrada> readAutostrade(Reader autoReader,
			Map<String, Tratta> tratteMap) throws IOException,
			MalformedFileException {
		
		BufferedReader bufferedReader = new BufferedReader(autoReader);
		ArrayList<Autostrada> autostradeList = new ArrayList<Autostrada>();
		Autostrada autostrada;
		while ((autostrada = readAutostrada(bufferedReader, tratteMap)) != null)
		{
			autostradeList.add(autostrada);
		}
		return autostradeList;
		
	}

	private Autostrada readAutostrada(BufferedReader bufferedReader, Map<String, Tratta> tratteMap) throws IOException, MalformedFileException {
	
		String line = bufferedReader.readLine();
		
		if (line == null)
			return null;
		
		if (line.trim().isEmpty())
			throw new MalformedFileException("Riga vuota - manca tutto");
		
		StringTokenizer stk = new StringTokenizer(line);
		String nome = stk.nextToken();
		
		Autostrada target = new Autostrada(nome);
		
		while(stk.hasMoreTokens()) {
			
			String nomeTratta = stk.nextToken().trim().toUpperCase();
			Tratta corrente = tratteMap.get(nomeTratta);
			
			if (corrente == null)
				throw new MalformedFileException("nome tratta non valido");
			
			target.addTratta(corrente);
			
		}
		
		return target;
		
	}

}
