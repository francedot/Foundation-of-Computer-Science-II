package weather.ui;

import java.util.SortedSet;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import weather.model.Forecast;

public class WeatherPanel extends JPanel {
	private static final long serialVersionUID = 2665600682017399030L;

	public WeatherPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	private JPanel getPanel(Forecast forecast, Controller controller) {
		JPanel forecastPanel = new JPanel();
		forecastPanel.setLayout(new BoxLayout(forecastPanel, BoxLayout.X_AXIS));

		JLabel label = new JLabel(controller.getIcon(forecast.getWeather())
				.getIcon());
		label.setAlignmentX(CENTER_ALIGNMENT);
		forecastPanel.add(label);

		label = new JLabel(forecast.toString());
		label.setAlignmentX(CENTER_ALIGNMENT);
		forecastPanel.add(label);

		return forecastPanel;
	}

	public void setForecast(SortedSet<Forecast> forecast, Controller controller) {
		removeAll();
		if (forecast == null) {
			add(new JLabel("Non esistono dati per la città o data richiesta"));
		} else {
			for (Forecast f : forecast) {
				JPanel panel = getPanel(f, controller);
				add(panel);
			}
		}
		repaint();
	}

}
