package raptor.tests;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Test;

import raptor.model.Gate;
import raptor.model.Section;
import raptor.model.Transit;

public class SectionTests
{

	@Test
	public void testGetName()
	{
		Section s = new Section("MyNameXXX", 100, 10, new DummySpeedingTicketCollector());
		assertEquals("MyNameXXX", s.getName());
	}

	@Test
	public void testGetSpeedLimit()
	{
		Section s = new Section("MyNameXXX", 100, 10, new DummySpeedingTicketCollector());
		assertEquals(100, s.getSpeedLimit(), 0);
	}

	@Test
	public void testGetLength()
	{
		Section s = new Section("MyNameXXX", 100, 10, new DummySpeedingTicketCollector());
		assertEquals(10, s.getLength(), 0);
	}

	@Test
	public void testPutTransit1()
	{
		DummySpeedingTicketCollector collector = new DummySpeedingTicketCollector();
		Section s = new Section("MyNameXXX", 100, 10, collector);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(GregorianCalendar.HOUR_OF_DAY, 10);
		cal.set(GregorianCalendar.MINUTE, 10);
		cal.set(GregorianCalendar.SECOND, 10);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		
		Transit t1 = new Transit("MyNameXXX", Gate.IN, cal.getTime(), "XXX");
		
		cal.set(GregorianCalendar.MINUTE, 12);
		Transit t2 = new Transit("MyNameXXX", Gate.OUT, cal.getTime(), "XXX");
		
		s.putTransit(t1);
		assertTrue(collector.getTickets().isEmpty());
		
		s.putTransit(t2);
		assertEquals(1, collector.getTickets().size());
		
		assertEquals(collector.getTickets().get(0).getSpeedLimit(), 100, 0);
		assertEquals(collector.getTickets().get(0).getActualSpeed(), 300, 0);
		assertEquals(collector.getTickets().get(0).getPlate(), "XXX");
		assertEquals(collector.getTickets().get(0).getSectionName(), "MyNameXXX");
		assertEquals(collector.getTickets().get(0).getInTransit(), t1);
		assertEquals(collector.getTickets().get(0).getOutTransit(), t2);
	}

	@Test
	public void testPutTransit2()
	{
		DummySpeedingTicketCollector collector = new DummySpeedingTicketCollector();
		Section s = new Section("MyNameXXX", 400, 10, collector);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(GregorianCalendar.HOUR_OF_DAY, 10);
		cal.set(GregorianCalendar.MINUTE, 10);
		cal.set(GregorianCalendar.SECOND, 10);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		
		Transit t1 = new Transit("MyNameXXX", Gate.IN, cal.getTime(), "XXX");
		
		cal.set(GregorianCalendar.MINUTE, 12);
		Transit t2 = new Transit("MyNameXXX", Gate.OUT, cal.getTime(), "XXX");
		
		s.putTransit(t1);
		assertTrue(collector.getTickets().isEmpty());
		
		s.putTransit(t2);
		assertTrue(collector.getTickets().isEmpty());
	}

	@Test
	public void testPutTransit3()
	{
		DummySpeedingTicketCollector collector = new DummySpeedingTicketCollector();
		Section s = new Section("MyNameXXX", 100, 10, collector);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(GregorianCalendar.HOUR_OF_DAY, 10);
		cal.set(GregorianCalendar.MINUTE, 10);
		cal.set(GregorianCalendar.SECOND, 10);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		
		Transit t1 = new Transit("MyNameXXX", Gate.IN, cal.getTime(), "XXX1");
		s.putTransit(t1);
		assertTrue(collector.getTickets().isEmpty());
		
		cal.set(GregorianCalendar.MINUTE, 12);
		Transit t2 = new Transit("MyNameXXX", Gate.IN, cal.getTime(), "XXX2");		
		s.putTransit(t2);
		assertTrue(collector.getTickets().isEmpty());
		
		cal.set(GregorianCalendar.MINUTE, 50);
		Transit t3 = new Transit("MyNameXXX", Gate.OUT, cal.getTime(), "XXX1");
		s.putTransit(t3);
		assertTrue(collector.getTickets().isEmpty());
		
		cal.set(GregorianCalendar.MINUTE, 14);
		Transit t4 = new Transit("MyNameXXX", Gate.OUT, cal.getTime(), "XXX2");
		s.putTransit(t4);
		assertEquals(1, collector.getTickets().size());
	}

}
