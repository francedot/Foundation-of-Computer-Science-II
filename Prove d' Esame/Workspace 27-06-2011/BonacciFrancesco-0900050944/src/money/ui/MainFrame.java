package money.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import money.model.Bilancio;

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Controller controller;

	private JButton buttonVisualizzaMov;

	private JButton buttonSalva;

	private SummaryPanel summaryPanel1;

	private JComboBox<String> comboConti;

	private SummaryPanel summaryPanel2;

	private MyMovimentiDialog myMovimentiDialog;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == buttonVisualizzaMov) {
			
			clickOnMovimenti();
			
		} else if (arg0.getSource() == buttonSalva) {
			
			clickOnSave();
			
		} else if (arg0.getSource() == comboConti) {
			
			changeOnComboConti();
			
		}
		
		
	}
	
	private void changeOnComboConti() {

		String nome = (String) comboConti.getSelectedItem();
		Bilancio conto = controller.getConto(nome);
		summaryPanel2.setBilancio(conto);
		
	}

	private void clickOnSave() {
		
		controller.save();
		
	}

	private void clickOnMovimenti() {

		controller.openMovimenti();
		summaryPanel1.refreshData();
		summaryPanel2.refreshData();
		
	}

	protected void initGUI() {
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JPanel upperBottoms = new JPanel();
		{
			
			upperBottoms.setLayout(new GridLayout(1, 2));
			
			buttonVisualizzaMov = new JButton("Visualizza Movimenti");
			upperBottoms.add(buttonVisualizzaMov);
			buttonVisualizzaMov.addActionListener(this);
			
			buttonSalva = new JButton("Salva");
			upperBottoms.add(buttonSalva);
			buttonSalva.addActionListener(this);
			
		}
		this.getContentPane().add(upperBottoms);
		
		summaryPanel1 = new SummaryPanel();
		this.getContentPane().add(summaryPanel1);
		
		comboConti = new JComboBox<String>();
		this.getContentPane().add(comboConti);
		
		summaryPanel2 = new SummaryPanel();
		this.getContentPane().add(summaryPanel2);
		
		
	}
	public MainFrame() {
		
		super();
		initGUI();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
		
	}
	
	
	public void setController(Controller controller) {
		
		this.controller = controller;
		
		for (String s : controller.getConti()) {
			
			comboConti.addItem(s);
			
		}
		
		summaryPanel1.setBilancio(controller.getBilancioFamiliare());
		
	}
	
}
