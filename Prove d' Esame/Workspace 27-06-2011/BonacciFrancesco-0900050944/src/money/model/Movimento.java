package money.model;

import java.io.Serializable;
import java.util.Date;

import money.ui.ColumnTable;

public class Movimento implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String causale;
	private Date data;
	private double denaroMosso;
	private String sorgente;
	private String destinazione;

	public Movimento(String causale, double denaroMosso, String sorgente, String destinazione, Date data)
	{
		if (causale == null || causale.isEmpty())
			throw new IllegalArgumentException("causale");
		if (denaroMosso <= 0)
			throw new IllegalArgumentException("denaroMosso");
		if (sorgente == null || sorgente.isEmpty())
			throw new IllegalArgumentException("sorgente");
		if (destinazione == null || destinazione.isEmpty())
			throw new IllegalArgumentException("destinazione");
		if (destinazione.equalsIgnoreCase(sorgente))
			throw new IllegalArgumentException("Destinazione e sorgente uguali");
		if (data == null)
			throw new IllegalArgumentException("data");

		this.causale = causale;
		this.denaroMosso = denaroMosso;
		this.sorgente = sorgente;
		this.destinazione = destinazione;
		this.data = data;
	}

	@ColumnTable(name = "Importo")
	public double getDenaroMosso()
	{
		return denaroMosso;
	}
	
	@ColumnTable(name = "Sorgente")
	public String getSorgente()
	{
		return sorgente;
	}
	
	@ColumnTable(name = "Destinazione")
	public String getDestinazione()
	{
		return destinazione;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Movimento))
		{
			return false;
		}
		Movimento st = (Movimento) o;
		return this.causale.equals(st.getCausale()) && 
			   this.data.equals(st.getData()) && 
			   this.denaroMosso == st.getDenaroMosso() && 
			   this.destinazione.equals(st.getDestinazione()) &&
			   this.sorgente.equals(st.getSorgente());
	}

	@ColumnTable(name = "Causale")
	public String getCausale()
	{
		return causale;
	}
	
	@ColumnTable(name = "Data")
	public Date getData()
	{
		return data;
	}
}
