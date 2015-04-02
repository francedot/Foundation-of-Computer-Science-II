package weather.persistence.binary;

import weather.model.WeatherIcon;

public interface WeatherIconLoader {

	WeatherIcon getIcon(String descrizione);

}