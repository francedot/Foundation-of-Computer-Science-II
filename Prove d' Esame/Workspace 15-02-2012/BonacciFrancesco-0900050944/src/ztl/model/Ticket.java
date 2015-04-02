package ztl.model;

import java.util.Date;

public class Ticket {

	private Transit entryTransit;
	private Transit exitTransit;
	private double cost;
	private String plate;

	public Transit getEntryTransit() {
		
		return entryTransit;
		
	}

	public Transit getExitTransit() {
		
		return exitTransit;
		
	}

	public double getCost() {
		
		return cost;
	}

	public String getPlate() {
		
		return plate;
		
	}

	public Ticket(Transit entryTransit, Transit exitTransit) {
		
		if (!entryTransit.getPlate().equals(exitTransit.getPlate())) {
		
			throw new IllegalArgumentException("transiti non relativi allo stesso veicolo");

		}
		
		if (!entryTransit.getDirection().equals(Direction.ENTRY)) {
			
			throw new IllegalArgumentException("primo transito non è di entrata");
			
		}
		
		if (!exitTransit.getDirection().equals(Direction.EXIT)) {
			
			throw new IllegalArgumentException("secondo transito non è di uscita");
			
		}
		
		if (entryTransit.getDate().compareTo(exitTransit.getDate()) >= 0) {
			
			throw new IllegalArgumentException("il primo transito non è precedente al secondo");

		}
		
		this.entryTransit = entryTransit;
		this.exitTransit = exitTransit;
		
		//calcolare durata permanenza e conseguente importo
		
		/**
		 * La tariffa è uguale tutti i giorni della settimana e pari a € 3 l’ora 
		 * (ad esempio, se si è entrati al martedì
		 * mattina alle 9 e si esce al mercoledì pomeriggio alle 15.30,
		 *  saranno addebitate 30h ½ per un totale di 91,50 €).
		 */
		
		Date dateEntrata = entryTransit.getDate();
		Date dateUscita = exitTransit.getDate();
		
		long durataMS = dateUscita.getTime() - dateEntrata.getTime();
		
		double durataMin = durataMS / 60000;
		
		cost = durataMin / 20 ;
	
		plate = entryTransit.getPlate();
		
	}


	@Override
	public String toString() {
		
		return "Plate '" + plate + "' - Entry '" + entryTransit
				+ "'- Exit '" + exitTransit + "' Total Cost:" + cost + "\n";
		
	}
	
	
	
}