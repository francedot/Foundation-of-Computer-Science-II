package gattovolpe.holidays.controller;

import gattovolpe.holidays.model.Pacchetto;

import java.util.Comparator;
import java.util.Date;

public class InizioComparator implements Comparator<Pacchetto> {

	Date date;
	
	public InizioComparator(Date date) {

		this.date = date;
	}

	@Override
	public int compare(Pacchetto arg0, Pacchetto arg1) {
		
		long first = Math.abs(date.getTime() - arg0.getDataInizio().getTime());
		long second = Math.abs(date.getTime() - arg1.getDataInizio().getTime());
		
		if (first > second)	return 1;
		if (first < second)	return -1;
		else return 0;
		
	}
	
}
