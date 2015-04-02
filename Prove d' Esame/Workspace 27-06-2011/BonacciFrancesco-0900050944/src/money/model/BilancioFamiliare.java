package money.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class BilancioFamiliare implements Bilancio {

	private Esterno esterno;
	
	private HashMap<String, Conto> mappaNomeToConto;
	
	public void aggiungiMovimento(Movimento m) {
		
		//prendo i 2 conti coinvolti
		String nomeSorgente = m.getSorgente();
		String nomeDestinazione = m.getDestinazione();
		
		/**
		 * Per garantire la consistenza, l’aggiunta di un movimento in 
		 * un conto dev’essere
		 *  permessa solo se la data di tale 
		 *  movimento è successiva a quella dell’ultimo movimento 
		 *  già presente nel conto
		 *  
		 */
		
		Conto sorgente = mappaNomeToConto.get(nomeSorgente);
		Conto destinazione = mappaNomeToConto.get(nomeDestinazione);
		
		if (sorgente == null || destinazione == null)
			throw new IllegalArgumentException("sorgente == null || destinazione == null");
		
		sorgente.aggiungiMovimento(m);
		destinazione.aggiungiMovimento(m);
		
	}
	
	public BilancioFamiliare(Collection<Conto> conti,
			Collection<Movimento> movimenti) {
		
		mappaNomeToConto = new HashMap<String, Conto>();
		
		for (Conto conto : conti) {
			
			mappaNomeToConto.put(conto.getName(), conto);
			
		}
		
		esterno = new Esterno();
		
		mappaNomeToConto.put(esterno.getName(), esterno);	//c'è sempre!
		
		for (Movimento mov : movimenti) {
					
			this.aggiungiMovimento(mov);
					
		}
		
	}
	
	public Collection<String> checkVincoli(Movimento m) {
		
		ArrayList<String> checks = new ArrayList<String>();
		
		for (Conto conto : getConti()) {
			
			if (conto.checkVincoli(m) != null)
				checks.add(conto.checkVincoli(m));
			
		}
		
		return checks;
		
	}
	
	public Iterable<Conto> getConti() {
		
		return mappaNomeToConto.values();
	
	}
	
	public Conto getConto(String nome) {
		
		return mappaNomeToConto.get(nome);
		
	}
	
	public Collection<Movimento> getMovimenti(Date inizio, Date fine) {

		HashSet<Movimento> movimentiFiltrati = new HashSet<Movimento>();
		
		for (Conto conto : getConti()) {
			
			movimentiFiltrati.addAll(conto.getMovimenti(inizio, fine));	//sono solo la add e la addAll del set che non aggiungono duplicati! non il costruttore in se
		
		}
		
		ArrayList<Movimento> movimentiOrdinati = new ArrayList<Movimento>(movimentiFiltrati);
		
		Collections.sort(movimentiOrdinati, new DateComparator());
		
		return movimentiOrdinati;
		
		
	}
	
	public Collection<Movimento> getMovimenti() {
		
		HashSet<Movimento> movimentiFiltrati = new HashSet<Movimento>();
		
		for (Conto conto : getConti()) {
			
			movimentiFiltrati.addAll(conto.getMovimenti());
			
		}
		
		ArrayList<Movimento> movimentiOrdinati = new ArrayList<Movimento>(movimentiFiltrati);
		
		Collections.sort(movimentiOrdinati, new DateComparator());
		
		return movimentiOrdinati;
		
	}
	
	@Override
	public double getSaldo(Date fine) {
		
		return getTotaleEntrate(new Date(0), fine) - getTotaleUscite(new Date(0), fine);
		
	}

	@Override
	public double getTotaleEntrate(Date inizio, Date fine) {
		
		return this.esterno.getTotaleUscite(inizio, fine);
		
	}

	
	@Override
	public double getTotaleUscite(Date inizio, Date fine) {
		
		return this.esterno.getTotaleEntrate(inizio, fine);
		
	}

	

}
