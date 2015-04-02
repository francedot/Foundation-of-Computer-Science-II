package ztl.ui;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import ztl.model.Ticket;
import ztl.model.Transit;

public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;

	private JList<Transit> listaTransitiRegistrati;

	private JButton buttonElabora;

	private JList<Ticket> listaTicketEmessi;

	private Label labelSouth;

	private DefaultListModel<Transit> model1;

	private DefaultListModel<Ticket> model2;
	
	public MainFrame(Controller controller) {
		
		this.controller = controller;
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		Label labelNorth = new Label("Elenco transiti registrati");
		add(labelNorth);
		
		model1 = new DefaultListModel<Transit>();
		for (Transit t : controller.getAllTransits()) {
			
			model1.addElement(t);
			
		}
		listaTransitiRegistrati = new JList<Transit>(model1);
		JScrollPane pane1 = new JScrollPane(listaTransitiRegistrati);
		add(pane1);
		
		buttonElabora = new JButton("Elabora Prossimo Transito");
		buttonElabora.addActionListener(this);
		add(buttonElabora);
		
		model2 = new DefaultListModel<Ticket>();
		listaTicketEmessi = new JList<Ticket>(model2);
		JScrollPane pane2 = new JScrollPane(listaTicketEmessi);
		add(pane2);
		
		labelSouth = new Label("Totale incassato finora: € 0.0");
		add(labelSouth);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Transit rimosso = null;
		
		int posizioneInLista = listaTransitiRegistrati.getSelectedIndex();
		
		if (!model1.isEmpty()) {
			
			rimosso = (Transit) model1.get(posizioneInLista);
			model1.remove(posizioneInLista);
			if (model1.size() == 0)	buttonElabora.setEnabled(false);
			
		} else {
			
			return;
			
		}
		
		if (rimosso == null)
			return;
		
		Ticket ticketCorrente = controller.manageTransit(rimosso);
		
		model2.addElement(ticketCorrente);
		
		labelSouth.setText("Totale incassato finora: € " + controller.getTotalAmount());
		
	}

}
