package money.ui;

import money.model.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MyMovimentiDialog extends FormDialog implements ActionListener, ChangeListener
{
	private static final long serialVersionUID = 1L;
	private JButton insertButton;
	private JPanel buttonPanel;
	private JPanel datePanel;
	private MovimentoController controller;
	private JBindableTable<Movimento> table;
	private MyDateSpinner inizio;
	private MyDateSpinner fine;

	public MyMovimentiDialog()
	{
		super();
	}

	@Override
	protected void initGUI()
	{
		try
		{
			setPreferredSize(new Dimension(800, 400));
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				table = new JBindableTable<Movimento>(Movimento.class);
				getContentPane().add(table, BorderLayout.CENTER);
			}

			{
				datePanel = new JPanel();
				getContentPane().add(datePanel, BorderLayout.NORTH);
				datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.PAGE_AXIS));
				{
					JLabel label = new JLabel("Data Iniziale:");
					label.setAlignmentX(CENTER_ALIGNMENT);
					datePanel.add(label);
					inizio = new MyDateSpinner();
					datePanel.add(inizio);
					GregorianCalendar cal = new GregorianCalendar();
					cal.add(Calendar.DAY_OF_MONTH, -30);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					Date d = cal.getTime();
					inizio.setValue(d);
					label = new JLabel("Data Finale:");
					label.setAlignmentX(CENTER_ALIGNMENT);
					datePanel.add(label);
					fine = new MyDateSpinner();
					datePanel.add(fine);

					inizio.addChangeListener(this);
					fine.addChangeListener(this);

					cal = new GregorianCalendar();
					cal.set(Calendar.HOUR_OF_DAY, 23);
					cal.set(Calendar.MINUTE, 59);
					cal.set(Calendar.SECOND, 59);
					cal.set(Calendar.MILLISECOND, 999);
					d = cal.getTime();
					fine.setValue(d);
				}

			}

			{
				buttonPanel = new JPanel();
				getContentPane().add(buttonPanel, BorderLayout.WEST);
				GridLayout buttonPanelLayout = new GridLayout(3, 1);
				buttonPanelLayout.setColumns(1);
				buttonPanelLayout.setHgap(5);
				buttonPanelLayout.setVgap(5);
				buttonPanel.setLayout(buttonPanelLayout);
				{
					insertButton = new JButton();
					buttonPanel.add(insertButton);
					insertButton.setText("Inserisci");
					insertButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/New.png")));
					insertButton.addActionListener(this);
				}
			}
			setCancelButtonVisible(false);
			Center();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		super.actionPerformed(e);
		Object source = e.getSource();
		if (source == insertButton)
			OnInsertClick();
	}

	private void updateMovimenti()
	{
		table.dataBind(controller.getMovimenti(inizio.getDateValue(), fine.getDateValue()));
	}

	private void updateData()
	{
		Collection<Movimento> movimenti = controller.getMovimenti(inizio.getDateValue(), fine.getDateValue());
		table.dataBind(movimenti);
	}

	private void OnInsertClick()
	{
		if (controller.inserisciMovimento())
		{
			updateMovimenti();
		}
	}

	public void setController(MovimentoController controller)
	{
		this.controller = controller;
		updateMovimenti();
		updateData();
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		if (controller != null)
		{
			updateData();
		}
	}
}
