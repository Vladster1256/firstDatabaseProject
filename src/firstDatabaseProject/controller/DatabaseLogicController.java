package firstDatabaseProject.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * @version 1.0
 * @author VGAR7399
 * This is the class that we use to control all of the logic for the entire database project
 */
public class DatabaseLogicController
{
	private String connectionString;
	private Connection databaseConnection;
	private DatabaseController mainController;
	
	/**
	 * this is our constructor for this class
	 * @param mainController is our database Controller class
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
		}
		catch(Exception currentExeption)
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
		}
		catch(SQLException currentException)
		{
			displayErrors(currentException);
		}
	}
	
	/**
	 * This is our method we use for the database project to display the errors and error codes on JOptionPane popups
	 * @param currentException is the exception that the try catch methods have caught and sent it here
	 */
	public void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(mainController.getAppFrame(), currentException.getMessage());;;;;;
		if(currentException instanceof SQLException)
		{
			JOptionPane.showMessageDialog(mainController.getAppFrame(), "SQL State:" + ((SQLException)currentException).getSQLState());
			JOptionPane.showMessageDialog(mainController.getAppFrame(),"SQL Error Code:" + ((SQLException)currentException).getErrorCode());;;;;;;;;;;;;
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
		}
		catch(SQLException error)
		{
			displayErrors(error);
		}
	}
}