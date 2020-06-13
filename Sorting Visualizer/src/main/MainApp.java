package main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import screen.MainScreen;

public class MainApp {
	public static final int SCREEN_WIDTH = 1080;
	public static final int SCREEN_HEIGHT = SCREEN_WIDTH * 9 / 16;
	
	private JFrame frame = null;
	
	private void start() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		MainScreen screen = new MainScreen(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		frame = new JFrame("Sorting Visualizer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setContentPane(screen);
		frame.validate();
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		screen.requestFocusInWindow();
	}
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainApp().start();
			}			
		});
	}
}