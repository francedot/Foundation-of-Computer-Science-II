package money.ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;

import money.model.Bilancio;
import money.model.BilancioFamiliare;
import money.model.Conto;
import money.model.Movimento;
import money.persistence.ContiReader;
import money.persistence.MovimentiManager;

public class MyController implements Controller {

	private ContiReader contiReader;
	private MovimentiManager movimentiManager;
	private BilancioFamiliare bilancio;
	
	private MyMovimentoController movimentoController;
	private MainFrame mainFrame;
	
	public MyController(ContiReader contiReader,
			MovimentiManager movimentiManager) {
		
		this.contiReader = contiReader;
		this.movimentiManager = movimentiManager;
		
	}

	public boolean load() {
		
		//carica separatamente!!!
		
		Collection<Conto> conti;
		
		try{
			
			FileReader reader = new FileReader("StrutturaBilancio.txt");
			conti = contiReader.readAll(reader);
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Impossibile leggere la struttura del bilancio " + e.getMessage());
			return false;
			
		}
		
		Collection<Movimento> movimenti;
		try	{
			
			FileInputStream inputStream = new FileInputStream("Bilancio.dat");
			movimenti = movimentiManager.leggiMovimenti(inputStream);
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Impossibile leggere i movimenti " + e.getMessage());
			movimenti = new ArrayList<Movimento>();
			
		}
		
		bilancio = new BilancioFamiliare(conti, movimenti);
		
		return true;
		
	}
	
	@Override
	public void save() {
		
		try {
			
			movimentiManager.scriviMovimenti(bilancio.getMovimenti(), new FileOutputStream("Bilancio.dat"));
			
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Errore di I/O" + e.getMessage());
			
		}

	}

	@Override
	public Iterable<String> getConti() {

		ArrayList<String> nomiConti = new ArrayList<String>();
		
		for (Conto conto : bilancio.getConti()) {
			
			nomiConti.add(conto.getName());
			
		}
		
		return nomiConti;
		
	}

	@Override
	public void openMovimenti() {

		movimentoController = new MyMovimentoController(bilancio);
		movimentoController.showUI();
		
	}

	@Override
	public Bilancio getBilancioFamiliare() {

		return bilancio;
		
	}

	@Override
	public Bilancio getConto(String nome) {

		return bilancio.getConto(nome);
		
	}
	
	public void showUI() {
		
		mainFrame = new MainFrame();
		mainFrame.setController(this);
		mainFrame.setVisible(true);
		
	}

}
