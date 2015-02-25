package firstDatabaseProject.view;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import firstDatabaseProject.controller.DatabaseController;

/**
 * this is the panel class for the GUI that we can sea with our i's. (W0W m8, d3d u jus1 do that?)
 * @author VGAR7399
 * @version 1.0
 */
public class DatabasePanel extends JPanel
{
	private DatabaseController mainController;
	private SpringLayout baseLayout;
	
	/**
	 * this is the constructor for the DatabasePanel class
	 * @param mainController this is how we refer the DatabaseController class for this class
	 */
	public DatabasePanel(DatabaseController mainController)
	{
		this.mainController = mainController;
		 setupPanel();
		 setupLayout();
		 setupListeners();
		 
	}
	
	/**
	 * This is where we setup the panel
	 */
	private void setupPanel()
	{
		this.setBackground(Color.MAGENTA);
	}
	
	/**
	 * This is where all of the constraints for the elements in the panel
	 */
	private void setupLayout()
	{
		
	}
	
	/**
	 * This method is all of our listeners for all of the elements in the panel
	 */
	private void setupListeners()
	{
		
	}
}
