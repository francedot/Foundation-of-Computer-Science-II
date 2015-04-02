package raptor.ui;

import raptor.model.*;

public interface Controller
{
	Iterable<Section> getSections();
	Iterable<Transit> getAvailableTransits();
	Iterable<SpeedingTicket> getSpeedingTickets();
	boolean hasTransits();
	
	void putTransitInSystem();
}
