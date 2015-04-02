package money.model;

import java.util.Comparator;

public class DateComparator implements Comparator<Movimento> {

	@Override
	public int compare(Movimento arg0, Movimento arg1) {
		
		return arg0.getData().compareTo(arg1.getData());

	}

	
	
}
