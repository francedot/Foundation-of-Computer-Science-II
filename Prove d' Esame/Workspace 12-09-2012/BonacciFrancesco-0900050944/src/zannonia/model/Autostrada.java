package zannonia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Autostrada { 

	private String nome;
	private List<Tratta> listaTratte;
	
	public void addTratta(Tratta tratta) {
		
		//if (tratta == null)
			//throw new IllegalArgumentException("tratta = null");
		
		if (!listaTratte.contains(tratta)) {
			
			listaTratte.add(tratta);
			tratta.addAutostrada(this);
			
		}
	}
	
	public Autostrada(String nome) {
		
		this.nome = nome;
		listaTratte = new ArrayList<Tratta>();
		
	}
	
	public Tratta getNext(Tratta current, boolean ascending) {
	
		
		int nextIndex = ascending ? listaTratte.indexOf(current) + 1 : listaTratte.indexOf(current) - 1;
		
		if (nextIndex < 0 || nextIndex == listaTratte.size())
			return null;
		else 
			return listaTratte.get(nextIndex);
		
	}

	public String getNome() {
		
		return nome;
		
	}

	public List<Tratta> getTratte() {
		
		return Collections.unmodifiableList(listaTratte);
		
	}

	@Override
	public String toString() {
		
		return nome;
		
	}
	
	
	
	
	
	
}
