package money.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public abstract class Conto implements Bilancio
{
	private String nome;
	private ArrayList<Movimento> movimenti;	//inizialmente vuoti
	
	public Conto(String nome)
	{
		this.nome = nome;
		movimenti = new ArrayList<Movimento>();
	}

	public String getName()
	{
		return nome;
	}

	public void aggiungiMovimento(Movimento movimento)
	{
		String error = checkVincoli(movimento);
		if (error != null)
			throw new IllegalArgumentException(error);
		
		//La lista è mantenuta ordinata in modo automatico: è possibile inserire movimenti
		//solamente con data superiore o uguale a quella dell'ultimo movimento in lista-- senza comparator!
		movimenti.add(movimento);
	}

	public Collection<Movimento> getMovimenti()
	{
		return new ArrayList<Movimento>(movimenti);
	}
	
	public Collection<Movimento> getMovimenti(Date inizio, Date fine)
	{
		ArrayList<Movimento> filteredMovimenti = new ArrayList<Movimento>();
		for (Movimento movimento : movimenti)
		{
			if (inizio.getTime() <= movimento.getData().getTime() && 
				movimento.getData().getTime() <= fine.getTime())
			{
				filteredMovimenti.add(movimento);
			}
		}
		return filteredMovimenti;
	}
	
	public Collection<Movimento> getMovimenti(Date fine)
	{
		return getMovimenti(new Date(0), fine);
	}
	
	public double getSaldo(Date fine)
	{
		return getTotaleEntrate(new Date(0), fine) - getTotaleUscite(new Date(0), fine);
	}

	public double getTotaleUscite(Date inizio, Date fine)
	{
		double uscite = 0;
		for (Movimento m : getMovimenti(inizio, fine))
		{			
			if (m.getSorgente().equalsIgnoreCase(nome))
			{
				uscite += m.getDenaroMosso();
			}
		}
		return uscite;
	}
	
	public double getTotaleUscite(Date fine)
	{
		return getTotaleUscite(new Date(0), fine);
	}

	public double getTotaleEntrate(Date inizio, Date fine)
	{
		double entrate = 0;
		for (Movimento m : getMovimenti(inizio, fine))
		{			
			if (m.getDestinazione().equalsIgnoreCase(nome))
			{
				entrate += m.getDenaroMosso();
			}
		}
		return entrate;
	}
	
	public double getTotaleEntrate(Date fine)
	{
		return getTotaleEntrate(new Date(0), fine);
	}

	public int getNumTotaleMovimenti()
	{
		return movimenti.size();
	}

	public String checkVincoli(Movimento m)
	{
		if (!(m.getSorgente().equals(getName()) || m.getDestinazione().equals(getName())))
			throw new IllegalArgumentException("Il movimento non è relativo a questo conto");
		
		Movimento lastMovimento = movimenti.size() > 0 ? movimenti.get(movimenti.size() - 1) : null;
		if (lastMovimento != null && lastMovimento.getData().compareTo(m.getData()) > 0)
		{
			return "Impossibile inserire un movimento con data precedente rispetto all'ultimo presente.";
		}
		return null;
	}

	@Override
	public String toString()
	{
		return nome;
	}
}
