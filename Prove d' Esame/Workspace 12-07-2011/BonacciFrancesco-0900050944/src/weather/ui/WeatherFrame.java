package weather.ui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class WeatherFrame extends JFrame implements ActionListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	private Controller controller;

	private JComboBox<String> comboCitta;

	private JSpinner dateSpinner;

	private JRadioButton radioPuntuale;

	private JRadioButton radioGlobale;

	private WeatherPanel weatherPanel;
	
	private WeatherFrame(Controller controller) throws HeadlessException {
		
		super();
		this.controller = controller;
		
		initGUI();
		bindData();
		
		
		
	}

	private void initGUI() {
		
		this.setTitle("Previsioni Meteo");
		
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel leftPanel = new JPanel();
		{
			leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
			
			leftPanel.add(new JLabel("Città:"));
			
			comboCitta = new JComboBox<String>();
			comboCitta.addActionListener(this);
			leftPanel.add(comboCitta);

			leftPanel.add(new JLabel("Data:"));
			
			dateSpinner = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "EEE dd/MM/yyyy");
			dateSpinner.setEditor(dateEditor);
			dateSpinner.addChangeListener(this);
			leftPanel.add(dateSpinner);
			
			leftPanel.add(new JLabel("Tipo di previsione:"));
			
			radioPuntuale = new JRadioButton("Puntuale");
			radioPuntuale.addActionListener(this);
			leftPanel.add(radioPuntuale);
			
			radioGlobale = new JRadioButton("Globale");
			radioGlobale.addActionListener(this);
			leftPanel.add(radioGlobale);
			
			ButtonGroup group = new ButtonGroup();
			group.add(radioPuntuale);
			group.add(radioGlobale);
			
		}
		this.getContentPane().add(leftPanel, BorderLayout.LINE_START);
		
		JPanel centerPanel = new JPanel();
		{
			
			centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
			
			centerPanel.add(new JLabel("Previsioni richieste:"));
			
			weatherPanel = new WeatherPanel();
			centerPanel.add(weatherPanel);
	
		}
		this.getContentPane().add(new JScrollPane(centerPanel), BorderLayout.CENTER);
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}

	private void bindData() {
		
		for (String city : controller.getCities()) {
			
			comboCitta.addItem(city);
			
		}
			
		
		
	}
	
	private void refreshForecast() {
		
		String city = (String) comboCitta.getSelectedItem();
		Date currentDate = (Date) dateSpinner.getValue();
		
		if (radioGlobale.isSelected()) {
			
			
			weatherPanel.setForecast(controller.searchForecast(city, currentDate, true), controller);
			
			
		} else if (radioPuntuale.isSelected()) {
			
			weatherPanel.setForecast(controller.searchForecast(city, currentDate, false), controller);
			
		}
		
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		
		refreshForecast();
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		refreshForecast();
		
	}
	
	public static void main (String[] args) {
		
		WeatherFrame frame = new WeatherFrame(null);
		
	}
	
}
