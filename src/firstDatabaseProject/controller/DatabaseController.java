package firstDatabaseProject.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import firstDatabaseProject.exception.Vlad;
import firstDatabaseProject.model.QueryInfo;
import firstDatabaseProject.view.DatabaseFrame;
import firstDatabaseProject.view.DatabasePanel;
import firstDatabaseProject.view.DatabaseView;

/**
 * this is the entire controller for the entire project, with controllers for
 * the databaseLogicController and GUI controllers
 * 
 * @author Vladster1256
 * @version 1.0
 */
public class DatabaseController
{
	private DatabaseView applicationView;
	private DatabaseFrame appFrame;
	private DatabaseLogicController $database;
	private ArrayList<QueryInfo> timingInfoList;

	/**
	 * this is the constructor for the DatabaseController class
	 */
	public DatabaseController()
	{
		// Initialize the DatabaseLogicController object that we can open and
		// use
		$database = new DatabaseLogicController(this);
		// Initialize the DatabaseFrame object that we can open and see the GUI
		appFrame = new DatabaseFrame(this);
	}

	/**
	 * The first method that will run in the project, but since there is nothing
	 * the constructor will first go
	 */
	public void start()
	{
//		loadTimingInfo();
	}

	/**
	 * our getter for the AppFrame that we can return the appFrame
	 * 
	 * @return
	 */
	public DatabaseFrame getAppFrame()
	{
		return appFrame;
	}

	/**
	 * This gets the $database
	 * 
	 * @return $database
	 */
	public DatabaseLogicController getDatabase()
	{
		return $database;
	}

	public ArrayList<QueryInfo> getTimingInfoList()
	{
		return timingInfoList;
	}

	private void loadTimingInfo()
	{
		File saveFile = new File("save.txt");
		try
		{
			Scanner readFileScanner;
			if (saveFile.exists())
			{
				timingInfoList.clear();
				readFileScanner = new Scanner(saveFile);
				while (readFileScanner.hasNext())
				{
					String tempQuery = readFileScanner.next();
					long tempTime = readFileScanner.nextLong();
					readFileScanner.next();
					timingInfoList.add(new QueryInfo(tempQuery, tempTime));
				}
				readFileScanner.close();
			}

		} catch (IOException current)
		{
			try
			{
				throw new Vlad(current);
			} catch (Vlad e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void saveQueryTimingInfo()
	{
		File saveFile = new File("save.txt");

		try
		{
			Scanner readFileScanner;
			if (saveFile.exists())
			{
				timingInfoList.clear();
				readFileScanner = new Scanner(saveFile);
				while (readFileScanner.hasNext())
				{
					String tempQuery = readFileScanner.nextLine();
					readFileScanner.next();
					long tempTime = readFileScanner.nextLong();

					timingInfoList.add(new QueryInfo(tempQuery, tempTime));
				}
				readFileScanner.close();
			}

		} catch (IOException current)
		{
			try
			{
				throw new Vlad(current);
			} catch (Vlad e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
