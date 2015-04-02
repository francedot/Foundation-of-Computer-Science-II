package ztl.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public interface AuthorizedPlateReader
{
	List<String> read(Reader reader) throws IOException, MalformedFileException;
}
