package firstDatabaseProject.exception;

import java.io.IOException;
import java.sql.SQLException;

public class Vlad extends Exception
{
	public Vlad(SQLException currentError)
	{
		super(currentError);
	}

	public Vlad(IOException current)
	{
		super(current);
	}
}
