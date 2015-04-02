package money.persistence;



public class InvalidDataException extends Exception
{
	private static final long serialVersionUID = 1L;

	public InvalidDataException()
	{
	}

	public InvalidDataException(String message)
	{
		super(message);
	}

	public InvalidDataException(Throwable innerException)
	{
		super(innerException);
	}

	public InvalidDataException(String message, Throwable innerException)
	{
		super(message, innerException);
	}

}