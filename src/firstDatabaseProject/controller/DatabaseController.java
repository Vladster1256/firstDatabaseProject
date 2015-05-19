package firstDatabaseProject.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

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
	private ArrayList<QueryInfo> queryList;

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

	
	/**
	 * This is the get timing info list array for query info
	 * @return the timing info list
	 */
	public ArrayList<QueryInfo> getTimingInfoList()
	{
		return timingInfoList;
	}

	/**
	 * We load the previously saved timing info list
	 */
	private void loadTimingInfo()
	{
		try
		{
			File loadFile = new File("asdasda.save");
			Scanner readFileScanner;
			if (loadFile.exists())
			{
				queryList.clear();
				Scanner textScanner = new Scanner(loadFile);
				while(textScanner.hasNext())
				{
					String query = textScanner.nextLine();
					long queryTime = Long.parseLong(textScanner.nextLine());
					queryList.add(new QueryInfo(query,queryTime));
				}
				textScanner.close();
				JOptionPane.showMessageDialog(getAppFrame(),queryList.size() + "Query objects were loaded to the file");
			}
			else{
				JOptionPane.showMessageDialog(getAppFrame(),"file not present. nothing was done");
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

	
	/**
	 * This is the method to save information in the array list that has the timing info list
	 */
	public void saveQueryTimingInfo()
	{
		

		try
		{
			File saveFile = new File("asdasda.txt");
			PrintWriter writer = new PrintWriter(saveFile);
			if(saveFile.exists())
			{
				for(QueryInfo wow: queryList)
				{
					writer.println(wow.getQuery());
					writer.println(wow.getQueryTime());
				}
				writer.close();
				JOptionPane.showMessageDialog(getAppFrame(), queryList.size() + "QueryInfo objects were saved");
			}
			else
			{
				JOptionPane.showMessageDialog(getAppFrame(), "File not present. nothing done");
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
