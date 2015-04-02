package mm.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import mm.model.CodiceRisposta;
import mm.model.MatchingAlgorithm;
import mm.persistence.BadFileFormatException;
import mm.persistence.PartitaPersister;
import mm.persistence.PartitaWriter;

public class MyController extends AbstractController {

	public MyController(MatchingAlgorithm matchingAlgorithm,
			PartitaPersister partitaPersister, PartitaWriter partitaWriter) {
		
		super(matchingAlgorithm, partitaPersister, partitaWriter);
		
	}

	@Override
	public void carica() {

		if (mainView == null)
			throw new IllegalStateException("mainView == null");
		
		try {
			partitaPersister.read(new FileInputStream(new File("Partita.dat")));
		} catch (FileNotFoundException e) {
			
			mainView.showMessage(e.getMessage());
			e.printStackTrace();
			
		} catch (IOException e) {
			
			mainView.showMessage(e.getMessage());
			e.printStackTrace();
			
		} catch (BadFileFormatException e) {
			
			mainView.showMessage(e.getMessage());
			e.printStackTrace();
			
		}
		
		mainView.reset();
		
		for(CodiceRisposta cr : partitaCorrente.getGiocate()) {
		
			mainView.addCodiceRisposta(cr);
		
		}
		
	}

	@Override
	public void salva() {
		
		if (mainView == null)
			throw new IllegalStateException("mainView == null");
		if (partitaCorrente.isPartitaChiusa())
			throw new IllegalStateException("partitaCorrente.isPartitaChiusa()");
		
		try {
			
			partitaPersister.write(partitaCorrente, new FileOutputStream(new File("Partita.dat")));
			
		} catch (IOException e) {
			
			mainView.showMessage(e.getMessage());
			e.printStackTrace();
			
		}
			
	}

	@Override
	public void salvaPartita() {

		if (mainView == null)
			throw new IllegalStateException("mainView == null");
		if (!partitaCorrente.isPartitaChiusa())
			throw new IllegalStateException("!partitaCorrente.isPartitaChiusa()");
	
		
		try {
			
			partitaWriter.write(partitaCorrente, new PrintWriter(new FileWriter("Partita.txt")));
			
		} catch (FileNotFoundException e) {
			mainView.showMessage(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			mainView.showMessage(e.getMessage());
			e.printStackTrace();
		}
		
	}

	
	
}
