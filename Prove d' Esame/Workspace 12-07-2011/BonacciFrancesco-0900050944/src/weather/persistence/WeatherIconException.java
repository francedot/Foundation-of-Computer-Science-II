package weather.persistence;

public class WeatherIconException extends Exception
{
	private static final long serialVersionUID = 1L;

	public WeatherIconException()
	{
	}

	public WeatherIconException(String message)
	{
		super(message);
	}

	public WeatherIconException(Throwable innerException)
	{
		super(innerException);
	}

	public WeatherIconException(String message, Throwable innerException)
	{
		super(message, innerException);
	}


}
