package mm.persistence;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

import mm.model.Codice;
import mm.model.CodiceRisposta;
//import mm.model.ColoreRisposta;
import mm.model.Partita;
//import mm.model.Risposta;

public class MyPartitaWriter implements PartitaWriter
{

	@Override
	public void write(Partita partita, PrintWriter dest) throws IOException
	{
		//printwriter va SEMPRE E COMUNQUE vvrappato in un buferedwriter!
		//forse buffer troppo piccolo??
		if (partita == null)
			throw new IllegalArgumentException();
		
		StringBuilder sb = new StringBuilder("nni");
		
		BufferedWriter bf = new BufferedWriter(dest);
		
		bf.newLine();
		bf.newLine();
		
		for (CodiceRisposta giocata : partita.getGiocate())
		{
			writeCodice(giocata.getTentativo(), bf);
			bf.newLine();
		}
		
		bf.close();
		
		
	}
	
	private void writeCodice(Codice codice, BufferedWriter bf) throws IOException
	{
		for (int i = 0; i < codice.getCount(); i++)
		{
			bf.write(codice.getColore(i).toString());
			if (i < codice.getCount() - 1)
			{
				bf.write(", ");
			}
		}
	}
	
//	private void writeRisposta(Risposta risposta, BufferedWriter writer) throws IOException
//	{
//		for (int i = 0; i < risposta.getCount(); i++)
//		{
//			ColoreRisposta coloreRisposta = risposta.getColoreRisposta(i);
//			writer.write(coloreRisposta.toString());
//			if (i < risposta.getCount() - 1)
//			{
//				writer.write(" | ");
//			}
//		}
//		if (risposta.allMatch())
//		{
//			writer.write(" ****");
//		}
//	}

}
