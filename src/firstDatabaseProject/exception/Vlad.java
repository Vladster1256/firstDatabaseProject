package firstDatabaseProject.exception;

import java.sql.SQLException;

public class Vlad extends Exception
{
	public Vlad(SQLException currentError)
	{
		super(currentError);
	}
}
