package raptor.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import raptor.model.Gate;
import raptor.model.Transit;
import raptor.persistence.MalformedFileException;
import raptor.persistence.MyTransitReader;

public class MyTransitReaderTests
{

	@Test
	public void testReadTransits() throws IOException, MalformedFileException
	{
		String v = "A14-BO1-N IN MP333BO 10/01/2012 12.34.57\nA14-BO1-N OUT MP333BO 10/01/2012 12.36.55\n";
		StringReader reader = new StringReader(v);
		
		MyTransitReader tr = new MyTransitReader();		
		List<Transit> transits = tr.readTransits(reader);
		
		assertEquals(2, transits.size());
		
		GregorianCalendar cal = new GregorianCalendar();
		
		Transit t = transits.get(0);
		assertEquals("A14-BO1-N", t.getSectionName());
		assertEquals(Gate.IN, t.getGate());
		assertEquals("MP333BO", t.getPlate());
		cal.set(GregorianCalendar.DAY_OF_MONTH, 10);
		cal.set(GregorianCalendar.MONTH, GregorianCalendar.JANUARY);
		cal.set(GregorianCalendar.YEAR, 2012);
		cal.set(GregorianCalendar.HOUR_OF_DAY, 12);
		cal.set(GregorianCalendar.MINUTE, 34);
		cal.set(GregorianCalendar.SECOND, 57);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		assertEquals(cal.getTime(), t.getDate());
		
		t = transits.get(1);
		assertEquals("A14-BO1-N", t.getSectionName());
		assertEquals(Gate.OUT, t.getGate());
		assertEquals("MP333BO", t.getPlate());
		cal.set(GregorianCalendar.DAY_OF_MONTH, 10);
		cal.set(GregorianCalendar.MONTH, GregorianCalendar.JANUARY);
		cal.set(GregorianCalendar.YEAR, 2012);
		cal.set(GregorianCalendar.HOUR_OF_DAY, 12);
		cal.set(GregorianCalendar.MINUTE, 36);
		cal.set(GregorianCalendar.SECOND, 55);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		assertEquals(cal.getTime(), t.getDate());
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadTransits_Errors1() throws IOException, MalformedFileException
	{
		String v = "A14-BO1-N IxN MP333BO 10/01/2012 12.34.57\nA14-BO1-N OUT MP333BO 10/01/2012 12.36.55\n";
		StringReader reader = new StringReader(v);
		
		MyTransitReader tr = new MyTransitReader();		
		tr.readTransits(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadTransits_Errors2() throws IOException, MalformedFileException
	{
		String v = "A14-BO1-N IN MP333BO 10x01/2012 12.34.57\nA14-BO1-N OUT MP333BO 10/01/2012 12.36.55\n";
		StringReader reader = new StringReader(v);
		
		MyTransitReader tr = new MyTransitReader();		
		tr.readTransits(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadTransits_Errors3() throws IOException, MalformedFileException
	{
		String v = "A14-BO1-N IN MP333BO 10/01/2012 12:34:57\nA14-BO1-N OUT MP333BO 10/01/2012 12.36.55\n";
		StringReader reader = new StringReader(v);
		
		MyTransitReader tr = new MyTransitReader();		
		tr.readTransits(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadTransits_Errors4() throws IOException, MalformedFileException
	{
		String v = "A14-BO1-N IN MP333BO 10/01/2012 12.34.57\nA14-BO1-N OUT MP333BO10/01/2012 12.36.55\n";
		StringReader reader = new StringReader(v);
		
		MyTransitReader tr = new MyTransitReader();		
		tr.readTransits(reader);
	}

}
