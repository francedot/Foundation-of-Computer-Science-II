package zannotaxi.ui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import zannotaxi.model.CorsaTaxi;

public class MainFrame extends JFrame implements ListSelectionListener {

	private static final long serialVersionUID = 1L;
	
	private JList<String> jLCorse;
	private JCorsaTaxiPane jcp;
	
	private Controller controller;

	public MainFrame(Controller controller) {

		super("ZannoTaxi");
		
		this.controller = controller;
		
		initGUI();
		bindData();
		
		jLCorse.addListSelectionListener(this);
		
		//op. logiche -- dopo aver gestito inigui, binddata e gli eventi -- pack, op. di chiusura, rendi visibile il frame
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}

	private void bindData() {
		
		//prendere tutte le corse taxi
		//prendere i nomi delle corse dalle corsetaxi -- met controller
		//metodo apposito del controller getdescrizionicorse
		
		jLCorse.setListData(controller.getDescrizioniCorse());
		
	}
	
	
	private void initGUI() {
		
		this.getContentPane().setLayout(new GridLayout(2, 1));
		
		JPanel NPanel = new JPanel();
		{
			NPanel.setBackground(Color.blue);
			NPanel.setLayout(new GridLayout(1, 2));
			
			JTextArea jta = new JTextArea("Corsa taxi, simulazione costi"
					+ "\n\n\nSeleziona corsa:");
			NPanel.add(jta);
			jta.setBackground(Color.DARK_GRAY);
			jta.setForeground(Color.WHITE);
			
			jLCorse = new JList<String>();
			JScrollPane pane = new JScrollPane(jLCorse);
			jLCorse.setVisibleRowCount(4);
			jLCorse.setBackground(Color.DARK_GRAY);
			jLCorse.setForeground(Color.WHITE);
			NPanel.add(pane);
		}
		this.getContentPane().add(NPanel);
		
		jcp = new JCorsaTaxiPane();
		this.getContentPane().add(jcp);
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		String descrizioneCorsa = jLCorse.getSelectedValue();
		CorsaTaxi corsaCorrente = controller.getCorsaPerDescrizione(descrizioneCorsa);
	
		jcp.update(controller.getLineeDiCosto(corsaCorrente));
		
	
	}
}
