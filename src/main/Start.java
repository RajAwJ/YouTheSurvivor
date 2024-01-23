/**
 @author: Awais Javed
 */

package main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.*;

import util.Constants;
import util.GameObject;

public class Start extends JFrame {

	private static final long serialVersionUID = 1L;
	private static GameObject player;
	private static Model model;
	private static View view;
	private KeyListener Controller;
	private Timer timer;

	public Start() {
		
		initUI();
	}
	
	private void initUI() {

		player = new GameObject();
		model = new Model(player);

		JPanel statsDisplay = new JPanel();
		view = new View(model, statsDisplay);
		Controller = new Controller();

		JPanel holdingPanel = new JPanel();
		holdingPanel.setLayout( new BoxLayout( holdingPanel, BoxLayout.Y_AXIS ) );
		holdingPanel.setBorder( BorderFactory.createEmptyBorder( 0, 10, 0, 10 ) );

		holdingPanel.add( view );
		holdingPanel.add( statsDisplay );

		setTitle("Game Room");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation( ( int ) ( screenSize.width * 0.20 ), 0 );

		getContentPane().add( holdingPanel, BorderLayout.CENTER );
		pack();
		view.addKeyListener(Controller);

		ActionListener timerExecute = new ActionListener() {
			
			public void actionPerformed( ActionEvent evt) {
				
				view.requestFocusInWindow();
				model.initializeState();
				view.updateView();

				if ( model.getState().getCurrState() == 2 )
					timer.setDelay( Constants.PLAY_DELAY );
				else
					timer.setDelay( Constants.MENU_DELAY );
			}
		};
		timer = new Timer(Constants.MENU_DELAY, timerExecute);
		timer.start();
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				System.out.println( "Start time: " + System.currentTimeMillis() );
				Start st = new Start();
				st.setVisible(true);
			}
		});
	}
}