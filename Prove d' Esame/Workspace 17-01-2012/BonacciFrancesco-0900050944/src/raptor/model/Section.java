package raptor.model;

import java.util.ArrayList;

public class Section {

	private String name;
	private double speedLimit;
	private double length;
	
	private SpeedingTicketCollector collector;
	
	private ArrayList<Transit> transitiInEntrata;
	
	public Section(String name, double speedLimit, double length,
			SpeedingTicketCollector collector) {
		
		this.name = name;
		this.speedLimit = speedLimit;
		this.length = length;
		this.collector = collector;
		
		transitiInEntrata = new ArrayList<Transit>();
		
	}

	public String getName() {
		return name;
	}

	public double getSpeedLimit() {
		return speedLimit;
	}

	public double getLength() {
		return length;
	}

	public SpeedingTicketCollector getCollector() {
		return collector;
	}
	
	public void putTransit(Transit transit) {
		
		if (transit == null) {
			
			throw new IllegalArgumentException("transito = null");
			
		}
		
		if (transit.getGate().equals(Gate.IN)) {
			
			transitiInEntrata.add(transit);
			
		} else if (transit.getGate().equals(Gate.OUT)) {
			
			String targa = transit.getPlate();
			
			Transit corrispondenteInEntrata = null;
			
			for (Transit t : transitiInEntrata) {
				
				if (t.getPlate().equals(targa)) {
					
					corrispondenteInEntrata = t;
					
				}
				
			}
			
			if (corrispondenteInEntrata == null) {
				
				throw new IllegalArgumentException("non posso aggiungere un transito in uscita"
						+ "se non ho il corrispondente in entrata");
				
			}
			
			transitiInEntrata.remove(corrispondenteInEntrata);
			
			long dataEntrata = corrispondenteInEntrata.getDate().getTime();
			long dataUscita = transit.getDate().getTime();//n.b non perdere main informazione!
			
			long durataMSDouble = dataUscita - dataEntrata;
			
			double durata = durataMSDouble / 3600000.0; //fa la divisione tra due long che è più imprecisa! valore vicino
			//a 0 diventa 0 e operazione successiva spara a infinito
			
			double vMedia = length / durata;
			
			if (vMedia > speedLimit) {	//caso di violazione limite
				
				SpeedingTicket ticket = new SpeedingTicket(speedLimit, vMedia, corrispondenteInEntrata, transit);
				
				collector.add(ticket);
				
			}
				
		}
			
	}

	
	@Override
	public String toString() {
		
		return name +"-Limit " + speedLimit + ", Length " + length + "Km\n";
		
	}
	
	
	
	
}
