package money.ui;




import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * Implementazione di vista modale con pulsanti Ok e Cancel
 * <p>
 * <b>Esempio:</b>
 * <p>
 * <code><pre>
 * public class Person{
 *    private String firstName;
 *    
 *    public Person(String firstName)
 *    {
 *       this.firstName = firstName;
 *    }		
 * 
 *    public String getFirstName()
 *    {
 *       return firstName;
 *    }
 *    
 *    public void setFirstName(String firstName)
 *    {
 *       this.firstName = firstName;
 *    }
 * }
 * 
 * public interface MyView extends ModalView<Person>
 * {
 * }
 * 
 * public class MyFrame extends FormDialog<Person> implements MyView
 * {
 *    public void initGUI()
 *    {
 *       setTitle("Inserisci/Modifica Persona");
 *       getContentPanel().setLayout(new FlowLayout());
 *       JTextField field = new JTextField();
 *       getContentPanel().add(field);
 *    }
 * 
 *    public void setModel(Person model)
 *    {
 *       field.setText(model.getFirstName());
 *    }
 * 
 *    public void updateModel(Person model)
 *    {
 *       model.setFirstName(field.getText());
 *    }
 * }
 * </pre></code>
 * 
 * @author Fondamenti di Informatica T-2
 * @version May 21, 2010 8:39:12 PM
 * @param <M>
 */
public abstract class FormDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel;
	private JPanel buttonContainer;
	private JPanel contentPanel;
	private JButton cancelButton;
	private JButton okButton;
	private DialogResult result;

	protected void setModalResult(DialogResult result)
	{
		this.result = result;
	}

	protected DialogResult getModalResult()
	{
		return this.result;
	}

	public DialogResult showDialog()
	{
		setModal(true);
		setVisible(true);
		return getModalResult();
	}
	
	/**
	 * Centra la vista nel centro dello schermo
	 */
	protected void Center(){
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    
	    // Determine the new location of the window
	    int w = getSize().width;
	    int h = getSize().height;
	    int x = (dim.width-w)/2;
	    int y = (dim.height-h)/2;
	    
	    // Move the window
	    setLocation(x, y);
	}

	/**
	 * Imposta il titolo della vista
	 */
	public void setTitle(String title)
	{
		super.setTitle(title);
		titleLabel.setText(title);
	}

	/**
	 * Ritorna il pannello principale a cui aggiungere tutti i controlli.
	 * 
	 * @return
	 */
	protected JPanel getContentPanel()
	{
		return contentPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source == okButton)
			OnOkClick();
		if (source == cancelButton)
			OnCancelClick();
	}

	public FormDialog()
	{
		super();
		preinitGUI();
		initGUI();
		pack();
	}

	private void preinitGUI()
	{
		try
		{
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setResizable(false);
			{
				titleLabel = new JLabel();
				getContentPane().add(titleLabel, BorderLayout.NORTH);
				titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));
				titleLabel.setPreferredSize(new java.awt.Dimension(434, 62));
				titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
				titleLabel.setText("TITOLO");
			}
			{
				buttonContainer = new JPanel();
				FlowLayout buttonContainerLayout = new FlowLayout();
				buttonContainerLayout.setAlignment(FlowLayout.RIGHT);
				buttonContainer.setLayout(buttonContainerLayout);
				getContentPane().add(buttonContainer, BorderLayout.SOUTH);
				{
					cancelButton = new JButton();
					buttonContainer.add(cancelButton);
					cancelButton.setText("Annulla");
					cancelButton.addActionListener(this);
				}
				{
					okButton = new JButton();
					buttonContainer.add(okButton);
					okButton.setText("Ok");
					okButton.addActionListener(this);
				}
			}
			{
				contentPanel = new JPanel();
				GridLayout contentPanelLayout = new GridLayout(1, 1);
				contentPanelLayout.setColumns(1);
				contentPanelLayout.setHgap(5);
				contentPanelLayout.setVgap(5);
				contentPanel.setLayout(contentPanelLayout);
				getContentPane().add(contentPanel, BorderLayout.CENTER);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void setCancelButtonVisible(boolean visible)
	{
		cancelButton.setVisible(visible);
	}

	/**
	 * Metodo astratto da implementare per inserire tutti i controlli. Il pack
	 * non è necessario in quanto richiamato automaticamente dal costruttore
	 */
	protected abstract void initGUI();

	/** 
	 * E' eseguito alla pressione del pulsante cancel
	 */
	protected void OnCancelClick()
	{
		setModalResult(DialogResult.Cancel);
		Close();
	}

	/**
	 * E' eseguito alla pressione del pulsante ok
	 */
	protected void OnOkClick()
	{
		setModalResult(DialogResult.Ok);
		Close();
	}

	/**
	 * Chiude la finestra e gestisce EXIT_ON_CLOSE e DISPOSE_ON_CLOSE
	 */
	protected void Close()
	{
		this.setVisible(false);
		if (getDefaultCloseOperation() == WindowConstants.EXIT_ON_CLOSE)
		{
			System.exit(0);
		}
		if (getDefaultCloseOperation() == WindowConstants.DISPOSE_ON_CLOSE)
		{
			dispose();
		}
	}
}

