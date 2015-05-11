package firstDatabaseProject.controller;

import java.io.File;
import java.io.PrintWriter;
import java.sql.*;

import javax.security.sasl.SaslException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import firstDatabaseProject.exception.Vlad;
import firstDatabaseProject.model.QueryInfo;

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
	private String currentQuery;

	/**
	 * this is our constructor for this class
	 * 
	 * @param mainController
	 *            is our database Controller class
	 */
	public DatabaseLogicController(DatabaseController mainController)
	{
		this.mainController = mainController;
		connectionString = "jdbc:mysql://localhost/vlad's_database_of_smash?user=root";

		checkDriver();
		setupConnection();
	}

	/**
	 * This is the method that we use to change the connectionString with all of
	 * the different parameters that the user needs to use
	 * 
	 * @param pathToDBServer
	 *            its the path to the Server
	 * @param databaseName
	 *            its the database name
	 * @param userName
	 *            the user name of the database
	 * @param password
	 *            password for the user
	 */
	public void connectionStringBuilder(String pathToDBServer, String databaseName, String userName, String password)
	{
		connectionString = "jdbc:mysql://";
		connectionString += pathToDBServer;
		connectionString += "/" + databaseName;
		connectionString += "?user=" + userName;
		connectionString += "&password=" + password;
	}

	/**
	 * this checks our driver for the database
	 */
	public void checkDriver()
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
	public void setupConnection()
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
	 * This checks if the query contains DROP, TRUNCATE, SET, OR ALTER in a
	 * query statement
	 * 
	 * @return true or false
	 */
	private boolean checkForDataViolation()
	{
		if (currentQuery.toUpperCase().contains(" DROP ") || currentQuery.toUpperCase().contains(" TRUNCATE ") || currentQuery.toUpperCase().contains(" SET ") || currentQuery.toUpperCase().contains(" ALTER "))
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * checks if the query contains DATABASE so we cannot modify or drop
	 * database
	 * 
	 * @return true or false depending if the condition is true or not
	 */
	private boolean checkForStructureViolation()
	{
		if (currentQuery.toUpperCase().contains(" DATABASE "))
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * This checks for creation of content
	 * 
	 * @return true or false
	 */
	private boolean checkForCreation()
	{
		if (currentQuery.toUpperCase().contains("CREATE"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * This creates a dialog with clippy image to ask if you want to create
	 * something
	 */
	public void createThingy()
	{
		String results;

		if (checkForCreation())
		{
			final ImageIcon icon = new ImageIcon(DatabaseLogicController.class.getResource("/firstDatabaseProject/images/clippy.jpg"));
			String[] buttons = new String[] { "Yes Sirrie!!", "Na m8" };
			JOptionPane.showOptionDialog(null, null, "I See that you are trying to CREATE something, would you like help with that?", JOptionPane.WARNING_MESSAGE, 0, icon, buttons, buttons[1]);

		}

	}

	/**
	 * This will allow us to drop the table or indexes, but not the database,
	 * throws an exception if a drop of DB is attempted m8.
	 */
	public void dropStatement()
	{
		String results;
		try
		{
			if (checkForStructureViolation())
			{
				throw new SQLException("you is no allowed to dropping db's", "duh", Integer.MIN_VALUE);
			}

			if (currentQuery.toUpperCase().contains(" INDEX "))
			{
				results = "The index was ";
			} else
			{
				results = "The table was ";
			}

			Statement dropStatement = databaseConnection.createStatement();
			int affected = dropStatement.executeUpdate(currentQuery);

			dropStatement.close();

			if (affected == 0)
			{
				results += "dropped";
			}
			JOptionPane.showMessageDialog(mainController.getAppFrame(), results);
		} catch (SQLException dropError)
		{
			displayErrors(dropError);
		}
	}

	/**
	 * Generic select based query for the databaseLogicController Checks that
	 * the query will not destroy data by calling the checkForDataViolation
	 * method in the try catch
	 * 
	 * @param query
	 *            the query to be executed on the database it will be set at the
	 *            currentQuery for the controller
	 * @throws SQLExeption
	 *             if the currentQuery contains DDl statements that would affect
	 *             the structure/contents of the database. :\
	 * @return the 2d array of results
	 */
	public String[][] selectQueryResults(String query)
	{
		this.currentQuery = query;
		String[][] results;
		long startTime, endTime;
		startTime = System.currentTimeMillis();

		try
		{
			if (checkForDataViolation())
			{
				throw new SQLException("Attempted illegal modification of data", ":( tried to mess up da data state :0", Integer.MIN_VALUE);
			}
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			int columnCount = answer.getMetaData().getColumnCount();

			answer.last();
			int rowCount = answer.getRow();
			answer.beforeFirst();
			results = new String[rowCount][columnCount];
			while (answer.next())
			{
				for (int col = 0; col < columnCount; col++)
				{
					results[answer.getRow() - 1][col] = answer.getString(col + 1);
				}
			}
			endTime = System.currentTimeMillis();
		} catch (SQLException currentSQLError)
		{
			results = new String[][] { { "error procressing query" }, { "try sending a better query" }, { currentSQLError.getMessage() } };
			displayErrors(currentSQLError);
			endTime = System.currentTimeMillis();
		}
		mainController.getTimingInfoList().add(new QueryInfo(currentQuery,endTime-startTime));
		return results;
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
		long startTime, endTime;
		startTime = System.currentTimeMillis();

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
			endTime = System.currentTimeMillis();
		} catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
			endTime = System.currentTimeMillis();
		}
		mainController.getTimingInfoList().add(new QueryInfo(currentQuery,endTime-startTime));

		return results;
	}

	/**
	 * this gets the table info and converts the 1d array to a 2d array with a
	 * width of 1 and a length of the length of the tables.
	 * 
	 * @return the 2d array results
	 */
	public String[][] tableInfo()
	{
		String[][] results;
		String query = "SHOW TABLES";
		long startTime, endTime;
		startTime = System.currentTimeMillis();

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
			endTime = System.currentTimeMillis();
		} catch (SQLException currentSQLError)
		{
			results = new String[][] { { "problem occured :(((((((" } };
			displayErrors(currentSQLError);
			endTime = System.currentTimeMillis();

		}

//		mainController.getTimingInfoList().add(new QueryInfo(currentQuery,endTime-startTime));
		return results;
	}

	/**
	 * this gets the metaData of the tables in the database
	 * 
	 * @return
	 */
	public String[] getMetaData()
	{
		String[] columnInformation;
		String query = "SHOW TABLES";

		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			ResultSetMetaData myMeta = answer.getMetaData();

			columnInformation = new String[myMeta.getColumnCount()];
			for (int spot = 0; spot < myMeta.getColumnCount(); spot++)
			{
				columnInformation[spot] = myMeta.getColumnName(spot + 1);
			}

			answer.close();
			firstStatement.close();
		} catch (SQLException currentSQLError)
		{
			columnInformation = new String[] { "nada exists" };
			displayErrors(currentSQLError);
		}
		return columnInformation;
	}

	/**
	 * This inserts data into the database
	 * 
	 * @return the number of rowsAffected
	 */
	public int insertSample()
	{
		int rowsAffected = 0;
		String insertQuery = "CREATE TABLE `Stewie's_fave's` (id int(20));";
		long startTime, endTime;
		startTime = System.currentTimeMillis();

		try
		{
			Statement insertStatement = databaseConnection.createStatement();
			rowsAffected = insertStatement.executeUpdate(insertQuery);
			insertStatement.close();
			endTime = System.currentTimeMillis();

		} catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
			endTime = System.currentTimeMillis();
		}

		mainController.getTimingInfoList().add(new QueryInfo(currentQuery,endTime-startTime));
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
		long startTime, endTime;
		startTime = System.currentTimeMillis();

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
			endTime = System.currentTimeMillis();
		} catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
			endTime = System.currentTimeMillis();
		}

		mainController.getTimingInfoList().add(new QueryInfo(currentQuery,endTime-startTime));
		return results;
	}

	/**
	 * gets results from the sys_columns
	 * 
	 * @return the 2d array results and
	 */
	public String[][] realResults()
	{
		String[][] results;
		String query = "SELECT * FROM `INNODB_SYS_COLUMNS";
		long startTime, endTime;
		startTime = System.currentTimeMillis();

		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			int columnCount = answers.getMetaData().getColumnCount();

			answers.last();
			int numberOfRows = answers.getRow();

			results = new String[numberOfRows][columnCount];

			while (answers.next())
			{
				for (int col = 0; col < columnCount; col++)
				{
					results[answers.getRow() - 1][col] = answers.getString(col + 1);
				}
			}
			endTime = System.currentTimeMillis();
		} catch (SQLException currentSQLError)
		{
			results = new String[][] { { "error processing" } };
			displayErrors(currentSQLError);
			endTime = System.currentTimeMillis();
		}
		mainController.getTimingInfoList().add(new QueryInfo(currentQuery,endTime-startTime));
		return results;
	}

	/**
	 * This is what we use to submit a Query
	 * @param currentQuery is the Query we pass it
	 * @throws Vlad when an exception is met
	 */
	public void submitQuery(String currentQuery) throws Vlad
	{
		this.currentQuery = currentQuery;
		long startTime, endTime = 0;
		startTime = System.currentTimeMillis();
		if (!checkForDataViolation())
		{

			try
			{
				Statement submitStatement = databaseConnection.createStatement();
				submitStatement.executeUpdate(currentQuery);
				submitStatement.close();
				endTime = System.currentTimeMillis();
			} catch (SQLException currentError)
			{
				endTime = System.currentTimeMillis();
				throw new Vlad(currentError);
			}
			
		}
		mainController.getTimingInfoList().add(new QueryInfo(currentQuery,endTime-startTime));

	}

	/**
	 * This is part of the dynamic buttons and columns for the database that
	 * returns the string array for table name
	 * 
	 * @param tableName
	 *            the string for the table name
	 * @return the string array columns
	 */
	public String[] getDatabaseColumnNames(String tableName)
	{
		String[] columns;
		currentQuery = "SELECT * FROM `" + tableName + "`";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(currentQuery);
			ResultSetMetaData answerData = answers.getMetaData();

			columns = new String[answerData.getColumnCount()];

			for (int column = 0; column < answerData.getColumnCount(); column++)
			{
				columns[column] = answerData.getColumnName(column++);
			}

			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();

		} catch (SQLException currentException)
		{
			endTime = System.currentTimeMillis();
			columns = new String[] { "empty" };
			displayErrors(currentException);
		}
		long queryTime = endTime - startTime;
		mainController.getTimingInfoList().add(new QueryInfo(currentQuery, queryTime));
		return columns;
	}
	
	public void saveTimingInformation()
	{
		try
		{
			File saveFile = new File("asdfasdf.save");
			PrintWriter writer = new PrintWriter(saveFile);
			if(saveFile.exists())
			{
				
			}
		}
		catch()
		{
			
		}
	}
}
