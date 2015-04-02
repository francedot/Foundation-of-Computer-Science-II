package ed.model;

public class TariffaAConsumo extends Tariffa {

	private double prezzoKWh;
	private double prezzoKWhExtra;
	private double sogliaMensile;
	
	@Override
	public Bolletta creaBolletta(Utente utente, int mese, int anno,
			double consumo) {
		
		//restituire opportuna bolletta
		//utente OK
		//mese, anno, consumo OK
		//nomeTariffa OK DA SOPRA
		//LINEEBOLLETTA MANCANO
		
		//GESTIONE LINEEBOLLETTA
		
		/**
		 * 2 CASI : 
		 * 1)(senza soglia) 4 voci per le tariffe a consumo a prezzo singolo :
		 * costo energia, accise, IVA, totale;
		 * 
		 * 2)(con soglia)5 voci le tariffe a consumo a prezzi differenziati :
		 * costo energia entro soglia, costo energia extra soglia, accise, IVA, totale
		 * 
		 * ----
		 * 
		 * 1)
		 * 
		 * 
		 */
		
		
		//CASO 1
		//n.b costi trasp ecc già in formula -- tasse no!
		
		if (!this.hasSogliaMensile() || consumo < sogliaMensile) {
		
			double valoreCostoEnergiaSenzaSoglia = 1.50 * consumo * prezzoKWh;
			
			double valoreCostoEnergiaSenzaSogliaWAccise = 1.50 * consumo * prezzoKWh + calcAccise(consumo);
		
			double totaleEnergiaSenzaSoglia =  valoreCostoEnergiaSenzaSogliaWAccise + calcIVA(valoreCostoEnergiaSenzaSogliaWAccise);
		
			double valoreAccise = calcAccise(consumo);
			
			double valoreIVA = calcIVA(valoreCostoEnergiaSenzaSogliaWAccise);
			
			LineaBolletta lineaSenzaSoglia1 = new LineaBolletta("costo energia",valoreCostoEnergiaSenzaSoglia );
		
			LineaBolletta lineaSenzaSoglia2 = new LineaBolletta("accise", valoreAccise);
		
			LineaBolletta lineaSenzaSoglia3 = new LineaBolletta("IVA", valoreIVA);
		
			LineaBolletta lineaSenzaSoglia4 = new LineaBolletta("totale", totaleEnergiaSenzaSoglia);
		
			Bolletta bolletta = new Bolletta(utente, mese, anno, super.getNome(), consumo);
			
			bolletta.addLineaBolletta(lineaSenzaSoglia1);
			bolletta.addLineaBolletta(lineaSenzaSoglia2);
			bolletta.addLineaBolletta(lineaSenzaSoglia3);
			bolletta.addLineaBolletta(lineaSenzaSoglia4);
			
			return bolletta;
			
		} else if (this.hasSogliaMensile() && consumo > sogliaMensile) {
			
			double valoreCostoEnergiaEntroSoglia = 1.50 * sogliaMensile * prezzoKWh;
			
			double valoreCostoEnergiaOltreSoglia = 1.50 * (consumo - sogliaMensile) * prezzoKWhExtra;
			
			double valoreCostoEnergiaTotSenzaIVA = valoreCostoEnergiaEntroSoglia + valoreCostoEnergiaOltreSoglia + calcAccise(consumo);
			
			double valoreAccise = calcAccise(consumo);
			
			double IVA = calcIVA(valoreCostoEnergiaTotSenzaIVA);
			
			double costoTotaleEnergia = valoreCostoEnergiaTotSenzaIVA + IVA;
			
			Bolletta bolletta = new Bolletta(utente, mese, anno, super.getNome(), consumo);
			
			bolletta.addLineaBolletta(new LineaBolletta("costo energia entro soglia", valoreCostoEnergiaEntroSoglia));
			bolletta.addLineaBolletta(new LineaBolletta("costo energia extra soglia", valoreCostoEnergiaOltreSoglia));
			bolletta.addLineaBolletta(new LineaBolletta("accise", valoreAccise));
			bolletta.addLineaBolletta(new LineaBolletta("IVA", IVA));
			bolletta.addLineaBolletta(new LineaBolletta("totale", costoTotaleEnergia));
			
			return bolletta;
			
		}
		return null;
	}

	public double getPrezzoKWh() {
		
		return prezzoKWh;
		
	}

	public double getPrezzoKWhExtra() {
		
		return prezzoKWhExtra;
		
	}

	public double getSogliaMensile() {
		
		return sogliaMensile;
		
	}
	
	public boolean hasSogliaMensile() {
		
		return sogliaMensile != 0;
		
	}
	
	public TariffaAConsumo(String nome, double prezzoKWh, double sogliaMensile,
			double prezzoKWhExtra) {
		
		super(nome);
		this.prezzoKWh = prezzoKWh;
		this.sogliaMensile = sogliaMensile;
		this.prezzoKWhExtra = prezzoKWhExtra;
		
	}
	
	public TariffaAConsumo(String nome, double prezzoKWh) {
		
		super(nome);
		this.prezzoKWh = prezzoKWh;
		this.prezzoKWhExtra = 0;
		this.sogliaMensile = 0;

	}

	
}
