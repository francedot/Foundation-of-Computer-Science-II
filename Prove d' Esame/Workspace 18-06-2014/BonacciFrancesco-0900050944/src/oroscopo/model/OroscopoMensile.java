package oroscopo.model;

public class OroscopoMensile implements Oroscopo, Comparable<Oroscopo> {

	//private String nomeSegnoZodiacale;
	private SegnoZodiacale segnoZodiacale;
	private Previsione amore;
	private Previsione lavoro;
	private Previsione salute;
	private int fortunaMedia;
	
	private static int getNumOrder(SegnoZodiacale segnoZodiacale) {
		
		/**
		 * ARIETE, TORO, GEMELLI, CANCRO, LEONE, VERGINE, 
	 BILANCIA, SCORPIONE, SAGITTARIO, CAPRICORNO, ACQUARIO, PESCI;
		 */
		
		switch (segnoZodiacale) {
		
			case ARIETE: return 1; 
			case TORO: return 2; 
			case GEMELLI: return 3; 
			case CANCRO: return 4;
			case LEONE: return 4;
			case VERGINE: return 6;
			case BILANCIA: return 7;
			case SCORPIONE: return 8;
			case SAGITTARIO: return 9;
			case CAPRICORNO: return 10;
			case ACQUARIO: return 11;
			case PESCI: return 12;
			default: throw new IllegalArgumentException("segno non valido");
		
		}
		
	}
	
	@Override
	public int compareTo(Oroscopo that) {
		// ordinare le previsioni secondo l'ordinde dei segni
			
		if (OroscopoMensile.getNumOrder(this.segnoZodiacale) < OroscopoMensile.getNumOrder(that.getSegnoZodiacale())) return -1;
		if (OroscopoMensile.getNumOrder(this.segnoZodiacale) > OroscopoMensile.getNumOrder(that.getSegnoZodiacale())) return 1;
		else return 0;	
			
	}
	
	@Override
	public int getFortuna() {

		return fortunaMedia;
		
	}
	
	@Override
	public Previsione getPrevisioneAmore() {

		return amore;
		
	}
	
	@Override
	public Previsione getPrevisioneLavoro() {

		return lavoro;
		
	}
	
	@Override
	public Previsione getPrevisioneSalute() {

		return salute;
		
	}
	
	@Override
	public SegnoZodiacale getSegnoZodiacale() {
		
		return segnoZodiacale;
		
	}

	public OroscopoMensile(String nomeSegnoZodiacale, Previsione amore,
			Previsione lavoro, Previsione salute) {

		//this.segnoZodiacale = segnoZodiacale;
		
		//verificare che la stringa passata rappresenti un valore valido dell' enumerativo
		
		if (nomeSegnoZodiacale == null || amore == null || lavoro == null || salute == null) {
			
			throw new IllegalArgumentException("parametro oroscopo = null");
			
		}
		
		
		//lancia illegal se non esiste un valore dell' enum
		this.segnoZodiacale = SegnoZodiacale.valueOf(nomeSegnoZodiacale);
		
		//verificare che le 3 previsioni siano valide per il segno dato -- isvalid di previsione
		
		if (!amore.validaPerSegno(segnoZodiacale)) {
			
			throw new IllegalArgumentException("previsione amore non valida per questo segno zodiacale");
			
		}
		
		if (!lavoro.validaPerSegno(segnoZodiacale)) {
			
			throw new IllegalArgumentException("previsione lavoro non valida per questo segno zodiacale");
			
		}
		
		if (!salute.validaPerSegno(segnoZodiacale)) {
			
			throw new IllegalArgumentException("previsione salute non valida per questo segno zodiacale");
			
		}
		
		this.amore = amore;
		this.lavoro = lavoro;
		this.salute = salute;
		
		this.fortunaMedia = (int) Math.round((amore.getValore() + lavoro.getValore() + salute.getValore()) / 3.0);
		
	}
	
	public OroscopoMensile(SegnoZodiacale segnoZodiacale, Previsione amore,
			Previsione lavoro, Previsione salute) {
		
		if (segnoZodiacale == null || amore == null || lavoro == null || salute == null) {
			
			throw new IllegalArgumentException("parametro oroscopo = null");
			
		}
		
		//verificare che le 3 previsioni siano valide per il segno dato -- isvalid di previsione
		
		if (!amore.validaPerSegno(segnoZodiacale)) {
			
			throw new IllegalArgumentException("previsione amore non valida per questo segno zodiacale");
			
		}
		
		if (!lavoro.validaPerSegno(segnoZodiacale)) {
			
			throw new IllegalArgumentException("previsione lavoro non valida per questo segno zodiacale");
			
		}
		
		if (!salute.validaPerSegno(segnoZodiacale)) {
			
			throw new IllegalArgumentException("previsione salute non valida per questo segno zodiacale");
			
		}
		
		this.segnoZodiacale = segnoZodiacale;
		this.amore = amore;
		this.lavoro = lavoro;
		this.salute = salute;
		
		//ciclo dentro le previsioni -- prendo il valore di fortuna -- e calcolo la media!
		
		this.fortunaMedia = (int) Math.round((amore.getValore() + lavoro.getValore() + salute.getValore()) / 3.0);
		
		
	}

	@Override
	public  String toString() {
		return amore.getPrevisione() + " " + lavoro.getPrevisione() + " " + salute.getPrevisione();
	}

	

	

	

	

}
