package raptor.model;

public enum Fine {

	LessThan10(40), LessThan40(155), LessThan60(500), Beyond60(780);

	private int value;
	
	private Fine(int value) {
		
		this.value = value;
		
	}

	public int getValue() {
		return value;
	}
	
	
	
	
	
}
