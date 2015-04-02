package ed.model;

public class TariffaFlat extends Tariffa {

	private double prezzoKWhExtra;
	private double quotaFissaMensile;
	private double sogliaMensile;
	
	@Override
	public Bolletta creaBolletta(Utente utente, int mese, int anno,
			double consumo) {

		if (consumo <= sogliaMensile) {
			
			Bolletta bolletta = new Bolletta(utente, mese, anno, super.getNome(), consumo);
			
			bolletta.addLineaBolletta(new LineaBolletta("quota fissa mensile", quotaFissaMensile));
			
			bolletta.addLineaBolletta(new LineaBolletta("costo energia extra soglia", 0.0));
			
			bolletta.addLineaBolletta(new LineaBolletta("accise", calcAccise(consumo)));
			bolletta.addLineaBolletta(new LineaBolletta("IVA", calcIVA(quotaFissaMensile + calcAccise(consumo))));
			bolletta.addLineaBolletta(new LineaBolletta("totale", quotaFissaMensile + calcAccise(consumo) + calcIVA(quotaFissaMensile + calcAccise(consumo))));
			
			return bolletta;
			
		} else if (consumo > sogliaMensile) {
			
			Bolletta bolletta = new Bolletta(utente, mese, anno, super.getNome(), consumo);
			
			bolletta.addLineaBolletta(new LineaBolletta("quota fissa mensile", quotaFissaMensile));
			
			bolletta.addLineaBolletta(new LineaBolletta("costo energia extra soglia", (consumo - sogliaMensile) * prezzoKWhExtra));
			
			bolletta.addLineaBolletta(new LineaBolletta("accise", calcAccise(consumo)));
			
			bolletta.addLineaBolletta(new LineaBolletta("IVA", calcIVA(quotaFissaMensile + (consumo - sogliaMensile) * prezzoKWhExtra + calcAccise(consumo))));
			
			bolletta.addLineaBolletta(new LineaBolletta("totale", quotaFissaMensile + (consumo - sogliaMensile) * prezzoKWhExtra + calcAccise(consumo) 
										+ calcIVA(quotaFissaMensile + (consumo - sogliaMensile) * prezzoKWhExtra + calcAccise(consumo))));
			
			return bolletta;
			
		}
		
		return null;
	}
	
	public double getPrezzoKWhExtra() {
		
		return prezzoKWhExtra;
		
	}

	public double getQuotaFissaMensile() {
		
		return quotaFissaMensile;
		
	}

	public double getSogliaMensile() {
		
		return sogliaMensile;
		
	}

	public TariffaFlat(String nome, double quotaFissaMensile,
			double sogliaMensile, double prezzoKWhExtra) {
		
		super(nome);
		this.quotaFissaMensile = quotaFissaMensile;
		this.sogliaMensile = sogliaMensile;
		this.prezzoKWhExtra = prezzoKWhExtra;
		
	}

}
