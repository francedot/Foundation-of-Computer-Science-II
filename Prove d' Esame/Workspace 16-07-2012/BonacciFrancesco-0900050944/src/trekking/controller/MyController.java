package trekking.controller;

import java.util.List;

import trekking.model.Difficulty;
import trekking.model.Itinerary;
import trekking.model.MyItinerary;
import trekking.model.Trail;
import trekking.model.Trekking;
import trekking.ui.MessageManager;

public class MyController implements Controller {

	private Trekking trekking;
	private MessageManager messageManager;
	private Itinerary itinerary;
	

	public MyController(Trekking trekking, MessageManager messageManager) {

		this.trekking = trekking;
		this.messageManager = messageManager;
		itinerary = new MyItinerary();
		
	}

	@Override
	public List<Trail> getAllTrails() {

		return trekking.getTrailList();
		
	}

	@Override
	public Itinerary getItinerary() {

		return itinerary;
		
	}

	@Override
	public boolean checkItinerary(double dislivelloMax, double distanzaMax,
			Difficulty difficoltaMax, double difficoltaMedia) {
		
		String messaggio = itinerary.isValid(dislivelloMax, distanzaMax, difficoltaMax, difficoltaMedia);
		
		if (messaggio == null) {
			
			return true;
		
		}
		else {
			
			messageManager.showMessage(messaggio);
			return false;
		}
	}

	@Override
	public void addTrail(Trail s) throws IllegalArgumentException {
		
		itinerary.addTrail(s);

	}

	@Override
	public void reset() {

		itinerary = new MyItinerary();

	}

}
