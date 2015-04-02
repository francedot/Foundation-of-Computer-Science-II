package ztl.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import ztl.model.Transit;

public interface TransitReader
{
	List<Transit> read(Reader reader) throws IOException, MalformedFileException;
}
