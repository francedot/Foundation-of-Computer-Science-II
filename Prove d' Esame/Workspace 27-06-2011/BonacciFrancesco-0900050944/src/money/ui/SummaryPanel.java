package money.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import money.model.Bilancio;

public class SummaryPanel extends JPanel implements ChangeListener
{
	private static final long serialVersionUID = 1L;
	private MyDateSpinner inizio;
	private MyDateSpinner fine;
	private JTextField saldo;
	private JTextField totaleEntrate;
	private JTextField totaleUscite;
	private Bilancio bilancio;
	
	public SummaryPanel()
	{		
		initGUI();
	}
	
	public void setBilancio(Bilancio bilancio)
	{
		this.bilancio = bilancio;
		refreshData();
	}

	private void initGUI()
	{
		setBackground(Color.WHITE);
		
		GridLayout summaryPanelLayout = new GridLayout(6, 2);
		summaryPanelLayout.setColumns(2);
		summaryPanelLayout.setHgap(2);
		summaryPanelLayout.setVgap(2);
		summaryPanelLayout.setRows(6);
		setLayout(summaryPanelLayout);
		
		JLabel label = new JLabel("Data Iniziale:");
		label.setBackground(Color.WHITE);
		label.setAlignmentX(CENTER_ALIGNMENT);
		add(label);
		inizio = new MyDateSpinner();
		add(inizio);
		GregorianCalendar cal= new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -30);
		inizio.setValue(cal.getTime());

		label = new JLabel("Data Finale:");
		label.setBackground(Color.WHITE);
		label.setAlignmentX(CENTER_ALIGNMENT);
		add(label);
		fine = new MyDateSpinner();
		add(fine);
		fine.setValue(new Date());

		inizio.addChangeListener(this);
		fine.addChangeListener(this);

		label = new JLabel("Saldo:");
		label.setBackground(Color.WHITE);
		label.setAlignmentX(CENTER_ALIGNMENT);
		add(label);
		saldo = new JTextField();
		saldo.setEditable(false);
		add(saldo);

		label = new JLabel("Entrate:");
		label.setBackground(Color.WHITE);
		label.setAlignmentX(CENTER_ALIGNMENT);
		add(label);
		totaleEntrate = new JTextField();
		totaleEntrate.setEditable(false);
		add(totaleEntrate);

		label = new JLabel("Uscite:");
		label.setBackground(Color.WHITE);
		label.setAlignmentX(CENTER_ALIGNMENT);
		add(label);
		totaleUscite = new JTextField();
		totaleUscite.setEditable(false);
		add(totaleUscite);
	}
	
	public void refreshData()
	{
		saldo.setText("" + bilancio.getSaldo(fine.getDateValue()));
		totaleEntrate.setText("" + bilancio.getTotaleEntrate(inizio.getDateValue(), fine.getDateValue()));
		totaleUscite.setText("" + bilancio.getTotaleUscite(inizio.getDateValue(), fine.getDateValue()));
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		refreshData();
	}

}
