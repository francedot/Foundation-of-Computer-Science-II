package raptor.tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import raptor.model.Gate;
import raptor.model.SpeedingTicket;
import raptor.model.Transit;

public class SpeedingTicketTests
{

	@Test
	public void testGetInTransit()
	{
		Transit t1 = new Transit("Section", Gate.IN, new Date(), "XXX");
		Transit t2 = new Transit("Section", Gate.OUT, new Date(), "XXX");
		SpeedingTicket s = new SpeedingTicket(100, 120, t1, t2);
		
		assertEquals(t1, s.getInTransit());
	}

	@Test
	public void testGetOutTransit()
	{
		Transit t1 = new Transit("Section", Gate.IN, new Date(), "XXX");
		Transit t2 = new Transit("Section", Gate.OUT, new Date(), "XXX");
		SpeedingTicket s = new SpeedingTicket(100, 120, t1, t2);
		
		assertEquals(t2, s.getOutTransit());
	}

	@Test
	public void testGetSectionName()
	{
		Transit t1 = new Transit("Section", Gate.IN, new Date(), "XXX");
		Transit t2 = new Transit("Section", Gate.OUT, new Date(), "XXX");
		SpeedingTicket s = new SpeedingTicket(100, 120, t1, t2);
		
		assertEquals("Section", s.getSectionName());
	}

	@Test
	public void testGetDate()
	{
		Date date = new Date();
		Transit t1 = new Transit("Section", Gate.IN, new Date(), "XXX");
		Transit t2 = new Transit("Section", Gate.OUT, date, "XXX");
		SpeedingTicket s = new SpeedingTicket(100, 120, t1, t2);
		
		assertEquals(date, s.getDate());
	}

	@Test
	public void testGetPlate()
	{
		Transit t1 = new Transit("Section", Gate.IN, new Date(), "XXX");
		Transit t2 = new Transit("Section", Gate.OUT, new Date(), "XXX");
		SpeedingTicket s = new SpeedingTicket(100, 120, t1, t2);
		
		assertEquals("XXX", s.getPlate());
	}

	@Test
	public void testGetSpeedLimit()
	{
		Transit t1 = new Transit("Section", Gate.IN, new Date(), "XXX");
		Transit t2 = new Transit("Section", Gate.OUT, new Date(), "XXX");
		SpeedingTicket s = new SpeedingTicket(100, 120, t1, t2);
		
		assertEquals(100, s.getSpeedLimit(), 0);
	}

	@Test
	public void testGetActualSpeed()
	{
		Transit t1 = new Transit("Section", Gate.IN, new Date(), "XXX");
		Transit t2 = new Transit("Section", Gate.OUT, new Date(), "XXX");
		SpeedingTicket s = new SpeedingTicket(100, 120, t1, t2);
		
		assertEquals(120, s.getActualSpeed(), 0);
	}

	@Test
	public void testGetFine()
	{
		Transit t1 = new Transit("Section", Gate.IN, new Date(), "XXX");
		Transit t2 = new Transit("Section", Gate.OUT, new Date(), "XXX");
		SpeedingTicket s = new SpeedingTicket(100, 120, t1, t2);
		
		assertEquals(155, s.getFine().getValue(), 0);
	}

}
