package trekking.model;

import java.util.ArrayList;
import java.util.List;

public class MyItinerary implements Itinerary {

	private List<Trail> trailsList;
	
	public MyItinerary() {
		
		trailsList = new ArrayList<Trail>();

	}

	@Override
	public void addTrail(Trail s) throws IllegalArgumentException {

		if (s == null) {
			
			throw new IllegalArgumentException("s = null");
		}
		
		if (trailsList.size() == 0) {
			
			trailsList.add(s);			
		}
		else {
			
			if (!trailsList.get(trailsList.size()-1).getTrailEnd().equals(s.getTrailHead())) {
				
				throw new IllegalArgumentException("il sentiero da aggiungere non inizia dove termina il precedente");
				
			}
			else {
				
				trailsList.add(s);
				
			}
			
		}
		
	}

	@Override
	public List<Trail> getTrails() {

		return trailsList;
		
	}

	@Override
	public String isValid(double dislivelloMax, double distanzaMax,
			Difficulty difficoltaMax, double difficoltaMedia) {
		
		StringBuilder sb = new StringBuilder("");
		
		if (this.calcMaxAltitudeDifference() > dislivelloMax)
			sb.append("dislivello massimo superato\n");
		
		if (this.calcTotalLength() > distanzaMax)
			sb.append("distanza massima superata\n");
		
		if (this.calcMaxDifficulty().getValue() > difficoltaMax.getValue())
			sb.append("difficoltà massima superata\n");
		
		if (this.calcAverageDifficulty() > difficoltaMedia)
			sb.append("difficoltà media superata\n");
		
		if (sb.toString().isEmpty())
			return null;
		else
			return sb.toString();
		
		
	}

	@Override
	public double calcMaxAltitudeDifference() {
		
		double altMax = Double.MIN_VALUE;
		double altMin = Double.MAX_VALUE;
		
		for (Trail t : trailsList) {
			
			if (t.getMaxAltitude() > altMax)
				altMax = t.getMaxAltitude();
			
			if (t.getMinAltitude() < altMin)
				altMin = t.getMinAltitude();
			
		}
		
		return altMax - altMin;

		
	}

	@Override
	public double calcTotalLength() {
		
		double totalLength = 0;
		
		for (Trail t : trailsList) {
			
			totalLength += t.getLength();
			
		}
		
		return totalLength;
		
	}

	@Override
	public Difficulty calcMaxDifficulty() {

		Difficulty maxDifficulty = Difficulty.values()[0];
		
		for (Trail t : trailsList) {
			
			if (t.getDifficulty().getValue() > maxDifficulty.getValue()) {
				
				maxDifficulty = t.getDifficulty();
				
			}
			
		}
		
		return maxDifficulty;
		
	}

	@Override
	public double calcAverageDifficulty() {

		double numS = 0;
		double denS = 0;
		for (Trail t : trailsList) {
			
			numS += t.getLength() * t.getAltitudeDifference() * t.getDifficulty().getValue();
			denS += t.getLength() * t.getAltitudeDifference();
			
		}
		
		return numS / denS;
		
	
	}

}
