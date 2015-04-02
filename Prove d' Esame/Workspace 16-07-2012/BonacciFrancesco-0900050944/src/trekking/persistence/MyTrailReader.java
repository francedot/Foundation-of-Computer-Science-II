package trekking.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import trekking.model.Difficulty;
import trekking.model.Trail;
import trekking.model.TrailHead;

public class MyTrailReader implements TrailReader {

	BufferedReader innerReader;
	
	public MyTrailReader(Reader reader) {

		if (reader == null)
			throw new IllegalArgumentException();
		
		innerReader = new BufferedReader(reader);
		
	}


	@Override
	public List<Trail> readTrails() throws InvalidTrailFormatException {

		ArrayList<Trail> trailList = new ArrayList<Trail>();
		Trail trail;
		
		while ((trail = readTrail()) != null) {
			
			trailList.add(trail);
			
		}
		
		return trailList;
		
		
	}


	 //GCA129, Segavecchia (925), Corno alle Scale (1725), 3.8 km, Difficolta 2
	 //GCA000, Corno alle Scale (1725), Punta Corno (1732), 1.1 km, Difficolta 3

	
	private Trail readTrail() throws InvalidTrailFormatException {

		try {
			
			String line = innerReader.readLine();		
		
			if (line == null || line.trim().isEmpty())
				return null;
			
			StringTokenizer tokenizer = new StringTokenizer(line, ",\n");
		
			String codicePercorso = tokenizer.nextToken().trim();
			
			String tokenHead = tokenizer.nextToken().trim();
			TrailHead head = getTrailHead(tokenHead);
			
			String tokenEnd = tokenizer.nextToken().trim();
			TrailHead end = getTrailHead(tokenEnd);
			
			String tokenLength = tokenizer.nextToken().trim();
			double length = getTrailLength(tokenLength);
					
			String tokenDifficulty = tokenizer.nextToken().trim();	/** attento se vuoi fare cambi di token con next perchè in quel caso i vecchi delimitatori sono sostituiti!*/
			Difficulty difficolta = getTrailDifficulty(tokenDifficulty);
			
			Trail target = new Trail(head, end, difficolta);
			target.setName(codicePercorso);
			target.setLength(length);
			
			return target;
			
			
		} catch (NoSuchElementException e) {
			
			throw new InvalidTrailFormatException(e.getMessage());
			
		} catch (IOException e) {
			
			throw new InvalidTrailFormatException(e.getMessage());
			
		}
		
		
	}


	private Difficulty getTrailDifficulty(String tokenDifficulty) throws InvalidTrailFormatException {
		// Difficolta 3
		
		StringTokenizer stk = new StringTokenizer(tokenDifficulty, " ");
		if (!stk.nextToken().trim().equals("Difficolta"))
				throw new InvalidTrailFormatException("tag 'Difficolta' mancante");
		
		int difficoltaInt;
		try {
			
			difficoltaInt = Integer.parseInt(stk.nextToken().trim());
		
		} catch (NumberFormatException e) {
			
			throw new InvalidTrailFormatException(e.getMessage());
			
		}
	
		for (Difficulty d : Difficulty.values()) {
			
			if (d.getValue() == difficoltaInt)
				return d;
			
		}
		
		throw new InvalidTrailFormatException("Difficoltà non valida");
		
	}


	private double getTrailLength(String tokenLength) throws InvalidTrailFormatException {
		// 3.8 km
		
		StringTokenizer stk = new StringTokenizer(tokenLength, " ");
		double length;
		
		try {
		
			length = Double.parseDouble(stk.nextToken());
		
		} catch(NumberFormatException e) {
			
			throw new InvalidTrailFormatException(e.getMessage());
			
		}
		if (!stk.nextToken().trim().equals("km"))
			throw new InvalidTrailFormatException("tag 'km' mancante");
		
		return length;
		
		
	}


	private TrailHead getTrailHead(String tokenHead) throws InvalidTrailFormatException {
		
		// Segavecchia (925)
		
		StringTokenizer stk = new StringTokenizer(tokenHead, "()");
		
		String nome = stk.nextToken().trim();
		Double altezza;
		
		try {
		
			altezza = Double.parseDouble(stk.nextToken().trim());

		}
		catch (NumberFormatException e) {
			
			throw new InvalidTrailFormatException(e.getMessage());
			
		}
		
		TrailHead head = new TrailHead();
		head.setName(nome);
		head.setAltitude(altezza);
		
		return head;
		
	}


	public void close() throws IOException {
		
		try {
			
			innerReader.close();
			
		} catch (Exception e) {
			
		}
		
	}
}
