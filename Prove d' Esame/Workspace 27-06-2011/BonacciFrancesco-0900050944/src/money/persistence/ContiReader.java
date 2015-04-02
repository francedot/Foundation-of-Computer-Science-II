package money.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;

import money.model.*;

public interface ContiReader 
{
	public Collection<Conto> readAll(Reader reader) throws IOException, InvalidDataException;
}
