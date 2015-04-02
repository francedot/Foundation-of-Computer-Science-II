package mm.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mm.model.Codice;
import mm.model.CodiceRisposta;

public class MainFrame extends JFrame implements MainView, ActionListener {

	private static final long serialVersionUID = 1L;

	private AbstractController controller;
	
	private JButton bCarica;
	private JButton bSalva;
	private JButton bSalvaPartita;
	private JButton bNuovaPartita;
	
	private JScrollPane sp;
	private PartitaPanel pp;
	private CodicePanel cp;
	
	public MainFrame(AbstractController controller) {
		
		this.controller = controller;
		
		initGUI();
		
		//se settavi la view del controller prima setmainview invocava
		//il reset di questa view che invoca il reset di pp che non è ancora stato creato!
		controller.setMainView(this);
		
		bCarica.addActionListener(this);
		bSalva.addActionListener(this);
		bSalvaPartita.addActionListener(this);
		bNuovaPartita.addActionListener(this);
		
	}

	private void initGUI() {
		
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel west = new JPanel();
		{
			
			west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
			
			bCarica = new JButton("Carica");
			west.add(bCarica);
			
			bSalva = new JButton("Salva");
			west.add(bSalva);
			
			bSalvaPartita = new JButton("Salva Partita");
			bSalvaPartita.setEnabled(false);
			west.add(bSalvaPartita);	//N.B PANNELLO.ADD(COMPONENTE)   !!!!!
			
			bNuovaPartita = new JButton("Nuova Partita");
			west.add(bNuovaPartita);
			
		}
		this.getContentPane().add(west, BorderLayout.EAST);
		
		pp = new PartitaPanel(controller);
		sp = new JScrollPane(pp);
		this.getContentPane().add(sp, BorderLayout.CENTER);
		
		cp = new CodicePanel();
		this.getContentPane().add(cp, BorderLayout.SOUTH);
		
		bCarica.addActionListener(this);
		bSalva.addActionListener(this);
		bSalvaPartita.addActionListener(this);
		bNuovaPartita.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	}
	
	@Override
	public void reset() {
		
		cp.setCodice(null);
		pp.reset();
		
	}

	@Override
	public void addCodiceRisposta(CodiceRisposta codiceRisposta) {
	
		pp.addCodiceRisposta(codiceRisposta);
		updateButtonsStatus();
	}

	@Override
	public void showCodiceSegreto(Codice segreto) {

		cp.setCodice(segreto);
		
	}

	@Override
	public void showMessage(String msg) {

		JOptionPane.showMessageDialog(this, msg);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == bNuovaPartita)
			controller.nuovaPartita();
	
		if (e.getSource() == bSalva)
			controller.salva();
		
		if (e.getSource() == bSalvaPartita)
			controller.salvaPartita();

		if (e.getSource() == bCarica)
			controller.carica();
		
		updateButtonsStatus();
		
	}

	private void updateButtonsStatus()
	{
		boolean partitaChiusa = controller.isPartitaChiusa();
		bSalva.setEnabled(!partitaChiusa);
		bSalvaPartita.setEnabled(partitaChiusa);
	}
	
}
