package money.persistence;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import money.model.Movimento;

public class MyMovimentiManager implements MovimentiManager {

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Movimento> leggiMovimenti(InputStream inputStream)
			throws IOException, InvalidDataException {

		//ObjectInputStream innerStream = new ObjectInputStream(inputStream);
		Collection<Movimento> movimenti = null;
		
		try (ObjectInputStream innerStream = new ObjectInputStream(inputStream)) {

			movimenti = (Collection<Movimento>)innerStream.readObject();
			
		} catch (ClassNotFoundException e) {
			
			throw new InvalidDataException("Dati non validi", e);

		} catch (InvalidClassException e) {
			
			throw new InvalidDataException("Dati non validi", e);

		} catch (EOFException e) {

			return null;
			
		} //finally {	//comunque vada è buona prassi chiuderti! per evitare finally e close metti stream wrappante dentro try
			
		//	innerStream.close();
			
		//}
		
		return movimenti == null ? new ArrayList<Movimento>() : movimenti;//buona prassi non far passare niente di vuoto!
		
	
	}


	@Override
	public void scriviMovimenti(Collection<Movimento> listaMovimenti,
			OutputStream outputStream) throws IOException, InvalidDataException {
		
		//ObjectOutputStream oos = new ObjectOutputStream(outputStream);
		
		try(ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
			
			oos.writeObject(listaMovimenti);
			
		} catch (InvalidClassException e) {	//n.b controlla bene che eccezioni possono lanciare i metodi che chiami
			//ed aventualmente incapsula tali eccezioni le eccezioni wrappanti disponibili!
			
			throw new InvalidDataException("Scrittura: dati non validi", e);
			
		} //finally {
			
			//oos.close();
			
	  //}
		
	}

}
