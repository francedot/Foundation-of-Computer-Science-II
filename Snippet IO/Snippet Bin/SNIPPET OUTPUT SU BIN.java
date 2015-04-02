public class BinaryComponentSaver implements ComponentSaver {

	private ObjectOutputStream innerStream;
	
	public BinaryComponentSaver(OutputStream outputStream) throws IOException {

	innerStream = new ObjectOutputStream(outputStream);
	

	}

	@Override
	public void write(Component c) throws IOException
	
	{
	
		innerStream.writeObject(c);
	
	}

}