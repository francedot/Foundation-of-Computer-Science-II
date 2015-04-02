package weather.tests;

import static org.junit.Assert.*;


import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import weather.model.*;
public class GlobalForecastTest {
	private Collection<GlobalForecast> global=null;

	@Before
	public void setUp() throws Exception {
		TreeSet<PunctualForecast> prev = new TreeSet<PunctualForecast>();
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2011, 7, 12, 2, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date d= cal.getTime();
		PunctualForecast pf= new PunctualForecast("Bologna", d, 18, 87, "sereno");
		prev.add(pf);
		cal.set(2011, 7, 12, 5, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d= cal.getTime();
	    pf= new PunctualForecast("Bologna", d, 18, 87, "sereno");
	    prev.add(pf);
		cal.set(2011, 7, 12, 8, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d= cal.getTime();
	    pf= new PunctualForecast("Bologna", d, 18, 87, "nuvoloso");
	    prev.add(pf);
		cal.set(2011, 7, 12, 11, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d= cal.getTime();
	    pf= new PunctualForecast("Bologna", d, 25, 87, "sereno");
	    prev.add(pf);
	    cal.set(2011, 7, 12, 14, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d= cal.getTime();
	    pf= new PunctualForecast("Bologna", d, 12, 87, "tempesta");
	    prev.add(pf);
	    
	    cal.set(2011, 7, 12, 17, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d= cal.getTime();
	    pf= new PunctualForecast("Bologna", d, 25, 87, "tempesta");
        prev.add(pf);
        
        cal.set(2011, 7, 12, 20, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d= cal.getTime();
	    pf= new PunctualForecast("Bologna", d, 18, 87, "sereno");
	    prev.add(pf);
	    
	    cal.set(2011, 7, 12, 23, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d= cal.getTime();
	    pf= new PunctualForecast("Bologna", d, 10, 87, "sereno");
	    prev.add(pf);
	    
	    
	    cal.set(2011, 7, 13, 5, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d= cal.getTime();
	    pf= new PunctualForecast("Bologna", d, 10, 87, "sereno");
	    prev.add(pf);
	    cal.set(2011, 7, 13, 12, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d= cal.getTime();
	    pf= new PunctualForecast("Bologna", d, 30, 87, "sereno");
	    prev.add(pf);


	    cal.set(2011, 7, 14, 6, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d= cal.getTime();
	    pf= new PunctualForecast("Bologna", d, 12, 87, "nuvoloso");
	    prev.add(pf);

	    cal.set(2011, 7, 14, 15, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d= cal.getTime();
	    pf= new PunctualForecast("Bologna", d, 26, 87, "sereno");
	    prev.add(pf);

        global =  GlobalForecast.calcGlobalForecast(prev);
        assertEquals(3, global.size());
       
		
	}

	@Test
	public void testGetPlace() {
		for(GlobalForecast gf: global)
		{
			assertEquals("Bologna", gf.getPlace());
			
		}
	}

	@Test
	public void testGetDate() {
		Object[] previsioni = global.toArray();
		GlobalForecast glob = (GlobalForecast) previsioni[0];
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2011, 7, 12, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date d= cal.getTime();
		assertEquals(d,glob.getDate());
		
		glob = (GlobalForecast) previsioni[1];
		cal = new GregorianCalendar();
		cal.set(2011, 7, 13, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d= cal.getTime();
		assertEquals(d,glob.getDate());
		
		
		glob = (GlobalForecast) previsioni[2];
		cal = new GregorianCalendar();
		cal.set(2011, 7, 14, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d= cal.getTime();
		assertEquals(d,glob.getDate());
		
		
	}

	@Test
	public void testGetWeather() {
		Object[] previsioni = global.toArray();
		GlobalForecast glob = (GlobalForecast) previsioni[0];
		assertEquals("nuvoloso", glob.getWeather());
		glob = (GlobalForecast) previsioni[1];
		assertEquals("sereno", glob.getWeather());
		glob = (GlobalForecast) previsioni[2];
		assertEquals("variabile", glob.getWeather());
	}

	@Test
	public void testGetMinTemp() {
		Object[] previsioni = global.toArray();
		GlobalForecast glob = (GlobalForecast) previsioni[0];
		assertEquals(10, glob.getMinTemp(), 0.01);
		glob = (GlobalForecast) previsioni[1];
		assertEquals(10, glob.getMinTemp(), 0.01);
		glob = (GlobalForecast) previsioni[2];
		assertEquals(12, glob.getMinTemp(), 0.01);
	}

	@Test
	public void testGetMaxTemp() {
		Object[] previsioni = global.toArray();
		GlobalForecast glob = (GlobalForecast) previsioni[0];
		assertEquals(25, glob.getMaxTemp(), 0.01);
		glob = (GlobalForecast) previsioni[1];
		assertEquals(30, glob.getMaxTemp(), 0.01);
		glob = (GlobalForecast) previsioni[2];
		assertEquals(26, glob.getMaxTemp(), 0.01);
	}

}
