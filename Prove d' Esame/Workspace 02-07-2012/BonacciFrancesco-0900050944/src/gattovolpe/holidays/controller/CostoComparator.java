package gattovolpe.holidays.controller;

import gattovolpe.holidays.model.Pacchetto;

import java.util.Comparator;

public class CostoComparator implements Comparator<Pacchetto> {

	@Override
	public int compare(Pacchetto arg0, Pacchetto arg1) {
		
		if (arg0.getCosto() > arg1.getCosto())	return 1;
		if (arg0.getCosto() < arg1.getCosto())	return -1;
		else return 0;
		
	}

}
