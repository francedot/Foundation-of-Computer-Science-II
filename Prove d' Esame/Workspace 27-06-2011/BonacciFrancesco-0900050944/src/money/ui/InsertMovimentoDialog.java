package money.ui;

import java.awt.GridLayout;
import java.util.Collection;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import money.model.Movimento;

public class InsertMovimentoDialog extends FormDialog
{
	private static final long serialVersionUID = 1L;
	private JLabel sorgenteLabel;
	private JLabel destinazioneLabel;
	private JLabel causaleLabel;
	private JLabel importoLabel;
	private JLabel dataLabel;
	private JComboBox sorgenteBox;
	private JComboBox destinazioneBox;
	private JTextField causaleBox;
	private JTextField importoBox;
	private MyDateSpinner dataSpinner;
	private MovimentoController controller;

	public InsertMovimentoDialog(MovimentoController controller)
	{
		super();
		this.controller = controller;

		for (String c : this.controller.getConti())
		{
			sorgenteBox.addItem(c);
			destinazioneBox.addItem(c);
		}

		dataSpinner.setValue(new Date());
	}

	@Override
	protected void initGUI()
	{
		setTitle("Inserisci Movimento");
		GridLayout grid = new GridLayout(5, 2);
		getContentPanel().setLayout(grid);

		sorgenteLabel = new JLabel("Sorgente");
		getContentPanel().add(sorgenteLabel);
		sorgenteBox = new JComboBox();
		getContentPanel().add(sorgenteBox);

		destinazioneLabel = new JLabel("Destinazione");
		getContentPanel().add(destinazioneLabel);
		destinazioneBox = new JComboBox();
		getContentPanel().add(destinazioneBox);

		causaleLabel = new JLabel("Causale");
		getContentPanel().add(causaleLabel);
		causaleBox = new JTextField();
		getContentPanel().add(causaleBox);

		importoLabel = new JLabel("Importo");
		getContentPanel().add(importoLabel);
		importoBox = new JTextField();
		getContentPanel().add(importoBox);

		dataLabel = new JLabel("Data");
		getContentPanel().add(dataLabel);
		dataSpinner = new MyDateSpinner();
		getContentPanel().add(dataSpinner);

		this.setSize(400, 350);
	}

	@Override
	protected void OnOkClick()
	{
		String sorgente = sorgenteBox.getSelectedItem().toString();
		String destinazione = destinazioneBox.getSelectedItem().toString();
		if (sorgente.equals(destinazione))
		{
			JOptionPane.showMessageDialog(null, "Sorgente deve essere diverso da Destinazione.");
			return;
		}
		try
		{
			double value = Double.parseDouble(importoBox.getText());
			if (value <= 0)
			{
				JOptionPane.showMessageDialog(null, "Errore nell'importo: non sono ammessi valori negativi.");
				return;
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Errore nell'importo: non convertibile ad un numero.");
			return;
		}
		if (causaleBox.getText() == null || causaleBox.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "La causale non può essere vuota.");
			return;
		}
		Collection<String> vincoliViolati = controller.checkVincoli(getMovimento());
		if (!vincoliViolati.isEmpty())
		{
			StringBuilder sb = new StringBuilder();
			for (String error : vincoliViolati)
			{
				sb.append(error);
				sb.append("\n");
			}
			JOptionPane.showMessageDialog(null, sb.toString());
			return;
		}
		super.OnOkClick();
	}

	public Movimento getMovimento()
	{
		return new Movimento(causaleBox.getText(), Double.parseDouble(importoBox.getText()), sorgenteBox.getSelectedItem()
				.toString(), destinazioneBox.getSelectedItem().toString(), dataSpinner.getDateValue());
	}

}
