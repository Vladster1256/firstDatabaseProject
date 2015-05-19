package firstDatabaseProject.exception;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This is the custom Vlad exception that is throwable to classes that may try and error to prevent crashing
 * @author Vladlen Garder
 *
 */
public class Vlad extends Exception
{
	/**
	 * Constructor for sqlException
	 * @param currentError
	 */
	public Vlad(SQLException currentError)
	{
		super(currentError);
	}
	
	/**
	 * Constructor for ioException
	 * @param current
	 */
	public Vlad(IOException current)
	{
		super(current);
	}
}
