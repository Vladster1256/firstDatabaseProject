package firstDatabaseProject.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import firstDatabaseProject.controller.DatabaseController;
import firstDatabaseProject.controller.DatabaseLogicController;
import firstDatabaseProject.exception.Vlad;

/**
 * this is the panel class for the GUI that we can sea with our i's. (W0W m8,
 * d3d u jus1 do that?)
 * 
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
	private JTable tableData;
	private JTextField queryEntry;
	private TableCellWrapRenderer cellRenderer;
	public DatabaseLogicController $controller;

	/**
	 * this is the constructor for the DatabasePanel class
	 * 
	 * @param mainController
	 *            this is how we refer the DatabaseController class for this
	 *            class
	 */
	public DatabasePanel(DatabaseController mainController)
	{
		this.mainController = mainController;
		this.$controller = mainController.getDatabase();
		appButton = new JButton("Test the query");
		displayArea = new JTextArea(10, 30);
		displayPane = new JScrollPane(displayArea);
		baseLayout = new SpringLayout();
		cellRenderer = new TableCellWrapRenderer();
		queryEntry = new JTextField(null, 20);
	


		setupTable();
		setupDisplayPane();
		setupPanel();
		setupLayout();
		setupListeners();

	}

	/**
	 * This is what we use to setup the diplay pane
	 */
	private void setupDisplayPane()
	{
		
		baseLayout.putConstraint(SpringLayout.WEST, queryEntry, -527, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, queryEntry, -21, SpringLayout.NORTH, appButton);
		baseLayout.putConstraint(SpringLayout.EAST, queryEntry, -134, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, appButton, -119, SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, appButton, -295, SpringLayout.EAST, this);
	}

	/**
	 * This is where we setup the panel
	 */
	private void setupPanel()
	{
		this.setBackground(Color.BLUE);
		this.setLayout(baseLayout);
		this.setSize(800, 800);
		this.add(appButton);
		this.add(displayPane);
		this.add(queryEntry);
		// password.setEchoChar(``);
		// password.setForeground(Color.RED);
		// password.setFont(new Font("Serif", Font.BOLD,30));
	}

	/**
	 * This is where all of the constraints for the elements in the panel
	 */
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.NORTH, displayPane, 29, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, displayPane, -91, SpringLayout.EAST, this);

	}

	/**
	 * This is what we use to setup the view so it looks like a table and its
	 * all organized nice
	 */
	private void setupTable()
	// One d array for column titles
	// 2d array for contents
	{
		tableData = new JTable(new DefaultTableModel(mainController.getDatabase().tableInfo(), mainController.getDatabase().getMetaData()));
		displayPane = new JScrollPane(tableData);
		for (int spot = 0; spot < tableData.getColumnCount(); spot++)
		{
			tableData.getColumnModel().getColumn(spot).setCellRenderer(cellRenderer);
		}
	}

	/**
	 * This method is all of our listeners for all of the elements in the panel
	 */
	private void setupListeners()
	{
		/**
		 * we are getting the mainController, and inserting sampleMethod code.
		 * Which inserts stuffs into the database
		 */
		appButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
//				int answer = mainController.getDatabase().insertSample();
//				displayArea.setText(displayArea.getText() + "\nRows Affected: " + answer);
				try
				{
					$controller.submitQuery(queryEntry.getText());
					displayPane.revalidate();
					displayArea.revalidate();
					setupTable();
					
				} catch (Vlad e)
				{
					// TODO Auto-generated catch block
					$controller.displayErrors(e);
				}
				
				

			}
		});
	}
}
