package firstDatabaseProject.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
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
public class DynamicDataPanel extends JPanel
{
	private DatabaseController mainController;
	private SpringLayout baseLayout;
	private JScrollPane displayPane;
	private JButton submitQueryButton;
	private ArrayList<JTextField> inputFieldList;
	private String tableName;

	/**
	 * this is the constructor for the DatabasePanel class
	 * 
	 * @param mainController
	 *            this is how we refer the DatabaseController class for this
	 *            class
	 */
	public DynamicDataPanel(DatabaseController mainController, String tableName)
	{
		this.mainController = mainController;
		this.tableName = tableName;
		baseLayout = new SpringLayout();
		submitQueryButton = new JButton();
		inputFieldList = new ArrayList<JTextField>();

		setupPanel(tableName);
		setupLayout();
		setupListeners();
	}

	private void setupPanel(String table)
	{
		this.setLayout(baseLayout);
		this.add(submitQueryButton);
		int verticalOffset = 20;
		String[] columns = mainController.getDatabase().getDatabaseColumnNames(table);

		for (int fieldCount = 0; fieldCount < mainController.getDatabase().getDatabaseColumnNames(table).length; fieldCount++)
		{
			if (!columns[fieldCount].equalsIgnoreCase("id"))
			{
				JLabel dynamicLabel = new JLabel(columns[fieldCount] + "entry field:");
				JTextField dynamicField = new JTextField(10);
				this.add(dynamicLabel);
				this.add(dynamicField);
				dynamicLabel.setName(columns[fieldCount] + "Label");
				dynamicField.setName(columns[fieldCount] + "Field");

				inputFieldList.add(dynamicField);

				baseLayout.putConstraint(SpringLayout.NORTH, dynamicLabel, verticalOffset, SpringLayout.NORTH, this);
				baseLayout.putConstraint(SpringLayout.NORTH, dynamicField, verticalOffset, SpringLayout.NORTH, this);
				baseLayout.putConstraint(SpringLayout.EAST, dynamicField, 60, SpringLayout.EAST, dynamicLabel);

				verticalOffset += 50;
			}
		}
	}

	/**
	 * This is where all of the constraints for the elements in the panel
	 */
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.SOUTH, submitQueryButton, -50, SpringLayout.SOUTH, this);
	}
	
	private String getValueList()
	{
		String values = "";
		
		return values;
	}
	
	private String getFieldList()
	{
		String fields = "";
		
		return fields;
	}

	/**
	 * This method is all of our listeners for all of the elements in the panel
	 */
	private void setupListeners()
	{
		JTextField[] myFields;
		ArrayList<JTextField> myTextFields = new ArrayList<JTextField>();
		int fieldCount = 0;
		for (Component current : this.getComponents())
		{
			if (current instanceof JTextField)
			{
				fieldCount++;
			}
		}
		myFields = new JTextField[fieldCount];
		for (Component current : this.getComponents())
		{
			if (current instanceof JTextField)
			{
				myFields[fieldCount - 1] = (JTextField) current;
				fieldCount--;
			}
		}

	}
}
