package firstDatabaseProject.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
	private JScrollPane displayPane;
	private JButton appButton;
	private JTextArea displayArea;
	
	
	/**
	 * this is the constructor for the DatabasePanel class
	 * @param mainController this is how we refer the DatabaseController class for this class
	 */
	public DatabasePanel(DatabaseController mainController)
	{
		this.mainController = mainController;
		appButton = new JButton("Test the query");
		displayArea = new JTextArea(10,30);
		displayPane = new JScrollPane(displayArea);
		baseLayout = new SpringLayout();
		
		
		
		
		setupDisplayPane();
		 setupPanel();
		 setupLayout();
		 setupListeners();
		 
	}
	
	private void setupDisplayPane()
	{
		
	}
	
	/**
	 * This is where we setup the panel
	 */
	private void setupPanel()
	{
		this.setBackground(Color.BLUE);
		this.setLayout(baseLayout);
		this.add(appButton);
		this.add(displayPane);
	}
	
	/**
	 * This is where all of the constraints for the elements in the panel
	 */
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.NORTH, appButton, 27, SpringLayout.SOUTH, displayPane);
		baseLayout.putConstraint(SpringLayout.EAST, appButton, -149, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, displayPane, 29, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, displayPane, -91, SpringLayout.EAST, this);
	}
	
	/**
	 * This method is all of our listeners for all of the elements in the panel
	 */
	private void setupListeners()
	{
		appButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				int answer = mainController.getDatabase().insertSample();
				displayArea.setText(displayArea.getText() + "\nRows Affected: " + answer);
			}
		});
	}
}
