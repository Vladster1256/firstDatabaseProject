package firstDatabaseProject.controller;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * @version 1.0
 * @author VGAR7399 This is the class that we use to control all of the logic
 *         for the entire database project
 */
public class DatabaseLogicController
{
	private String connectionString;
	private Connection databaseConnection;
	private DatabaseController mainController;

	/**
	 * this is our constructor for this class
	 * 
	 * @param mainController
	 *            is our database Controller class
	 */
	public DatabaseLogicController(DatabaseController mainController)
	{
		connectionString = "jdbc:mysql://localhost/vlad's_database_of_smash?user=root";
		this.mainController = mainController;

		checkDriver();
		setupConnection();
	}

	/**
	 * this checks our driver for the database
	 */
	private void checkDriver()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception currentExeption)
		{
			displayErrors(currentExeption);
			JOptionPane.showMessageDialog(null, "wow, i wont open");
			System.exit(1);
		}
	}

	/**
	 * this is what we use to establish the connection to the database
	 */
	private void setupConnection()
	{
		try
		{
			databaseConnection = DriverManager.getConnection(connectionString);
		} catch (SQLException currentException)
		{
			displayErrors(currentException);
		}
	}

	/**
	 * This is our method we use for the database project to display the errors
	 * and error codes on JOptionPane popups
	 * 
	 * @param currentException
	 *            is the exception that the try catch methods have caught and
	 *            sent it here
	 */
	public void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(mainController.getAppFrame(), currentException.getMessage());
		;
		;
		;
		;
		;
		if (currentException instanceof SQLException)
		{
			JOptionPane.showMessageDialog(mainController.getAppFrame(), "SQL State:" + ((SQLException) currentException).getSQLState());
			JOptionPane.showMessageDialog(mainController.getAppFrame(), "SQL Error Code:" + ((SQLException) currentException).getErrorCode());
		}
	}

	/**
	 * This is the method we use to close the connection
	 */
	public void closeConnection()
	{
		try
		{
			databaseConnection.close();
		} catch (SQLException error)
		{
			displayErrors(error);
		}
	}

	/**
	 * This queries the database SHOW TABLES and returns the results
	 * 
	 * @return results
	 */
	public String displayTables()

	{
		String results = "";
		String query = "SHOW TABLES";

		/**
		 * try the statement, get the answer, and for each answer keep going
		 * until its out and close it.
		 */
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			while (answer.next())
			{
				results += answer.getString(1) + "\n";
			}
			answer.close();
			firstStatement.close();
		} catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}

		return results;
	}

	public String[][] tableInfo()
	{
		String[][] results;
		String query = "SHOW TABLES";

		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			int rowCount;
			answer.last();
			rowCount = answer.getRow();
			answer.beforeFirst();
			results = new String[rowCount][1];

			while (answer.next())
			{
				results[answer.getRow() - 1][0] = answer.getString(1);
			}

			answer.close();
			firstStatement.close();
		} catch (SQLException currentSQLError)
		{
			results = new String[][] { { "problem occured :((((((((((((((" } };
			displayErrors(currentSQLError);

		}

		return results;
	}
	
	public String [] getMetaData()
	{
		String [] columnInformation;
		String query = "SHOW TABLES";
		
		
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			ResultSetMetaData myMeta = answer.getMetaData();
			
			columnInformation = new String [myMeta.getColumnCount()];
			for(int spot = 0; spot < myMeta.getColumnCount(); spot ++)
			{
				columnInformation[spot] = myMeta.getColumnName(spot + 1);
			}
			
			answer.close();
			firstStatement.close();
		}
		catch(SQLException currentSQLError)
		{
			columnInformation = new String [] {"nada exists"};
			displayErrors(currentSQLError);
		}
		return columnInformation;
	}

	public int insertSample()
	{
		int rowsAffected = 0;
		String insertQuery = "INSERT INTO `houses`.`my_houses` () Values ();";

		try
		{
			Statement insertStatement = databaseConnection.createStatement();
			rowsAffected = insertStatement.executeUpdate(insertQuery);
			insertStatement.close();

		} catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}

		return rowsAffected;
	}

	/**
	 * This is the method that we use to describe the columns in the table, but
	 * not the data itself
	 * 
	 * @return it returns the string of the column headers of the table
	 */
	public String describeTable()
	{
		String results = "";
		String query = "DESCRIBE character_list";

		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			while (answer.next())
			{
				results += answer.getString(1) + "\t" + "\t" + answer.getString(2) + "\t" + "\t" + answer.getString(3) + "\t" + answer.getString(4) + "\n";
			}
			answer.close();
			firstStatement.close();
		} catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}

		return results;
	}
}
