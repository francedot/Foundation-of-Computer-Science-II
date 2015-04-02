package happybank.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import happybank.model.AbstractBancomat;
import happybank.model.HappyBancomat;
import happybank.model.TaglioBanconota;

public class MyBancomatConfigurationReader implements
		BancomatConfigurationReader {

	/**
	 * dichiara un metodo configura che prende in ingresso un Reader da
	 * cui leggere e un HappyBancomat da configurare mediante opportuna chiamata al metodo caricaBanconote.
	 */
	@Override
	public void configura(Reader reader, AbstractBancomat bancomat) throws IOException, BadFileFormatException {
		
		if (reader == null)
			throw new IllegalArgumentException();

		try(BufferedReader br = new BufferedReader(reader)) {
			//20 x 400 + 50 x 500
			String line = null;
			while ((line = br.readLine()) != null) {
					
				if(line.trim().isEmpty()) continue;
					
				StringTokenizer stk = new StringTokenizer(line, "x+ ");

				int tPiccoloN = Integer.parseInt(stk.nextToken());
				TaglioBanconota tPiccolo = HappyBancomat.prendiTaglio(tPiccoloN);
				int numPiccoliTagli = Integer.parseInt(stk.nextToken());
				
				int tGrandeN = Integer.parseInt(stk.nextToken());
				TaglioBanconota tGrande = HappyBancomat.prendiTaglio(tGrandeN);
				int numGrandiTagli = Integer.parseInt(stk.nextToken());
				
				bancomat = (HappyBancomat) bancomat;
				bancomat.caricaBanconote(tPiccolo, numPiccoliTagli, tGrande , numGrandiTagli);
				
					
			} 
			
		} catch (NumberFormatException | NoSuchElementException e) {
				
				throw new BadFileFormatException(e);
				
			}
		
		}
		


}
