package zannonia.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import zannonia.model.Autostrada;
import zannonia.model.Casello;
import zannonia.model.Tratta;
import zannonia.model.routing.Percorso;

public class MainFrame extends JFrame implements MainView, ActionListener {

	private static final long serialVersionUID = 1L;

	private JLabel labelP;
	private JComboBox<Autostrada> comboAutostradeEntrata;
	private JComboBox<Casello> comboCaselliEntrata;
	
	private JLabel labelA;
	private JComboBox<Autostrada> comboAutostradeUscita;
	private JComboBox<Casello> comboCaselliUscita;
	
	private JButton buttonCalcola;
	
	private JPanel contenitoreSol;
	
	private MainController mainController;
	
	public MainFrame() {
		
		super();
		
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel west = new JPanel();
		{
			west.setLayout(new BoxLayout(west, BoxLayout.PAGE_AXIS));
			
			labelP = new JLabel("Partenza");
			west.add(labelP);
			
			comboAutostradeEntrata = new JComboBox<Autostrada>();
			west.add(comboAutostradeEntrata);
			comboAutostradeEntrata.addActionListener(this);
			
			comboCaselliEntrata = new JComboBox<Casello>();
			west.add(comboCaselliEntrata);
			
			labelA = new JLabel("Arrivo");
			west.add(labelA);
			
			comboAutostradeUscita = new JComboBox<Autostrada>();
			west.add(comboAutostradeUscita);
			comboAutostradeUscita.addActionListener(this);
			
			comboCaselliUscita = new JComboBox<Casello>();
			west.add(comboCaselliUscita);
			
			buttonCalcola = new JButton("Calcola Percorso");
			west.add(buttonCalcola);
			buttonCalcola.addActionListener(this);
			
		}
		this.getContentPane().add(west, BorderLayout.WEST);
	
		contenitoreSol = new JPanel();
		contenitoreSol.setLayout(new BoxLayout(contenitoreSol, BoxLayout.Y_AXIS));
		JScrollPane sp = new JScrollPane(contenitoreSol);
		
		this.getContentPane().add(sp, BorderLayout.EAST);
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}

	public void setController(MainController mainController) {
		
		this.mainController = mainController;
		
	}

	public void setModel(List<Autostrada> autostrade) {
		
		for (Autostrada a : autostrade) {
			
			comboAutostradeEntrata.addItem(a);
			comboAutostradeUscita.addItem(a);
			
		}
		
	}
	
	public void mostraPercorsi(List<Percorso> percorsi) {
		
		contenitoreSol.removeAll();
		
		int cont = 1;
		
		for (Percorso p : percorsi) {
			
			SolutionPanel sp = new SolutionPanel(p, "Soluzione " + cont);
			contenitoreSol.add(sp);
			cont++;
		}
		
		this.repaint();
		this.validate();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == comboAutostradeEntrata) {
			
			comboCaselliEntrata.removeAllItems();
			
			Autostrada autoCorrente = (Autostrada) comboAutostradeEntrata.getSelectedItem();
			
			ArrayList<Casello> caselli = new ArrayList<>();
			
			for(Tratta t : autoCorrente.getTratte()) {
				
				for (Casello c: t.getCaselli()) {
					
					caselli.add(c);
					
				}
			}
			
			for (Casello c : caselli) {
				
				comboCaselliEntrata.addItem(c);
				
			}
			
		}
		
		if (e.getSource() == comboAutostradeUscita) {
			
			comboCaselliUscita.removeAllItems();
			
			Autostrada autoCorrente = (Autostrada) comboAutostradeUscita.getSelectedItem();
			
			ArrayList<Casello> caselli = new ArrayList<>();
			
			for(Tratta t : autoCorrente.getTratte()) {
				
				for (Casello c: t.getCaselli()) {
					
					caselli.add(c);
					
				}
			}
			
			for (Casello c : caselli) {
				
				comboCaselliUscita.addItem(c);
				
			}
			
		}
		
		if (e.getSource() == buttonCalcola) {
			
			Casello dep = (Casello) comboCaselliEntrata.getSelectedItem();
			Casello arr = (Casello) comboCaselliUscita.getSelectedItem();
			
			if (dep != null && arr != null) {
				mainController.calcolaPercorsi(this, dep, arr);
			}
		}
		
		
		
		
	}

}
