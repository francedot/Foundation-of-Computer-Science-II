package gattovolpe.holidays.controller;

import gattovolpe.holidays.model.Pacchetto;

import java.util.Comparator;

public class DurataComparator implements Comparator<Pacchetto> {

	private int durata;
	
	public DurataComparator(int durata) {
		
		this.durata = durata;

	}

	@Override
	public int compare(Pacchetto arg0, Pacchetto arg1) {
		
		int first = Math.abs(durata - arg0.getDurataGiorni());
		int second = Math.abs(durata - arg1.getDurataGiorni());
		
		if (first > second)	return 1;
		if (first < second)	return -1;
		else return 0;
		
	}
	
}
