package money.model;

import java.text.DateFormat;
import java.util.Date;

public class CartaCredito extends Conto
{

	private double massimaSpesaMensile;

	public CartaCredito(String nome, double massimaSpesaMensile)
	{
		super(nome);
		this.massimaSpesaMensile = massimaSpesaMensile;
	}

	public double getMassimaSpesa()
	{
		return massimaSpesaMensile;
	}

	public void setMassimaSpesa(double massimaSpesa)
	{
		this.massimaSpesaMensile = massimaSpesa;
	}

	@Override
	public String checkVincoli(Movimento m)
	{
		String error = super.checkVincoli(m);
		if (error != null)
		{
			return error;
		}
		
		if (m.getSorgente().equals(getName()))
		{
			Date monthStart = DateUtils.getMonthStart(m.getData());
			if (getTotaleUscite(monthStart, m.getData()) + m.getDenaroMosso() > massimaSpesaMensile)
			{
				DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
				return "Superato tetto massimo di spesa per il mese corrente: " + format.format(monthStart);
			}
		}
		return null;
	}

	@Override
	public String toString()
	{
		return "Carta di Credito: " + super.toString() + " Totale spese ad oggi : " + getTotaleUscite(new Date()) + " \n";
	}

}
