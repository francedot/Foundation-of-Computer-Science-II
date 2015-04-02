package zannonia.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tratta {

	private String id;
	private double pedaggio;
	private double lunghezza;
	private Set<Autostrada> listaAutostrade;
	private List<Casello> listaCaselli;
	
	public void addAutostrada(Autostrada autostrada) {
		
		//if (autostrada == null)
			//throw new IllegalArgumentException("autostrada = null");
		
		if (listaAutostrade.add(autostrada)) {
			
			autostrada.addTratta(this);
			
		}
		
	}
	
	public void addCasello(Casello casello) {
		
		//if (casello == null)
			//throw new IllegalArgumentException("casello = null");
		
		if (!listaCaselli.contains(casello)) {
			
			listaCaselli.add(casello);
			casello.setTratta(this);
			
		}
	}
	
	public Set<Autostrada> getAutostrade() {
		
		return listaAutostrade;
		
	}
	
	public List<Casello> getCaselli() {
		
		return listaCaselli;
		
	}

	public String getId() {
		
		return id;
		
	}

	public double getLunghezza() {
		
		return lunghezza;
		
	}
	
	public double getPedaggio() {
		
		return pedaggio;
		
	}

	/**
	 * ossia una di quelle tratte che
	 * appartengono a due o più autostrade
	 */
	public boolean isTrattaInterscambio() {
		
		return (listaAutostrade.size() > 1);
		
	}

	@Override
	public String toString() {
	
		StringBuilder sb = new StringBuilder();
		
		for (Casello c : listaCaselli) {
			
			sb.append(c.toString());
			sb.append(",");
			
		}
		
		return sb.toString();
		
	}

	public Tratta(String id, double pedaggio, double lunghezza) {
	
		this.id = id;
		this.pedaggio = pedaggio;
		this.lunghezza = lunghezza;
		listaAutostrade = new HashSet<Autostrada>();
		listaCaselli = new ArrayList<Casello>();
		
	}
	
	
	
}
