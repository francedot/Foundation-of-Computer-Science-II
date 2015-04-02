package ed.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ed.model.Bolletta;
import ed.model.Utente;

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;

	private JLabel labelCodideFiscale;

	private JTextField campoCodiceFiscale;

	private JLabel labelCognome;

	private JTextField campoCognome;

	private JLabel labelNome;

	private JTextField campoNome;

	private JLabel labelMese;

	private JTextField campoMese;

	private JLabel labelAnno;

	private JTextField campoAnno;

	private JLabel labelTariffa;

	private JComboBox<String> comboTariffe;

	private JLabel labelConsumo;

	private JTextField campoConsumo;

	private JButton buttonCalcola;

	private JTextArea areaBolletta;
	
	public MainFrame(Controller controller) {

		this.controller = controller;
		
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel west = new JPanel();
		{
			
			west.setLayout(new BoxLayout(west, BoxLayout.PAGE_AXIS));
			
			labelCodideFiscale = new JLabel("Codice Fiscale");
			west.add(labelCodideFiscale);
			
			campoCodiceFiscale = new JTextField(20);
			west.add(campoCodiceFiscale);
			
			labelCognome = new JLabel("Cognome");
			west.add(labelCognome);
			
			campoCognome = new JTextField(20);
			west.add(campoCognome);
			
			labelNome = new JLabel("Nome");
			west.add(labelNome);
			
			campoNome = new JTextField(20);
			west.add(campoNome);
			
			labelMese = new JLabel("Mese");
			west.add(labelMese);
			
			campoMese = new JTextField(20);
			west.add(campoMese);
			
			labelAnno = new JLabel("Anno");
			west.add(labelAnno);
			
			campoAnno = new JTextField(20);
			west.add(campoAnno);
			
			labelTariffa = new JLabel("Tariffa");
			west.add(labelTariffa);
			
			comboTariffe = new JComboBox<String>();
			for (String nomeTar : controller.getNomiTariffe()) {
				
				comboTariffe.addItem(nomeTar);
				
			}
			west.add(comboTariffe);
			
			labelConsumo = new JLabel("Consumo (KWh)");
			west.add(labelConsumo);
			
			campoConsumo = new JTextField(20);
			west.add(campoConsumo);
			
			buttonCalcola = new JButton("Calcola");
			buttonCalcola.addActionListener(this);
			west.add(buttonCalcola);
			
		}
		this.getContentPane().add(west, BorderLayout.LINE_START);
		
		areaBolletta = new JTextArea(100, 100);
		areaBolletta.setEditable(false);
		this.getContentPane().add(areaBolletta, BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String nomeTariffa = (String) comboTariffe.getSelectedItem();
		
		Utente utente = new Utente(campoCodiceFiscale.getText().trim(),
											campoCognome.getText().trim(), 
													campoNome.getText().trim());
		
		
		int mese = Integer.parseInt(campoMese.getText().trim());
		int anno = Integer.parseInt(campoAnno.getText().trim());
		
		double consumo = Double.parseDouble(campoConsumo.getText().trim());
		
		Bolletta bolletta = controller.creaBolletta(nomeTariffa, utente, mese, anno, consumo);
		
		areaBolletta.setText(bolletta.toString());
		
	}

	

	
	
}
