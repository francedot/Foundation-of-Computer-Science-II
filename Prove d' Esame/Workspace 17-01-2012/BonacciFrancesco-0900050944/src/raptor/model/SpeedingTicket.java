package raptor.model;

import java.util.Date;

public class SpeedingTicket {

	private double speedLimit;
	private double actualSpeed;
	private Transit inTransit;
	private Transit outTransit;
	
	private int eccesso;
	
	private Fine fasciaMulta;
	
	public SpeedingTicket(double speedLimit, double actualSpeed,
			Transit inTransit, Transit outTransit) {
		
		this.speedLimit = speedLimit;
		this.actualSpeed = actualSpeed;
		this.inTransit = inTransit;
		this.outTransit = outTransit;
		
		//gestire eccessi!
		
		eccesso = (int) (actualSpeed - speedLimit);
		
		if (eccesso > 0 && eccesso <= 10) {
			
			fasciaMulta = Fine.LessThan10;
			
		} else if (eccesso > 10 && eccesso <= 40) {
			
			fasciaMulta = Fine.LessThan40;
			
		} else if (eccesso > 40 && eccesso <= 60) {
			
			fasciaMulta = Fine.LessThan60;
			
		} else if (eccesso > 60) {
			
			fasciaMulta = Fine.Beyond60;
			
		} else if (eccesso < 0) {
			
			throw new IllegalArgumentException("velocità attuale non valida");
		
		}
		
	}

	public double getActualSpeed() {
		
		return actualSpeed;
		
	}
	
	public Date getDate() {
		
		return outTransit.getDate();
		
	}
	
	public Fine getFine() {
		
		return fasciaMulta;
		
	}
	
	public Transit getInTransit() {
		
		return inTransit;
		
	}

	public Transit getOutTransit() {
		
		return outTransit;
		
	}
	
	public String getPlate() {
		
		return inTransit.getPlate();
		
	}
	
	public String getSectionName() {
		
		return inTransit.getSectionName();
		
	}
	
	public double getSpeedLimit() {
		
		return speedLimit;
		
	}

	@Override
	public String toString() {
		return "Violazione di " + eccesso + "Km/h (limite " + speedLimit +
				",velocità registrata " + actualSpeed +")per il veicolo " +
					getPlate() + "--MULTA €" + getFine().getValue() + "\n";
	}

	

	
	
	
	
	
}
