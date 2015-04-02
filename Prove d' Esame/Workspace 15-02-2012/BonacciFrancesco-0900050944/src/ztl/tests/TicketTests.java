package ztl.tests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import ztl.model.*;

public class TicketTests
{

	@Test
	public void testGetEntryTransit()
	{
		Date date = new Date();
		Transit t1 = new Transit("Gate1", Direction.ENTRY, date, "XXX");
		Transit t2 = new Transit("Gate2", Direction.EXIT, new Date(date.getTime() + 1000), "XXX");
		Ticket s = new Ticket(t1, t2);
		
		assertEquals(t1, s.getEntryTransit());
	}

	@Test
	public void testGetExitTransit()
	{
		Date date = new Date();
		Transit t1 = new Transit("Gate1", Direction.ENTRY, date, "XXX");
		Transit t2 = new Transit("Gate2", Direction.EXIT, new Date(date.getTime() + 1000), "XXX");
		Ticket s = new Ticket(t1, t2);
		
		assertEquals(t2, s.getExitTransit());
	}

	@Test
	public void testGetPlate()
	{
		Date date = new Date();
		Transit t1 = new Transit("Gate1", Direction.ENTRY, date, "XXX");
		Transit t2 = new Transit("Gate2", Direction.EXIT, new Date(date.getTime() + 1000), "XXX");
		Ticket s = new Ticket(t1, t2);
		
		assertEquals("XXX", s.getPlate());
	}

	@Test
	public void testGetCost1()
	{
		Date date = new Date();
		Transit t1 = new Transit("Section", Direction.ENTRY, date, "XXX");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.HOUR, 2);
		cal.add(GregorianCalendar.MINUTE, 30);
		Transit t2 = new Transit("Section", Direction.EXIT, cal.getTime(), "XXX");
		Ticket s = new Ticket(t1, t2);
		
		assertEquals(7.5, s.getCost(), 0.01);
	}

	@Test
	public void testGetCost2()
	{
		Date date = new Date();
		Transit t1 = new Transit("Section", Direction.ENTRY, date, "XXX");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DAY_OF_YEAR, 1);
		cal.add(GregorianCalendar.HOUR, 6);
		cal.add(GregorianCalendar.MINUTE, 30);
		Transit t2 = new Transit("Section", Direction.EXIT, cal.getTime(), "XXX");
		Ticket s = new Ticket(t1, t2);
		
		assertEquals(91.5, s.getCost(), 0.01);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCtorErr1()
	{
		Date date = new Date();
		Transit t1 = new Transit("Gate1", Direction.ENTRY, date, "XXX");
		Transit t2 = new Transit("Gate2", Direction.ENTRY, new Date(date.getTime() + 1000), "XXX");
		new Ticket(t1, t2);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCtorErr2()
	{
		Date date = new Date();
		Transit t1 = new Transit("Gate1", Direction.ENTRY, date, "XXX");
		Transit t2 = new Transit("Gate2", Direction.EXIT, date, "XXX");
		new Ticket(t1, t2);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCtorErr3()
	{
		Date date = new Date();
		Transit t1 = new Transit("Gate1", Direction.ENTRY, date, "XXX1");
		Transit t2 = new Transit("Gate2", Direction.EXIT, new Date(date.getTime() + 1000), "XXX2");
		new Ticket(t1, t2);
	}

}
