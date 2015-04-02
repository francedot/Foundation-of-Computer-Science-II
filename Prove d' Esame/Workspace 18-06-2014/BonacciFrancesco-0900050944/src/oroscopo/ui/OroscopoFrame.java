package oroscopo.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import oroscopo.controller.AbstractController;
import oroscopo.model.Oroscopo;
import oroscopo.model.SegnoZodiacale;

public class OroscopoFrame extends JFrame implements ActionListener {

	private AbstractController controller;
	private int fortunaMin;
	
	private JComboBox<SegnoZodiacale> comboSegni;
	private JTextArea areaOroscopo;
	private JButton buttonStampa;
	
	private static final long serialVersionUID = 1L;

	public OroscopoFrame(AbstractController controller, int fortunaMin) {
		
		super();
		
		this.controller = controller;
		this.fortunaMin = fortunaMin;
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		JPanel upperPanel = new JPanel();
		{
			upperPanel.setLayout(new GridLayout(1, 2));
			
			upperPanel.add(new JLabel("Segno Zodiacale"));
			
			comboSegni = new JComboBox<SegnoZodiacale>();
			for (SegnoZodiacale s : controller.getSegni()) {
			
				comboSegni.addItem(s);
			
			}
			comboSegni.addActionListener(this);
			upperPanel.add(comboSegni);
			
			
		}
		this.getContentPane().add(upperPanel);
		
		this.getContentPane().add(new JLabel("Oroscopo Mensilie"));
		
		areaOroscopo = new JTextArea(100, 100);
		this.getContentPane().add(areaOroscopo);
		
		buttonStampa = new JButton("Stampa annuale");
		buttonStampa.addActionListener(this);
		this.getContentPane().add(buttonStampa);
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == buttonStampa) {
			
			stampaAnnuale();
			
		} else if (event.getSource() == comboSegni) {
			
			aggiornaAreaTesto();
			
		}
		
	}

	private void aggiornaAreaTesto() {
		
		Oroscopo oroscopo = controller.generaOroscopoCasuale((SegnoZodiacale) comboSegni.getSelectedItem());
		
		areaOroscopo.setText(oroscopo.toString());
		
	}

	private void stampaAnnuale() {

		Oroscopo[] oroscopo = controller.generaOroscopoAnnuale((SegnoZodiacale) comboSegni.getSelectedItem(), fortunaMin);
		
		FileWriter fileWriter = null;
		
		try {
			
			fileWriter = new FileWriter(new File("OroscopoAnnuale.txt"), true);
			
			
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(this, "Errore I/O su file di testo" + e);
			
		}
		
		PrintWriter pw = new PrintWriter(fileWriter, true);
		
		for (int i = 0; i < 12; i++) {
			
			pw.println(oroscopo.toString());
			
		}
		
	}

	
	
}
