package trekking.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import trekking.controller.Controller;
import trekking.model.Difficulty;
import trekking.model.Trail;

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;

	private JComboBox<Trail> comboSentieri;

	private JPanel jPanelOptions;

	private JLabel labelDiffMax;

	private JComboBox<Object> comboDifficolta;

	private JLabel labelDislMax;

	private JTextField campoDislivello;

	private JLabel labelLunMax;

	private JTextField campoLunghezza;

	private JLabel labelDiffMedia;

	private JTextField campoDiffMedia;

	private JButton buttonAggiungi;

	private JButton buttonControlla;

	private JButton buttonReset;

	private JButton buttonStampa;

	private JTextArea areaItinerario;

	
	public MainFrame(Controller controller) {
	
		this.controller = controller;
		
		this.getContentPane().setLayout(new BorderLayout());
		
		comboSentieri = new JComboBox<Trail>();
		for (Trail t : controller.getAllTrails()) {
			
			comboSentieri.addItem(t);
			
		}
		this.add(comboSentieri, BorderLayout.PAGE_START);
		
		jPanelOptions = new JPanel();
		{
			
			jPanelOptions.setLayout(new GridLayout(6, 2));
			
			labelDiffMax = new JLabel("Difficoltà Max");
			jPanelOptions.add(labelDiffMax);
			
			comboDifficolta = new JComboBox<>();
			for (Difficulty d : Difficulty.values()) {
				
				comboDifficolta.addItem(d);
				
			}
			jPanelOptions.add(comboDifficolta);
			comboDifficolta.addActionListener(this);
			
			labelDislMax = new JLabel("Dislivello Max");
			jPanelOptions.add(labelDislMax);
			
			campoDislivello = new JTextField(10);
			campoDislivello.setEditable(true);
			jPanelOptions.add(campoDislivello);
			campoDislivello.addActionListener(this);
			
			labelLunMax = new JLabel("Lunghezza Max");
			jPanelOptions.add(labelLunMax);
			
			campoLunghezza = new JTextField(10);
			campoLunghezza.setEditable(true);
			jPanelOptions.add(campoLunghezza);
			campoLunghezza.addActionListener(this);
			
			labelDiffMedia = new JLabel("Difficoltà media");
			jPanelOptions.add(labelDiffMedia);
			
			campoDiffMedia = new JTextField(10);
			campoDiffMedia.setEditable(true);
			jPanelOptions.add(campoDiffMedia);
			campoDiffMedia.addActionListener(this);
			
			buttonAggiungi = new JButton("Aggiungi");
			jPanelOptions.add(buttonAggiungi);
			buttonAggiungi.addActionListener(this);
			
			buttonControlla = new JButton("Controlla");
			jPanelOptions.add(buttonControlla);
			buttonControlla.addActionListener(this);
			
			buttonReset = new JButton("RESET");
			jPanelOptions.add(buttonReset);
			buttonReset.addActionListener(this);
			
			buttonStampa = new JButton("Stampa");
			jPanelOptions.add(buttonStampa);
			buttonStampa.addActionListener(this);
			
		}
		this.add(jPanelOptions, BorderLayout.LINE_START);
		
		areaItinerario = new JTextArea("Nessun sentiero inserito", 100, 100);
		areaItinerario.setEditable(false);
		this.add(areaItinerario, BorderLayout.CENTER);
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}


	@Override
	public void actionPerformed(ActionEvent evt) {

		if (evt.getSource() == buttonAggiungi) {
			
			Trail daAggiungere = (Trail) comboSentieri.getSelectedItem();

			try {
				
				controller.getItinerary().addTrail(daAggiungere);
				
			} catch (IllegalArgumentException e) {
				
				new SwingMessageManager().showMessage("sentiero non compatibile con l'itinerario");
				
			}
			
			aggiornaAreaItinerario();
			
		} else if (evt.getSource() == buttonControlla) {
			
			controlla();

		} else if (evt.getSource() == buttonReset) {
			
			areaItinerario.setText("Nessun sentiero inserito");
			controller.reset();
			
		} else if (evt.getSource() == buttonStampa) {
			
			File f = new File("Itinerari.txt");
			
			
			
			try {
				
				PrintWriter pw = new PrintWriter(new FileWriter(f, true), true);
				
				pw.println(areaItinerario.getText());// solo printf e println usano autoflash
				
			} catch (IOException e) {
				// should never occur here due to pre-condition check
			}
			
			
			
			
			
		} else {
			
			controlla();
			
		}
		
		
	}


	private void controlla() {
		
		double dislivelloMax;
		
		try {
			
			dislivelloMax = Double.parseDouble(campoDislivello.getText().trim());
			
		}
		catch (NumberFormatException e) {
			
			new SwingMessageManager().showMessage("Dislivello Max non è un numero");
			return;
			
		}
	
		double distanzaMax;
		
		try {
			
			distanzaMax = Double.parseDouble(campoLunghezza.getText().trim());
			
		}
		catch (NumberFormatException e) {
			
			new SwingMessageManager().showMessage("Lunghezza Max non è un numero");
			return;
		}
			
		Difficulty diffMax = (Difficulty) comboDifficolta.getSelectedItem();
		
		double diffMedia;
		
		try {
			
			diffMedia = Double.parseDouble(campoDiffMedia.getText().trim());
		
		}
		catch (NumberFormatException e) {
			
			new SwingMessageManager().showMessage("Difficoltà media non è un numero");
			return;
			
		}
		
		if (controller.checkItinerary(dislivelloMax, distanzaMax, diffMax, diffMedia)) {
			
			new SwingMessageManager().showMessage("Tutto OK!");
			
		}
		
		
	}


	private void aggiornaAreaItinerario() {
		
		StringBuilder sb = new StringBuilder("ITINERARIO ATTUALE:\n");
		for (Trail t : controller.getItinerary().getTrails()) {
			
			sb.append(t.toString() + "\n");
			
		}
		
		sb.append("\nDislivello max: " + controller.getItinerary().calcMaxAltitudeDifference() +"\n");
		sb.append("Lunghezza totale: " + controller.getItinerary().calcTotalLength() +"\n");
		sb.append("Difficoltà max: " + controller.getItinerary().calcMaxDifficulty() +"\n");
		sb.append("\nDifficoltà media: " + controller.getItinerary().calcAverageDifficulty() +"\n");
		
		sb.append("==============================\n\n");
		
		areaItinerario.setText(sb.toString());
		
	}


}
