package firstDatabaseProject.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import firstDatabaseProject.controller.DatabaseController;

/**
 * This is the frame that holds the JPanel in place
 * @author Vladster1256
 * @version 1.0
 */
public class DatabaseFrame extends JFrame
{
	private DatabasePanel mainPanel;
	private DatabaseController mainController;
	
	/**
	 * Constructor for this class
	 * @param mainController is the parameter for the DatabaseController so that way the controller can communicate with this class
	 */
	public DatabaseFrame(DatabaseController mainController)
	{
		this.mainController = mainController;
		mainPanel = new DatabasePanel(mainController);
		setupFrame();
		setupListeners();
		
	}
	
	/**
	 * Setup our frame here
	 */
	private void setupFrame()
	{
		this.setContentPane(mainPanel);
		this.setSize(800,800);
		this.setResizable(true);
		setVisible(true);
		
	}
	
	private void setupListeners()
	{
		this.addWindowListener(new WindowListener()
		{

			@Override
			public void windowActivated(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e)
			{
				//Call the save method info method!
				mainController.saveQueryTimingInfo();
			}

			@Override
			public void windowDeactivated(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
}
