package dentorestaurant.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Menu;
import dentorestaurant.model.Ordine;
import dentorestaurant.model.Portata;

public class RestaurantPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Ordine ordine;
	
	private Controller controller;
	private String nomeCliente;
	private JComboBox<Menu> comboMenu;
	private JComboBox<Portata> comboPortateA;
	private JComboBox<Portata> comboPortateP;
	private JComboBox<Portata> comboPortateS;
	private JComboBox<Portata> comboPortateD;
	private JTextField campoPrezzo;
	
	public RestaurantPanel(Controller controller, String nomeCliente) {
		
		this.controller = controller;
		this.nomeCliente = nomeCliente;
		
		initGUI();
		
	}

	private void initGUI() {

		this.setLayout(new BorderLayout());
		
		JPanel upperPanel = new JPanel();//
		{
			
			upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.LINE_AXIS));
			
			upperPanel.add(new JLabel("Menu disponibili"));
			
			comboMenu = new JComboBox<Menu>();
			for (Menu menu : controller.getMenus()) {
				
				comboMenu.addItem(menu);
				
			}
			comboMenu.addActionListener(this);
			upperPanel.add(comboMenu);
			
		}
		this.add(upperPanel, BorderLayout.PAGE_START);
		
		JPanel leftPanel = new JPanel();
		{
			leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
			{
				
				leftPanel.add(new JLabel("ANTIPASTO"));
				comboPortateA = new JComboBox<Portata>();
				leftPanel.add(comboPortateA);
				
				leftPanel.add(new JLabel("PRIMO"));
				comboPortateP = new JComboBox<Portata>();
				leftPanel.add(comboPortateP);
				
				leftPanel.add(new JLabel("SECONDO"));
				comboPortateS = new JComboBox<Portata>();
				leftPanel.add(comboPortateS);
				
				leftPanel.add(new JLabel("DESSERT"));
				comboPortateD = new JComboBox<Portata>();
				leftPanel.add(comboPortateD);
				
			}
			
		}
		this.add(leftPanel, BorderLayout.LINE_START);
		
		JPanel rightPanel = new JPanel();
		{
			
			rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.LINE_AXIS));
			
			rightPanel.add(new JLabel("Prezzo totale"));
			
			campoPrezzo = new JTextField("€ 0.0", 10);
			campoPrezzo.setBackground(Color.WHITE);
			campoPrezzo.setEditable(false);
			rightPanel.add(campoPrezzo);
			
		}
		this.add(rightPanel, BorderLayout.LINE_END);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		if (event.getSource() == comboMenu 
				|| event.getSource() == comboPortateA
					||event.getSource() == comboPortateD
						|| event.getSource() == comboPortateP
							|| event.getSource() == comboPortateS) {
		
			changeOnComboMenu();

		}
	}

	private void changeOnComboMenu() {
		
		clearComboPortate();
		
		Menu menuSelezionato = (Menu) comboMenu.getSelectedItem();
		
		ordine = controller.creaOrdine(menuSelezionato, nomeCliente);
		
		List<Portata> portatePerA = menuSelezionato.getPortate(Categoria.valueOf("ANTIPASTO"));
		List<Portata> portatePerP = menuSelezionato.getPortate(Categoria.valueOf("PRIMO"));
		List<Portata> portatePerS = menuSelezionato.getPortate(Categoria.valueOf("SECONDO"));
		List<Portata> portatePerD = menuSelezionato.getPortate(Categoria.valueOf("DESSERT"));
		
		for (Portata p : portatePerA) {
			
			comboPortateA.addItem(p);
			
		}
		
		for (Portata p : portatePerP) {
			
			comboPortateP.addItem(p);
			
		}
		
		for (Portata p : portatePerS) {
			
			comboPortateS.addItem(p);
			
		}
		
		for (Portata p : portatePerD) {
			
			comboPortateD.addItem(p);
			
		}

		ordine = controller.creaOrdine(menuSelezionato, nomeCliente);
		controller.sostituisciPortata(ordine, (Portata) comboPortateA.getSelectedItem());
		controller.sostituisciPortata(ordine, (Portata) comboPortateP.getSelectedItem());
		controller.sostituisciPortata(ordine, (Portata) comboPortateS.getSelectedItem());
		controller.sostituisciPortata(ordine, (Portata) comboPortateD.getSelectedItem());
		
		double prezzo = ordine.getPrezzoTotale();
		
		campoPrezzo.setText("€ " + prezzo);
		
	}

	private void clearComboPortate() {

		comboPortateA.removeAllItems();
		comboPortateP.removeAllItems();
		comboPortateS.removeAllItems();
		comboPortateD.removeAllItems();
		
	}
	
}
