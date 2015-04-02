package gattovolpe.holidays.model;

import gattovolpe.utils.DateUtil;

import java.util.Date;
import java.util.GregorianCalendar;

public class Pacchetto {

	private String nome;
	private Date dataInizio;
	private int durataGiorni;
	private double costo;
	private String descrizione;
	private TipoPacchetto tipo;
	private Destinazione destinazione;
	
	public Pacchetto() {
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public int getDurataGiorni() {
		return durataGiorni;
	}

	public void setDurataGiorni(int durataGiorni) {
		this.durataGiorni = durataGiorni;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public TipoPacchetto getTipo() {
		return tipo;
	}

	public void setTipo(TipoPacchetto tipo) {
		this.tipo = tipo;
	}

	public Destinazione getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(Destinazione destinazione) {
		this.destinazione = destinazione;
	}
	
	public boolean isInPeriod(Date start, Date end) {
		
		if (start == null)
			throw new IllegalArgumentException();
		
		if (this.getTipo().equals(TipoPacchetto.SOLOVOLO)) {
			
			Date dataInizio = DateUtil.normalizeDate(this.getDataInizio());
			Date startNormalizzata = DateUtil.normalizeDate(start);
			
			return (dataInizio.equals(startNormalizzata));
			
		}
		else {
			
			if (end == null) {	//fine viaggio indeterminata
				
				Date startNormalizzata = DateUtil.normalizeDate(start);
				Date dataInizioPacchetto = (this.getDataInizio());
				
				return (dataInizioPacchetto.compareTo(startNormalizzata) >= 0);
						
			} else {
			
				Date startNormalizzata = DateUtil.normalizeDate(start);
				Date endNormalizzata = DateUtil.normalizeDate(end);
			
				Date dataInizioPacchetto = DateUtil.normalizeDate(this.getDataInizio());
				
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(dataInizioPacchetto);
			
				cal.add(GregorianCalendar.DATE, this.getDurataGiorni());
			
				Date dataFinePacchetto = DateUtil.normalizeDate(cal.getTime());
			
				return (dataInizioPacchetto.getTime() >= startNormalizzata.getTime()
						&& dataFinePacchetto.getTime() <= endNormalizzata.getTime());
				
			}
		}
		
	}
	
}
