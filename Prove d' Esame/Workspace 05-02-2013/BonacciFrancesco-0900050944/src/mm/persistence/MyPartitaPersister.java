package mm.persistence;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import mm.model.Partita;

public class MyPartitaPersister implements PartitaPersister {

	@Override
	public Partita read(InputStream source) throws IOException, BadFileFormatException {
		
		ObjectInputStream innerStream = new ObjectInputStream(source);
		
		try {

			return (Partita)innerStream.readObject();
		
		}
		catch (ClassNotFoundException e) {

		throw new BadFileFormatException("Dati non validi", e);

		}
		catch (InvalidClassException e) {
			
			throw new BadFileFormatException("Dati non validi", e);

		}
		catch (EOFException e) {

			return null;
			
		}

	}


	@Override
	public void write(Partita partita, OutputStream dest) throws IOException {

		ObjectOutputStream innerStream = new ObjectOutputStream(dest);
		
		innerStream.writeObject(partita);
		

	}

}
