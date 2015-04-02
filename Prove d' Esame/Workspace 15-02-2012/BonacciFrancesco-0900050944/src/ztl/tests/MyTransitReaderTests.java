package ztl.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import ztl.model.Direction;
import ztl.model.Transit;
import ztl.persistence.MalformedFileException;
import ztl.persistence.MyTransitReader;

public class MyTransitReaderTests
{

	@Test
	public void testRead() throws IOException, MalformedFileException
	{
		String v = "ViaNovelli entrata ED999AP 10/01/2012 12.34\nVialeTimavo uscita ED999AP 10/01/2012 14.20\n";
		StringReader reader = new StringReader(v);
		
		MyTransitReader tr = new MyTransitReader();		
		List<Transit> transits = tr.read(reader);
		
		assertEquals(2, transits.size());
		
		GregorianCalendar cal = new GregorianCalendar();
		
		Transit t = transits.get(0);
		assertEquals("ViaNovelli", t.getGateName());
		assertEquals(Direction.ENTRY, t.getDirection());
		assertEquals("ED999AP", t.getPlate());
		cal.set(GregorianCalendar.DAY_OF_MONTH, 10);
		cal.set(GregorianCalendar.MONTH, GregorianCalendar.JANUARY);
		cal.set(GregorianCalendar.YEAR, 2012);
		cal.set(GregorianCalendar.HOUR_OF_DAY, 12);
		cal.set(GregorianCalendar.MINUTE, 34);
		cal.set(GregorianCalendar.SECOND, 0);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		assertEquals(cal.getTime(), t.getDate());
		
		t = transits.get(1);
		assertEquals("VialeTimavo", t.getGateName());
		assertEquals(Direction.EXIT, t.getDirection());
		assertEquals("ED999AP", t.getPlate());
		cal.set(GregorianCalendar.DAY_OF_MONTH, 10);
		cal.set(GregorianCalendar.MONTH, GregorianCalendar.JANUARY);
		cal.set(GregorianCalendar.YEAR, 2012);
		cal.set(GregorianCalendar.HOUR_OF_DAY, 14);
		cal.set(GregorianCalendar.MINUTE, 20);
		cal.set(GregorianCalendar.SECOND, 0);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		assertEquals(cal.getTime(), t.getDate());
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadTransits_Errors1() throws IOException, MalformedFileException
	{
		String v = "ViaNovelli entrata ED999AP 10/01/2012 12.34\nVialeTimavo NOWAY ED999AP 10/01/2012 14.20\n";
		StringReader reader = new StringReader(v);
		
		MyTransitReader tr = new MyTransitReader();		
		tr.read(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadTransits_Errors2() throws IOException, MalformedFileException
	{		
		String v = "ViaNovelli entrata ED999AP 10/01/2012 12.34\nVialeTimavo uscita ED999AP 10x01/2012 14.20\n";
		StringReader reader = new StringReader(v);
		
		MyTransitReader tr = new MyTransitReader();		
		tr.read(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadTransits_Errors3() throws IOException, MalformedFileException
	{
		String v = "ViaNovelli entrata ED999AP 10/01/2012 12:34\nVialeTimavo uscita ED999AP 10/01/2012 14:20\n";
		StringReader reader = new StringReader(v);
		
		MyTransitReader tr = new MyTransitReader();		
		tr.read(reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadTransits_Errors4() throws IOException, MalformedFileException
	{
		String v = "ViaNovelli entrata ED999AP 10/01/2012 12.34\nVialeTimavo uscita ED999AP10/01/2012 14.20\n";
		StringReader reader = new StringReader(v);
		
		MyTransitReader tr = new MyTransitReader();		
		tr.read(reader);
	}

}
