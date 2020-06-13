package screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.SwingWorker;

import customJComponent.CustomJButton;
import customJComponent.CustomJLabel;
import customJComponent.CustomJPanel;
import sortingAlgorithm.SortingAlgorithm;

public class SidePanel extends CustomJPanel{
	private static final long serialVersionUID = 1L;
	
	private MainScreen main_screen;
	private SwingWorker<Void, Void> side_panel_animation1;
	private boolean is_showing_in = false;
	
	public SidePanel(MainScreen mainScreen, Rectangle dim) {
		super(dim);
		setBackground(Color.DARK_GRAY);
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		
		main_screen = mainScreen;
		initComponent(dim.width, dim.height);
	}
	
	/*
	 * selected_algorithm has the only instantiated object
	 * since its content will the only one that would change
	 */
	private void initComponent(int width, int height) {
		final int fixed_x = 0, title_height = height / 5;
		int y = 0, font_size = 30;	//these values change for each component

		String title_text = "<html><center>SORTING<br />ALGORITHM</center></html>";
		add(new CustomJLabel(new Rectangle(fixed_x, y, width, title_height), 
							title_text, new Font("Arial", Font.PLAIN, font_size)));
		
		font_size = 20;
		y = title_height;
		int selected_algo_height = title_height;
		String selected_algorithm_text = "NONE";
		CustomJLabel selected_algorithm = new CustomJLabel(new Rectangle(fixed_x, y, width, selected_algo_height), 
															selected_algorithm_text, 
															new Font("Arial", Font.PLAIN, font_size)) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void setText(String text) {
				String selected_algorithm = "<html><center>SELECTED SORTING : <br />" + text + "</center></html>";
				super.setText(selected_algorithm);
			}
		};
		add(selected_algorithm);
		
		AbstractAction action = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = ((CustomJButton)e.getSource()).getText();
				selected_algorithm.setText(text);
				
				main_screen.setAlgorithm(SortingAlgorithm.getAlgorithmViaText(text));				
			}
		};
		
		/*
		 *  divide the remaining spaces depending on the number of sorting
		 *  algorithms minus the "NO_ALGORITHM" 
		 */
		int button_height = (height - (title_height + selected_algo_height)) 
							/ (SortingAlgorithm.values().length - 1); 
		y = selected_algorithm.getY() + selected_algorithm.getHeight();
		
		for(SortingAlgorithm algo : SortingAlgorithm.values()) {
			if("NO_ALGORITHM".equals(algo.toString()))	continue;
			
			add(new CustomJButton(new Rectangle(fixed_x, y, width, button_height), 
					action, 
					algo.toString().replace("_", " ")));
			y += button_height;
		}
	}
	
	public void startSidePanelAnimation() {
		is_showing_in = !is_showing_in;
		
		// stop execution if it is currently running,
		// then execute again, this time in opposite direction
		if(side_panel_animation1 != null && !side_panel_animation1.isDone())
			side_panel_animation1.cancel(true);
		
		side_panel_animation1 = new SwingWorker<>(){
			@Override
			protected Void doInBackground() throws InterruptedException {
				while(!isCancelled()) {
					if(is_showing_in && getX() <= 0)
						setLocation(getX() + 1, getY());
					else if(!is_showing_in && getX() >= -1 * CUSTOM_JPANEL_WIDTH)
						setLocation(getX() - 1, getY());
					else
						break;
				}
				return null;
			}
			
			@Override
			protected void done() {
				super.done();
				side_panel_animation1.cancel(true);
			}
		};
		
		side_panel_animation1.execute();
	}
	
	public boolean isShown() {
		return getX() > -getWidth();
	}
}