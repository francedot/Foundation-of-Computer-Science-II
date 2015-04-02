package teethcollege.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Carriera {

	private PianoDiStudi pianoDiStudi;//piano di studio studente
	private List<Esame> listaEsami;//esami sostenuti

	private Map<Long, List<Esame>> mappaCodiceInsToEsami;
	
	public Carriera(PianoDiStudi pianoDiStudi, List<Esame> listaEsami) {
		
		this.pianoDiStudi = pianoDiStudi;
		this.listaEsami = listaEsami;
		
		mappaCodiceInsToEsami = new HashMap<Long, List<Esame>>();
		
		for (Insegnamento ins : pianoDiStudi.getInsegnamenti()) {
			
			long codiceInsCorrente = ins.getCodice();
			
			List<Esame> esamiSostenutiPerIns = new ArrayList<Esame>();
			
			for (Esame esame : listaEsami) {
		
			//vedo se il codice è uguale all'insegnamento per quell'esame
				
				if (codiceInsCorrente == esame.getInsegnamento().getCodice()) {
					
					esamiSostenutiPerIns.add(esame);
					
				}
			}
			
			mappaCodiceInsToEsami.put(codiceInsCorrente, esamiSostenutiPerIns);
			
		}
		
		
		
		
	}
	
	public Carriera(PianoDiStudi pianoDiStudi) {
		
		this(pianoDiStudi, new ArrayList<Esame>());	//lista esami vuota (non null!)
		
	}

	public PianoDiStudi getPianoDiStudi() {
		return pianoDiStudi;
	}

	//ritorna la lista dei soli esami sostenuti per un dato insegnamento
	public List<Esame> getListaEsami(long codiceInsegnamento) {
		
		List<Esame> esamiPerIns = mappaCodiceInsToEsami.get(codiceInsegnamento);
		
		if (esamiPerIns.size() == 0)
			return null;
		
		return esamiPerIns;
			
	}
	
	public List<Esame> getEsamiSostenuti() {
		
		return listaEsami;
		
	}
	
	public List<Esame> getEsamiSuperati() {
		
		List<Esame> esamiSuperati = new ArrayList<Esame>();
		
		for (Esame esame : listaEsami) {
			
			if (esame.getValoreVoto() >= 18)
				esamiSuperati.add(esame);
			
		}
		
		return esamiSuperati;
		
	}
	
	public int getCreditiAcquisiti() {
		
		int cfu = 0;
		
		List<Esame> esamiSuperati = getEsamiSuperati();
		
		for (Esame esame : esamiSuperati) {
			
			cfu += esame.getInsegnamento().getCfu();
			
		}
		
		return cfu;
	}
	
	public double getMediaPesata() {
		
		double acc = 0;
		
		List<Esame> esamiSuperati = getEsamiSuperati();
		
		if (esamiSuperati.size() == 0)
			return 0;
		
		for (Esame esame : esamiSuperati) {
			
			acc += esame.getValoreVoto() * esame.getInsegnamento().getCfu();
			
		}
		
		double media = acc / getCreditiAcquisiti();
		
		//non passare per i formatter perchè può darsi che certi numeri non esistano!
		return Math.rint(100*media)/100;
		//22,8999-- 2289,99-- 2289.00 --22,89 TRUCCHETTO DI ZANNO!
		
	}
	
	//aggiunge l’esame passato come argomento alla lista degli esami sostenuti 
	//per l’insegnamento corrispondente
	public void addEsame(Esame esame) {
		
		long codiceInsEsame = esame.getInsegnamento().getCodice();
		
		boolean presente = false;
		for (Insegnamento ins : pianoDiStudi.getInsegnamenti()) {
			
			if (ins.getCodice() == codiceInsEsame)
				presente = true;
			
		}
		
		if(!presente)
			throw new IllegalArgumentException("Esame non presente nel piano di studi");
		
		for (Esame e : getEsamiSuperati()) {
			
			if (e.getInsegnamento().getCodice() == codiceInsEsame)
				throw new IllegalArgumentException("Esame già sostenuto con esito positivo");
			
		}
		
		
		listaEsami.add(esame);

	}

	@Override
	public String toString() {
		
		return pianoDiStudi.toString();
		
	}

	//toFullString(boolean mostraDettagli) deve mostrare la situazione complessiva della carriera 
	//in termini di numero di esami sostenuti, numero di esami superati, crediti acquisiti 
	//e media pesata; se il flag mostraDettagli è vero, deve mostrare anche l’elenco dettagliato, 
	//esame per esame, sia di tutti gli esami sostenuti sia di quelli superati
	public String toFullString(boolean mostraDettagli) {
		
		StringBuilder sb = new StringBuilder("Situazione esami per " + pianoDiStudi.getCognomeNome() + "\n");
		
		sb.append("Esami sostenuti: " + getEsamiSostenuti().size() + "\n");
		sb.append("Esami superati: " + getEsamiSuperati().size() + "\n");
		sb.append("Crediti acquisiti: " + getCreditiAcquisiti() + "\n");
		sb.append("Media Pesata: " + getMediaPesata() + "\n");
		
		if (mostraDettagli) {
			
			sb.append("Dettaglio esami sostenuti:\n");
			for (Esame esame : getEsamiSostenuti()) {
				
				sb.append(esame.toString());
				
			}
			
			sb.append("Dettaglio esami superati:\n");
			for (Esame esame : getEsamiSuperati()) {
				
				sb.append(esame.toString());
				
			}
		}
		
		return sb.toString();
		
	}
	
}
