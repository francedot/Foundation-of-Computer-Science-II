package money.model;

import java.util.Date;

public class ContoCorrente extends Conto
{

	private double massimoScoperto;

	public ContoCorrente(String nome, double massimoScoperto)
	{
		super(nome);
		this.massimoScoperto = massimoScoperto;
	}

	public double getMassimoScoperto()
	{
		return massimoScoperto;
	}

	public void setMassimoScoperto(double massimoScoperto)
	{
		this.massimoScoperto = massimoScoperto;
	}

	public String checkVincoli(Movimento m)
	{
		String error = super.checkVincoli(m);
		if (error != null)
		{
			return error;
		}
		
		if (m.getSorgente().equals(getName()))
		{
			if (getSaldo(m.getData()) - m.getDenaroMosso() < -massimoScoperto)
			{
				return "Superato il massimo scoperto: " + massimoScoperto;
			}
		}
		return null;
	}

	@Override
	public String toString()
	{
		return "Conto Corrente: " + super.toString() + " Saldo attuale: " + getSaldo(new Date()) + " \n";
	}

}
