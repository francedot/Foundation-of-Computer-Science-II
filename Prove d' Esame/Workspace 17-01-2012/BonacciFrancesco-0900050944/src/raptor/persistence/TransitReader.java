package raptor.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import raptor.model.Transit;

public interface TransitReader
{
	List<Transit> readTransits(Reader reader) throws IOException, MalformedFileException;
}
