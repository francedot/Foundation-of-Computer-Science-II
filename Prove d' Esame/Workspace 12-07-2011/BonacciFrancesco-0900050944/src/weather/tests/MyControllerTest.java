package weather.tests;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import weather.model.Forecast;
import weather.model.PunctualForecast;
import weather.persistence.binary.WeatherIconLoader;

import weather.ui.Controller;
import weather.ui.MyController;

public class MyControllerTest {
    Controller controller;

    @Before
    public void setUp() throws Exception {

	Map<String, SortedSet<PunctualForecast>> mappa = new TreeMap<String, SortedSet<PunctualForecast>>();
	DateFormat dateFormat = DateFormat.getDateTimeInstance(
		DateFormat.SHORT, DateFormat.SHORT, Locale.ITALY);

	TreeSet<PunctualForecast> forecastSet = new TreeSet<PunctualForecast>();
	forecastSet.add(new PunctualForecast("Bologna", dateFormat
		.parse("12/07/11 02.00"), 18.5f, 91, "pioggia"));
	forecastSet.add(new PunctualForecast("Bologna", dateFormat
		.parse("12/07/11 05.00"), 16, 92, "nuvoloso"));
	forecastSet.add(new PunctualForecast("Bologna", dateFormat
		.parse("12/07/11 08.00"), 20, 87, "sereno"));
	forecastSet.add(new PunctualForecast("Bologna", dateFormat
		.parse("12/07/11 11.00"), 24, 64, "sereno"));
	forecastSet.add(new PunctualForecast("Bologna", dateFormat
		.parse("12/07/11 14.00"), 27, 58, "tempesta"));
	mappa.put("Bologna", forecastSet);
	
	forecastSet = new TreeSet<PunctualForecast>();
	forecastSet.add(new PunctualForecast("Ferrara", dateFormat
		.parse("12/11/11 02.00"), 18, 91, "nebbia"));
	forecastSet.add(new PunctualForecast("Ferrara", dateFormat
		.parse("12/11/11 05.00"), 16, 92, "neve"));
	forecastSet.add(new PunctualForecast("Ferrara", dateFormat
		.parse("13/11/11 08.00"), 20.8f, 87, "nebbia"));
	mappa.put("Ferrara", forecastSet);

	WeatherIconLoader loader = new WeatherIconLoaderMock(); 

	controller = new MyController(loader, mappa);

    }

    @Test
    public void testGetCities() {
	Set<String> cities = controller.getCities();
	assertEquals(2, cities.size());
	Object[] cit = cities.toArray();
	assertEquals("Bologna", (String) cit[0]);
	assertEquals("Ferrara", (String) cit[1]);
    }

    @Test
    public void testSearchForecast() {
	GregorianCalendar cal = new GregorianCalendar();
	cal.set(2011, 6, 12, 0, 0, 0);
	cal.set(Calendar.MILLISECOND, 0);
	Date d = cal.getTime();
	SortedSet<Forecast> previsioni = controller.searchForecast("Bologna",
		d, false);
	assertEquals(5, previsioni.size());
	previsioni = controller.searchForecast("Bologna", d, true);
	assertEquals(1, previsioni.size());

	// / data non presente
	cal.set(2011, 11, 12, 0, 0, 0);
	cal.set(Calendar.MILLISECOND, 0);
	d = cal.getTime();
	previsioni = controller.searchForecast("Bologna", d, false);
	assertNull(previsioni);

	cal.set(2011, 10, 12, 0, 0, 0);
	cal.set(Calendar.MILLISECOND, 0);
	d = cal.getTime();
	previsioni = controller.searchForecast("Ferrara", d, false);
	assertEquals(2, previsioni.size());

	previsioni = controller.searchForecast("Ferrara", d, true);
	assertEquals(1, previsioni.size());

	cal.set(2011, 10, 13, 0, 0, 0);
	cal.set(Calendar.MILLISECOND, 0);
	d = cal.getTime();
	previsioni = controller.searchForecast("Ferrara", d, false);
	assertEquals(1, previsioni.size());

	previsioni = controller.searchForecast("Ferrara", d, true);
	assertEquals(1, previsioni.size());

    }

    @Test
    public void testGetIcon() {
	assertEquals("Sereno", controller.getIcon("sereno").getDescription());
	assertEquals("Pioggia", controller.getIcon("pioggia").getDescription());
    }

}
