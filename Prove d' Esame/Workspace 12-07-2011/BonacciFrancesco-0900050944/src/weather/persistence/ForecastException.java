package weather.persistence;

public class ForecastException extends Exception {

	private static final long serialVersionUID = 1L;

	public ForecastException() {
		
		super();
	}
	
	public ForecastException(String arg0, Throwable arg1) {
		
		super(arg0, arg1);
		
	}

	public ForecastException(String arg0) {
		
		super(arg0);
	
	}

	public ForecastException(Throwable arg0) {
		
		super(arg0);
		
	}

}
