package gattovolpe.holidays.model;

public class Destinazione {

	private String stato;
	private String luogo;
	
	public Destinazione() {
		
	}

	public String getStato() {
		
		return stato;
		
	}

	public void setStato(String stato) {
		
		this.stato = stato;
		
	}

	public String getLuogo() {
		
		return luogo;
		
	}

	public void setLuogo(String luogo) {
		
		this.luogo = luogo;
		
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((luogo == null) ? 0 : luogo.toUpperCase().hashCode());
		result = prime * result + ((stato == null) ? 0 : stato.toUpperCase().hashCode());
		return result;
		
	}

	@Override
	public boolean equals(Object that) {
		
		if (that instanceof Destinazione) {
			
				return ( this.getStato().toUpperCase().equals(((Destinazione)that).getStato().toUpperCase())
							&& this.getLuogo().toUpperCase().equals(((Destinazione)that).getLuogo().toUpperCase()) );
				
		}
		else 
				return false;

	}
		
}
	
	
