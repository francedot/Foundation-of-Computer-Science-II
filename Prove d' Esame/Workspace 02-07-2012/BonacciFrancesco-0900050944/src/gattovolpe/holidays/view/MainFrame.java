package gattovolpe.holidays.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import gattovolpe.holidays.controller.Controller;
import gattovolpe.holidays.controller.CostoComparator;
import gattovolpe.holidays.controller.DurataComparator;
import gattovolpe.holidays.controller.InizioComparator;
import gattovolpe.holidays.model.Destinazione;
import gattovolpe.holidays.model.Pacchetto;
import gattovolpe.holidays.model.TipoPacchetto;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame implements ActionListener, ChangeListener {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;	/** ricorda sempre di agganciare il controller alla view altrimenti lavori col nulla!
									  * a tutti gli effetti ---> vedi NullPointerException !!! */
	
	private JComboBox<String> comboStati;
	private JTextField campoPrezzo;
	private JSpinner spinnerInizio;
	private JSpinner spinnerFine;
	private JComboBox<TipoPacchetto> comboTipo;
	private JLabel labelTipo;
	private JRadioButton radioCosto;
	private JLabel labelBassoCosto;
	private JLabel labelInizioVicino;
	private JLabel labelDurataSimile;
	private JRadioButton radioInizio;
	private JRadioButton radioDurata;
	private JTextField campoDurataSimile;
	private JTextArea areaPacchetti;

	private ButtonGroup bg;
	
	public MainFrame(Controller controller) {

		this.controller = controller;
		
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel north = new JPanel();
		{
			
			north.setLayout(new BoxLayout(north, BoxLayout.LINE_AXIS));
			
			comboStati = new JComboBox<String>();
			ArrayList<String> nomiStati = new ArrayList<String>();
			for (Destinazione d : controller.getDestinazioni()) {
				
				if (!nomiStati.contains(d.getStato()))
					nomiStati.add(d.getStato());
				
			}
			for (String s : nomiStati)
				comboStati.addItem(s);
			
			north.add(comboStati);
			comboStati.addActionListener(this);
			
			campoPrezzo = new JTextField("1000");
			campoPrezzo.addActionListener(this);
			north.add(campoPrezzo);
			
			spinnerInizio = new JSpinner( new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH) );
			spinnerInizio.setEditor( new JSpinner.DateEditor(spinnerInizio, "dd/MM/yyyy") );
			spinnerInizio.addChangeListener(this);
			north.add(spinnerInizio);
			
			spinnerFine = new JSpinner( new SpinnerDateModel( new Date(), null, null, Calendar.DAY_OF_MONTH) );
			spinnerFine.setEditor( new JSpinner.DateEditor(spinnerFine, "dd/MM/yyyy") );
			spinnerFine.addChangeListener(this);
			north.add(spinnerFine);
			
		}
		this.add(north, BorderLayout.PAGE_START);
		
		JPanel west = new JPanel();
		{
			
			west.setLayout(new GridLayout(5, 2));
			
			labelTipo = new JLabel("Tipo");
			west.add(labelTipo);
			
			comboTipo = new JComboBox<TipoPacchetto>();
			for (TipoPacchetto t : TipoPacchetto.values()) {
				
				comboTipo.addItem(t);
				
			}
			comboTipo.addActionListener(this);
			west.add(comboTipo);
			
			labelBassoCosto = new JLabel("Basso Costo");
			west.add(labelBassoCosto);
			
			radioCosto = new JRadioButton();
			radioCosto.setSelected(true);
			radioCosto.addActionListener(this);
			west.add(radioCosto);
			
			labelInizioVicino = new JLabel("Inizio Vicino");
			west.add(labelInizioVicino);
			
			radioInizio = new JRadioButton();
			radioInizio.addActionListener(this);
			west.add(radioInizio);
			
			labelDurataSimile = new JLabel("Durata simile");
			west.add(labelDurataSimile);
			
			radioDurata = new JRadioButton();
			radioDurata.addActionListener(this);
			west.add(radioDurata);
			
			campoDurataSimile = new JTextField(5);
			campoDurataSimile.addActionListener(this);
			west.add(campoDurataSimile);
			
			bg = new ButtonGroup();
			bg.add(radioCosto);
			bg.add(radioInizio);
			bg.add(radioDurata);
				
		}
		this.getContentPane().add(west, BorderLayout.LINE_START);
		
		areaPacchetti = new JTextArea(100, 100);
		areaPacchetti.setEditable(false);
		this.getContentPane().add(new JScrollPane(areaPacchetti), BorderLayout.CENTER);
		
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		aggiornaAreaPacchetti();
		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {

		aggiornaAreaPacchetti();
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		aggiornaAreaPacchetti();
		
	}

	private void aggiornaAreaPacchetti() {
		
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		
		Date inizio = (Date) spinnerInizio.getValue();
		Date fine = (Date) spinnerFine.getValue();
	
		Comparator<Pacchetto> criterio = null;
		
		if (radioCosto.isSelected())
			criterio = new CostoComparator();
		else if (radioInizio.isSelected())
			criterio = new InizioComparator(inizio);
		else if (radioDurata.isSelected()) {
			
			try {
				
				criterio = new DurataComparator(Integer.parseInt(campoDurataSimile.getText()));
				
			} catch (NumberFormatException e) {
				
				return;
				
			}
			
			
		}
		TipoPacchetto tipo = (TipoPacchetto) comboTipo.getSelectedItem();
		
		String stato = (String) comboStati.getSelectedItem();
		
		double prezzo = Double.parseDouble(campoPrezzo.getText());
		
		List<Pacchetto> pacchetti = controller.evalPackage(criterio, tipo, stato, prezzo, inizio, fine);
		
		StringBuilder sb = new StringBuilder();
		
		for (Pacchetto p : pacchetti) {
			
			sb.append(p.getNome() + "; ");
			sb.append("Dest. :" + p.getDestinazione().getLuogo() + "; ");
			sb.append("Costo: " + p.getCosto() + "; ");
			sb.append("Inizio: " + df.format(p.getDataInizio()) + "; ");
			sb.append("Durata: " + p.getDurataGiorni() + " giorni\n");
			
		}
		
		areaPacchetti.setText(sb.toString());
		
	}
	
}
