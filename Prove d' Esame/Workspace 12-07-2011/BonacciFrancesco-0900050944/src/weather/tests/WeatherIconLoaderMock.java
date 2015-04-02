package weather.tests;

import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import weather.model.WeatherIcon;
import weather.persistence.binary.WeatherIconLoader;

public class WeatherIconLoaderMock implements WeatherIconLoader {

    private HashMap<String, WeatherIcon> map;
    
    public WeatherIconLoaderMock()
    {
	map = new HashMap<String, WeatherIcon>();
	
	Icon icon= new ImageIcon(getClass().getClassLoader().getResource("resources/sereno.png"));
	map.put("sereno", new WeatherIcon("Sereno", icon));
	
	icon= new ImageIcon(getClass().getClassLoader().getResource("resources/neve.png"));
	map.put("neve", new WeatherIcon("Neve", icon));
	
	icon= new ImageIcon(getClass().getClassLoader().getResource("resources/tempesta.png"));
	map.put("tempesta", new WeatherIcon("Tempesta", icon));
	
	icon= new ImageIcon(getClass().getClassLoader().getResource("resources/pioggia.png"));
	map.put("pioggia", new WeatherIcon("Pioggia", icon));
	
	icon= new ImageIcon(getClass().getClassLoader().getResource("resources/nebbia.png"));
	map.put("nebbia", new WeatherIcon("Nebbia", icon));
	
	icon= new ImageIcon(getClass().getClassLoader().getResource("resources/nuvoloso.png"));
	map.put("nuvoloso", new WeatherIcon("Nuvoloso", icon));
	
	icon= new ImageIcon(getClass().getClassLoader().getResource("resources/variabile.png"));
	map.put("variabile", new WeatherIcon("Variabile", icon));
    }
    
    @Override
    public WeatherIcon getIcon(String descrizione) {
	return map.get(descrizione);
    }

}
