package firstDatabaseProject.controller;

import firstDatabaseProject.view.DatabaseFrame;
import firstDatabaseProject.view.DatabasePanel;
import firstDatabaseProject.view.DatabaseView;

/**
 * this is the entire controller for the entire project, with controllers for the databaseLogicController and GUI controllers
 * @author Vladster1256
 * @version 1.0
 */
public class DatabaseController 
{
	private DatabaseView applicationView;
	private DatabaseFrame appFrame;
	private DatabaseLogicController $database;

	/**
	 * this is the constructor for the DatabaseController class
	 */
	public DatabaseController()
	{
		//Initialize the DatabaseLogicController object that we can open and use
		$database = new DatabaseLogicController(this);
		//Initialize the DatabaseFrame object that we can open and see the GUI
		appFrame = new DatabaseFrame(this);
	}
	
	/**
	 * The first method that will run in the project, but since there is nothing the constructor will first go
	 */
	public void start()
	{
		$database.connectionStringBuilder("localhost", "vlad's_database_of_smash", "root", "");
		$database.checkDriver();
		$database.setupConnection();
		
		
	}
	
	/**
	 * our getter for the AppFrame that we can return the appFrame
	 * @return
	 */
	public DatabaseFrame getAppFrame()
	{
		return appFrame;
	}
	
	/**
	 * This gets the $database
	 * @return $database
	 */
	public DatabaseLogicController getDatabase()
	{
		return $database;
	}
}
