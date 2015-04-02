package galliacapocciona.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import galliacapocciona.model.Collegio;
import galliacapocciona.model.Partito;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GalliaPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	
	private JLabel titolo;
	private JLabel label1;
	private JLabel label2;
	private JComboBox<String> comboScelta;
	private JTextField tSeggi;
	private GalliaElectionPane gep;
	
	
	public GalliaPanel(Controller controller) {

		this.controller = controller;
		//-------
		this.setLayout(new BorderLayout());
		
		JPanel north = new JPanel();
		{
			
			north.setLayout(new GridLayout(2, 1));
			titolo= new JLabel("Consiglio della Gallia Capocciona");
			north.add(titolo);
			
			JPanel panScelta = new JPanel();
			{
				
				panScelta.setLayout(new BoxLayout(panScelta, BoxLayout.X_AXIS));
				
				label1 = new JLabel("Metodo");
				panScelta.add(label1);
				
				comboScelta = new JComboBox<String>();
				for (String c : controller.getCalcolatoriSeggi())
					comboScelta.addItem(c);
				panScelta.add(comboScelta);
				
				label2 = new JLabel("Seggi:");
				panScelta.add(label2);
				
				tSeggi = new JTextField(String.valueOf(controller.getSeggiMinimi())); 
				//dagli numero minimo seggi -- n.b parsa a string altrimenti capisce che è il numero di colonne!!
				tSeggi.setEditable(true);
				panScelta.add(tSeggi);
				
			}
			
			north.add(panScelta);	
			
		}
		
		this.add(north, BorderLayout.NORTH);
		
		//INIZIALIZZAZIONE GalliaElectionPanel
		List<Collegio> listaCollegi = controller.getListaCollegi();
		List<Partito> listaPartiti = Collegio.generaListaPartiti(listaCollegi);
		List<String> elencoNomiPartiti = new ArrayList<>();
		Map<String, Integer> nomePartitoSeggi = new HashMap<String, Integer>();
		
		for(Partito p : listaPartiti)	
			elencoNomiPartiti.add(p.getNome());

		for (String nomeP: elencoNomiPartiti)
			nomePartitoSeggi.put(nomeP, 0);
		
		gep = new GalliaElectionPane(listaPartiti, nomePartitoSeggi);
		this.add(gep);
	//--------------
		
		tSeggi.addActionListener(this);
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		int numeroSeggi;
		
		try {
			
			numeroSeggi = Integer.parseInt(tSeggi.getText());
			
		}
		catch(NumberFormatException ecc) {
			
			controller.getUserInteractor().showMessage("Il campo seggi deve essere un numero");
			tSeggi.setText(String.valueOf(controller.getSeggiMinimi()));
			return;
			
		}
		
		Map<String, Integer> mappa = controller.calcola((String)comboScelta.getSelectedItem(), numeroSeggi);
		gep.update(mappa);	
		
	}	

	
}
