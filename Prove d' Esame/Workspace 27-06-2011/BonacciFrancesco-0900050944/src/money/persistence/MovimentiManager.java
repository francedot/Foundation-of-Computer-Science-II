package money.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import money.model.*;


public interface MovimentiManager
{
	Collection<Movimento> leggiMovimenti(InputStream inputStream) throws IOException, InvalidDataException;
	void scriviMovimenti(Collection<Movimento> listaMovimenti, OutputStream outputStream) throws IOException, InvalidDataException;
}
