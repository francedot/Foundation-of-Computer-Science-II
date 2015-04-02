package teethcollege.model;

import java.util.ArrayList;
import java.util.List;

public class PianoDiStudi {

	private static final Categoria obbligatorio = Categoria.OBBLIGATORIO;
	
	private String nome;
	private String cognome;
	private String matricola;
	private int sommaCrediti;
	private List<Insegnamento> listaInsegnamenti;
	
	
	public PianoDiStudi(String nome, String cognome, String matricola) throws IllegalArgumentException{
		
		if (nome == null || cognome == null || matricola == null)
			throw new IllegalArgumentException("Passato parametro null");
		
		try {
			Long.parseLong(matricola);
		}
		catch(NumberFormatException e) {
			throw new IllegalArgumentException("Matricola non convertibile in numero", e);
		}
		
		listaInsegnamenti = new ArrayList<Insegnamento>();
		
		this.nome = nome;
		this.cognome = cognome;
		this.matricola = matricola;
		
	}
	
	public List<Insegnamento> getInsegnamenti() {
		return listaInsegnamenti;
	}

	public String getNome() {
		return nome;
	}


	public String getCognome() {
		return cognome;
	}


	public String getMatricola() {
		return matricola;
	}

	public long getMatricolaAsLong() {
		
		return Long.parseLong(getMatricola());
	}
	
	public void aggiungiInsegnamento(Insegnamento corso) {
		
		if (getInsegnamenti().contains(corso)) {
			throw new IllegalArgumentException("Insegnamento già presente");
		}
		
		getInsegnamenti().add(corso);
		
	}
	
	public boolean isValid() {
		
		return (getSommaCrediti() == 180);
		
	}
	
	public int getSommaCrediti() {
		
		sommaCrediti = 0;
		
		for (Insegnamento i : getInsegnamenti()) {
			sommaCrediti += i.getCfu();
		}
		
		return sommaCrediti;
	}

	@Override
	public String toString() {
		return getMatricola() + " " + getCognomeNome();
	}
	
	public String getCognomeNome() {
		return getCognome() + " " + getNome();
	}
	
	public String toFullString() {
		
		StringBuilder sb = new StringBuilder("STUDENTE" + toString() + "\n");
		
		for (Insegnamento i : getInsegnamenti()) {
			sb.append(i.toString() + "\n");
		}
		
		return sb.toString();
		
	}
	
	
	public void sostituisciInsegnamento(Insegnamento corrente, Insegnamento futuro) {
		
		if (corrente.getCategoria() == obbligatorio || futuro.getCategoria() == obbligatorio 
				|| futuro.getCfu() != corrente.getCfu() || getInsegnamenti().contains(futuro) 
					|| !getInsegnamenti().contains(corrente)) {
			throw new IllegalArgumentException("Insegnamento non sostituibile");
		}
		
		int currentIndex = getInsegnamenti().indexOf(corrente);
		getInsegnamenti().remove(currentIndex);
		getInsegnamenti().add(currentIndex, futuro);
		
	}
	
}

