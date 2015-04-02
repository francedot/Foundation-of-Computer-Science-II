package ztl.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RestrictedArea {

	private Collection<String> authorizedPlates;
	private Map<String, Transit> mappaTargaToTransit;

	public RestrictedArea(Collection<String> authorizedPlates) {
		
		this.authorizedPlates = authorizedPlates;
		mappaTargaToTransit = new HashMap<String, Transit>();
		
	}
	
	public Ticket manageTransit(Transit transit) {
		
		if (!authorizedPlates.contains(transit.getPlate())) {
			
			if (transit.getDirection().equals(Direction.ENTRY)) {
			
				mappaTargaToTransit.put(transit.getPlate(), transit);
			
			} else if (transit.getDirection().equals(Direction.EXIT)) {
				
				Transit entryTransit = mappaTargaToTransit.get(transit.getPlate());
				
				mappaTargaToTransit.remove(transit.getPlate());
				
				Ticket ticket = new Ticket(entryTransit, transit);
				
				return ticket;
				
			}
			
		}
		
		return null;
		
	}
	
}
