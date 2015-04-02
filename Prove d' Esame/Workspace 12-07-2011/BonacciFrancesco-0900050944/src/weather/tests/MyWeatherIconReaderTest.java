package weather.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

import weather.persistence.binary.*;
import weather.model.WeatherIcon;

public class MyWeatherIconReaderTest {
    private WeatherIconLoader reader;

    @Before
    public void setUp() throws Exception {
	ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	ObjectOutputStream out = new ObjectOutputStream(outStream);
	HashMap<String, WeatherIcon> map = new HashMap<String, WeatherIcon>();
	Icon icon = new ImageIcon(getClass().getClassLoader().getResource(
		"resources/sereno.png"));
	map.put("sereno", new WeatherIcon("Sereno", icon));
	icon = new ImageIcon(getClass().getClassLoader().getResource(
		"resources/neve.png"));
	map.put("neve", new WeatherIcon("Neve", icon));
	icon = new ImageIcon(getClass().getClassLoader().getResource(
		"resources/tempesta.png"));
	map.put("tempesta", new WeatherIcon("Tempesta", icon));
	icon = new ImageIcon(getClass().getClassLoader().getResource(
		"resources/pioggia.png"));
	map.put("pioggia", new WeatherIcon("Pioggia", icon));
	icon = new ImageIcon(getClass().getClassLoader().getResource(
		"resources/nebbia.png"));
	map.put("nebbia", new WeatherIcon("Nebbia", icon));
	out.writeObject(map);
	out.flush();

	ByteArrayInputStream inputStream = new ByteArrayInputStream(
		outStream.toByteArray());
	reader = new MyWeatherIconReader(inputStream);
    }

    @Test
    public void testGetIcon() {
	WeatherIcon icon = reader.getIcon("sereno");
	assertNotNull(icon);
	assertTrue(icon.getDescription().equalsIgnoreCase("sereno"));
	icon = reader.getIcon("nebbia");
	assertNotNull(icon);
	assertTrue(icon.getDescription().equalsIgnoreCase("nebbia"));
	icon = reader.getIcon("piovere");
	assertNull(icon);

    }

}
