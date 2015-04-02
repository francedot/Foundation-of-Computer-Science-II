package weather.model;

import java.io.Serializable;

import javax.swing.Icon;

public class WeatherIcon implements Serializable {

	private static final long serialVersionUID = 1L;
	private String description;
	private Icon icon;

	public WeatherIcon(String description, Icon icon) {
		this.description = description;
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public Icon getIcon() {
		return icon;
	}

}
