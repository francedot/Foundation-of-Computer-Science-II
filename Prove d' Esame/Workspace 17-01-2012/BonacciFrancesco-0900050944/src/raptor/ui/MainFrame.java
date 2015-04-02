package raptor.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import raptor.model.Section;
import raptor.model.SpeedingTicket;
import raptor.model.Transit;

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;

	private JTextArea campoSezioni;

	private JTextArea campoTransiti;

	private JButton buttonElabora;

	private JTextArea campoViolazioni;
	
	public MainFrame(Controller controller) {

		this.controller = controller;

		initGUI();
		
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	
	}

	private void initGUI() {

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JLabel labelTop = new JLabel("Elenco sezioni controllate");
		this.getContentPane().add(labelTop);
		
		campoSezioni = new JTextArea(40, 40);
		campoSezioni.setEditable(false);
		this.getContentPane().add(campoSezioni);
		popolaCampoSezioni();
		
		JLabel labelCenter = new JLabel("Elenco transiti registrati");
		this.getContentPane().add(labelCenter);
		
		campoTransiti = new JTextArea(40, 40);
		campoTransiti.setEditable(false);
		this.getContentPane().add(campoTransiti);
		popolaCampoTransiti();
		
		buttonElabora = new JButton("Elabora Prossimo Transito");
		this.getContentPane().add(buttonElabora);
		buttonElabora.addActionListener(this);
		
		campoViolazioni = new JTextArea(40, 40);
		campoViolazioni.setEditable(false);
		this.getContentPane().add(campoViolazioni);
		
	}

	private void popolaCampoTransiti() {

		StringBuilder sb = new StringBuilder();
		
		for (Transit t : controller.getAvailableTransits()) {
			
			sb.append(t);
			sb.append(System.getProperty("line.separator"));
			
		}
		
		campoTransiti.setText(sb.toString());
		
		
	}

	private void popolaCampoSezioni() {

		StringBuilder sb = new StringBuilder();
		
		for (Section s : controller.getSections()) {
			
			sb.append(s);
			
		}
		
		campoSezioni.setText(sb.toString());
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		controller.putTransitInSystem();
		popolaCampoSezioni();
		popolaCampoTransiti();
		popolaCampoViolazioni();
		
		
	}

	private void popolaCampoViolazioni() {

		StringBuilder sb = new StringBuilder();
		
		for (SpeedingTicket s : controller.getSpeedingTickets()) {
			
			sb.append(s);
			
		}
		
		campoViolazioni.setText(sb.toString());

	}

}
