package raptor.tests;

import java.util.ArrayList;

import raptor.model.SpeedingTicket;
import raptor.model.SpeedingTicketCollector;

public class DummySpeedingTicketCollector implements SpeedingTicketCollector
{
	private ArrayList<SpeedingTicket> tickets = new ArrayList<SpeedingTicket>();
	
	@Override
	public void add(SpeedingTicket speedingTicket)
	{
		tickets.add(speedingTicket);
	}
	
	public ArrayList<SpeedingTicket> getTickets()
	{
		return tickets;
	}
}
