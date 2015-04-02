package happybank.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SelezionaImporto extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	
	private JLabel labelImporto;
	private JTextField fieldImporto;
	
	private OkCancelButtonPanel pulsantiera;
	
	public SelezionaImporto(Controller controller) {
		super();

		this.controller = controller;
		
		this.setLayout(new BorderLayout());
		{
			JPanel north = new JPanel();
			{
				north.setLayout(new GridLayout(2, 1));
				
				labelImporto = new JLabel("Importo");
				north.add(labelImporto);
				
				fieldImporto = new JTextField(10);
				fieldImporto.setEditable(true);
				north.add(fieldImporto);
				
				
			}
			this.add(north, BorderLayout.NORTH);
			
			pulsantiera = new OkCancelButtonPanel();
			this.add(pulsantiera, BorderLayout.CENTER);
			pulsantiera.addActionListener(this);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case "Ok":{
				
				String campo = fieldImporto.getText();
				
				try {
					
					int imp = Integer.parseInt(campo);
					controller.preleva(imp);
					
				} catch (NumberFormatException exc) {
					
					controller.showMessage("Inserire valore numerico");
				}
			}
				break;
			default:
				controller.inizio();
				break;				
		}
	}
	
	
}
