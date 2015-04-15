package firstDatabaseProject.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JPasswordField password;
	private JTextField dateField;
	private TableCellWrapRenderer cellRenderer;
	private JButton confirmDateButton;

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
		appButton = new JButton("Test the query");
		displayArea = new JTextArea(10, 30);
		displayPane = new JScrollPane(displayArea);
		baseLayout = new SpringLayout();
		password = new JPasswordField(null, 20);
		cellRenderer = new TableCellWrapRenderer();
		dateField = new JTextField(null, 20);
		confirmDateButton = new JButton("Confirm teh Date");

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
		this.add(password);
		this.add(dateField);
		this.add(confirmDateButton);
		// password.setEchoChar(``);
		// password.setForeground(Color.RED);
		// password.setFont(new Font("Serif", Font.BOLD,30));
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
		baseLayout.putConstraint(SpringLayout.NORTH, password, 371, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, password, -24, SpringLayout.WEST, displayPane);
		baseLayout.putConstraint(SpringLayout.NORTH, confirmDateButton, 6, SpringLayout.SOUTH, dateField);
		baseLayout.putConstraint(SpringLayout.WEST, confirmDateButton, 208, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, dateField, 179, SpringLayout.WEST, this);

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
		baseLayout.putConstraint(SpringLayout.NORTH, dateField, 55, SpringLayout.SOUTH, displayPane);
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
				int answer = mainController.getDatabase().insertSample();
				displayArea.setText(displayArea.getText() + "\nRows Affected: " + answer);

			}
		});

		confirmDateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				try
				{
					SimpleDateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy");
					 Calendar c = Calendar.getInstance();
					 c.setTime(df.parse(dateField.getText()));
				}
				catch(java.text.ParseException e)
				{
					e.printStackTrace();
				}
			}

		});
	}
}
