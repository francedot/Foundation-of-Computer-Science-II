package gattovolpe.holidays.controller;

import gattovolpe.holidays.model.Destinazione;
import gattovolpe.holidays.model.GattoVolpeHolidays;
import gattovolpe.holidays.model.Pacchetto;
import gattovolpe.holidays.model.TipoPacchetto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyController implements Controller {

	private GattoVolpeHolidays gvh;
	
	public MyController(GattoVolpeHolidays gvh) {
		
		this.gvh = gvh;
		
	}

	
	
	@Override
	public List<Pacchetto> evalPackage(Comparator<Pacchetto> criterio,
			TipoPacchetto tipo, String stato, double prezzo, Date start,
			Date end) {

		ArrayList<Pacchetto> pacchettiSoddisf = new ArrayList<Pacchetto>();
		
		for (Pacchetto p : gvh.getElencoPacchetti()) {
			
			if (p.getTipo().equals(tipo)
					&& p.getDestinazione().getStato().equals(stato)
						&& p.getCosto() <= prezzo && p.isInPeriod(start, end)) {
				
				pacchettiSoddisf.add(p);
				
			}
				
		}
		
		Collections.sort(pacchettiSoddisf, criterio);
		
		return pacchettiSoddisf;
		
	}

	@Override
	public Set<Destinazione> getDestinazioni() {

		//Set<Destinazione> setDestinazioni
		List<Pacchetto> listaPacchetti = gvh.getElencoPacchetti();
		
		Set<Destinazione> setDestinazioni = new HashSet<Destinazione>();
		
		for (Pacchetto p : listaPacchetti) {
			
			setDestinazioni.add(p.getDestinazione());
			
		}
		
		return setDestinazioni;
		
	}

}
