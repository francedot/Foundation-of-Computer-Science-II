package zannotaxi.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import zannotaxi.model.TariffaADistanza;
import zannotaxi.model.TariffaATempo;
import zannotaxi.model.TariffaTaxi;

public class MyTariffaTaxiReader implements TariffaTaxiReader {

	BufferedReader reader;
	ArrayList<TariffaTaxi> listaTariffe;
	
	public MyTariffaTaxiReader(Reader reader) {

		if (reader == null)
			throw new IllegalArgumentException();
		
		this.reader = new BufferedReader(reader);
		
		listaTariffe = new ArrayList<TariffaTaxi>();
		
	}


	@Override
	public List<TariffaTaxi> leggiTariffe() throws BadFileFormatException {
		
		try {
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				
				if(line.trim().isEmpty()) continue;
				
				StringTokenizer stk = new StringTokenizer(line, " \t\n\r");

				//(... LEGGI ED AGGIUNGI AL RISULTATO)
				
				String id = stk.nextToken().trim();
				
				String tipo = stk.nextToken().trim();
				
				/*
				 * "T1 DISTANZA	100   \t m\t 0.25 \t 0.00 10.00\n"+"T2 DISTANZA\t85 m   0.35\t \t10.00  25.00\nT3 DISTANZA	 65\t   \t m  	\t0.50\t25.00"
				 * 
				 */
				
				if (tipo.equals("TEMPO")) {
					
					int durataScatto = Integer.parseInt(stk.nextToken().trim());
					
					stk.nextToken(); //a vuoto
					
					double valoreScatto = Double.parseDouble(stk.nextToken().trim());
					
					TariffaTaxi tar = new TariffaATempo(id, valoreScatto, durataScatto);
					
					listaTariffe.add(tar);
					
				}
				else {
					
					/*
					 * "T0 TEMPO	12 s\t0.15\nT1 DISTANZA	100 m\t 0.25 \t 0.00 10.00\n"+
					"T2 DISTANZA\t85 m   0.35\t \t10.00  25.00\nT3 DISTANZA	 65 m  	\t0.50\t25.00"
					 * 
					 */
					
					if (tipo.equals("DISTANZA")) {
						
						int distanzaDiScatto = Integer.parseInt(stk.nextToken().trim());
						
						
						stk.nextToken(); //a vuoto
						
						double valoreScatto = Double.parseDouble(stk.nextToken().trim());
						
						double applicabileDaEuro;
						
						double applicabileFinoAEuro;
						
						applicabileDaEuro = Double.parseDouble(stk.nextToken().trim());
							
						applicabileFinoAEuro = (stk.countTokens() == 1) 
								? Double.parseDouble(stk.nextToken().trim())
										: Double.MAX_VALUE;
							
						TariffaTaxi tar = new TariffaADistanza(id, distanzaDiScatto, valoreScatto, applicabileDaEuro, applicabileFinoAEuro);
						
						listaTariffe.add(tar);
						
					}
				}
				
			}
			
		} catch (Exception e) {
			
			try {
				
				reader.close();
				
			} 
			catch (IOException e1) {
				
				throw new BadFileFormatException(e1);
				
			}
			
			throw new BadFileFormatException(e);
			
		}
		
		try {
			
			reader.close();
			
		} 
		catch (IOException e) {
			
			throw new BadFileFormatException(e);
			
		}
		
		return listaTariffe;
	}
	

}
