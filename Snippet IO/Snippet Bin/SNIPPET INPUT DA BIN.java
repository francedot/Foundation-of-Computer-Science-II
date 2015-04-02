public class BinaryComponentLoader implements ComponentLoader {

	private ObjectInputStream innerStream;

	public BinaryComponentLoader(InputStream inputStream) throws IOException {

	innerStream = new ObjectInputStream(inputStream);

	}

	//...

	@Override
	public Component read() throws IOException, InvalidDataException {
	
		try {

		return (Component)innerStream.readObject();
	
		}
		catch (ClassNotFoundException e) {
	
		throw new InvalidDataException("Dati non validi", e);

		}
		catch (InvalidClassException e) {
	
		throw new InvalidDataException("Dati non validi", e);

		}
		catch (EOFException e) {

		return null;
		}

	}



}