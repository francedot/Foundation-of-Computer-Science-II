package happybank.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class SelezionaBancomatControllaPin extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	
	private JLabel labelBancomat;
	private JLabel labelPin;
	private JComboBox<String> comboBancomat;
	private JPasswordField password;
	private OkCancelButtonPanel pulsantiera;
	
	public SelezionaBancomatControllaPin(Controller controller) {
		
		super();
		this.controller = controller;
		
		this.setLayout(new BorderLayout());
		{
			JPanel north = new JPanel();
			
			{
				north.setLayout(new GridLayout(2, 2));
				
				labelBancomat = new JLabel("Bancomat");
				north.add(labelBancomat);
			
				comboBancomat = new JComboBox<>();
				String[] idTessere = controller.getIdTessereBancomat();
				for (int i = 0; i < idTessere.length; i++) {
				
					comboBancomat.addItem(idTessere[i]);
				
				}
				north.add(comboBancomat);
			
				labelPin = new JLabel("Pin");
				north.add(labelPin);
			
				password = new JPasswordField();
				password.setEditable(true);
				north.add(password);
			}
			this.add(north, BorderLayout.NORTH);
			
			pulsantiera = new OkCancelButtonPanel();
			this.add(pulsantiera, BorderLayout.CENTER);
			pulsantiera.addActionListener(this);
			
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "Ok") {
			
			String idTessera = (String) comboBancomat.getSelectedItem();
			char[] pinC = password.getPassword();
			String pin = String.valueOf(pinC);
			
			controller.checkPin(idTessera, pin);
		}
		
		
	}
	
}
