package screen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingWorker;

import customJComponent.CustomJPanel;
import customJComponent.CustomSwingWorker;
import sortingAlgorithm.SortingAlgorithm;

public class SortingPanel extends CustomJPanel {
	private static final long serialVersionUID = 1L;
	
	private MainScreen mainScreen;
	
	private final float BAR_STROKE_THICKNESS = 2f;
	private final int FONT_SIZE = 15;
	private final Font ALGORITHM_FONT = new Font("Arial", Font.PLAIN, FONT_SIZE);
	
	/* default values */
	private int size = Menu.INIT_SIZE_SLIDER_VAL;
	private long speed = Menu.INIT_SPEED_SLIDER_VAL;
	
	private SortingAlgorithm current_algorithm = SortingAlgorithm.NO_ALGORITHM;
	private double bar_width;
	private double bar_height[];
	
	private CustomSwingWorker<Void, Void> sortingWorker;
	
	// -1 means the variable is not yet initialized
	private int current_bar_index		= -1;
	private int traversing_bar_index	= -1;
	private int selected_bar_index		= -1;
	
	public SortingPanel(MainScreen mainScreen, Rectangle rect) {
		super(rect);
		this.mainScreen = mainScreen;
		
		initBarsDimension();
	}
	
	private void initBarsDimension() {
		bar_width = (double) CUSTOM_JPANEL_WIDTH / size;
		bar_height = new double[size];
		
		double height_interval = (double) CUSTOM_JPANEL_HEIGHT / size;

		for(int i = 0; i < size; i++)
			bar_height[i] = height_interval * (i + 1.0) - BAR_STROKE_THICKNESS;
		
		repaint();
	}
	
	public void randomizeBarHeight() {
		SwingWorker<Void, Void> worker = new SwingWorker<>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				final long random_tick_speed = 10; 
				int left = 0, right = size - 1;
				
				for(; left < size / 2; left++, right--) {
					int rand_index;
					double temp;
					
					//randomizes left -> center
					rand_index = (int) (Math.random() * size);
					temp = bar_height[left];
					bar_height[left] = bar_height[rand_index];
					bar_height[rand_index] = temp;
					
					//randomizes right -> center
					rand_index = (int) (Math.random() * size);
					temp = bar_height[right];
					bar_height[right] = bar_height[rand_index];
					bar_height[rand_index] = temp;
					
					repaint();
					Thread.sleep(random_tick_speed);
				}
				
				return null;
			}
			
			@Override
			protected void done() {
				super.done();
				mainScreen.doneProcess(MainScreen.PROCESS.SHUFFLE);
			}
		}; 
		worker.execute();		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		super.paintComponents(g);	// to paint CustomJComponents
		Graphics2D g2d = (Graphics2D) g;
		
		Stroke old = g2d.getStroke();
		g2d.setStroke(new BasicStroke(BAR_STROKE_THICKNESS));

		g2d.setColor(Color.CYAN);
		
		double x, y;
		for(int i = 0; i < size; i++) {
			x = i * bar_width;
			y = CUSTOM_JPANEL_HEIGHT - (BAR_STROKE_THICKNESS + bar_height[i]);
			
			Rectangle2D.Double rect = new Rectangle2D.Double(x, y, bar_width, bar_height[i]);
			g2d.fill(rect);
		}
		
		if(current_algorithm != SortingAlgorithm.NO_ALGORITHM)
			drawAlgorithm(g2d);
		
		g2d.setStroke(old);
		g2d.dispose();
	}
	
	private void drawAlgorithm(Graphics2D g2d) {
		double x, y;
		
		if((current_bar_index = current_algorithm.get_current_index()) != -1) {
			g2d.setColor(Color.GREEN);
			 
			x = current_bar_index * bar_width;
			y = CUSTOM_JPANEL_HEIGHT - (BAR_STROKE_THICKNESS + bar_height[current_bar_index]);
			
			Rectangle2D.Double curr_rect = new Rectangle2D.Double(x, y, bar_width, bar_height[current_bar_index]);
			g2d.fill(curr_rect);
		}
		
		if((traversing_bar_index = current_algorithm.get_traversing_index()) != -1) {	
			g2d.setColor(Color.RED);
			
			x = traversing_bar_index * bar_width;
			y = CUSTOM_JPANEL_HEIGHT - (BAR_STROKE_THICKNESS + bar_height[traversing_bar_index]);
			
			Rectangle2D.Double select_rect = new Rectangle2D.Double(x, y, bar_width, bar_height[traversing_bar_index]);
			g2d.fill(select_rect);
		}
		
		if((selected_bar_index = current_algorithm.get_selected_index()) != -1) {	
			g2d.setColor(Color.MAGENTA);
			 
			x = selected_bar_index * bar_width;
			y = CUSTOM_JPANEL_HEIGHT - (BAR_STROKE_THICKNESS + bar_height[selected_bar_index]);
			
			Rectangle2D.Double select_rect = new Rectangle2D.Double(x, y, bar_width, bar_height[selected_bar_index]);
			g2d.fill(select_rect);
		}
		
		// informations
		g2d.setColor(Color.WHITE);
		g2d.setFont(ALGORITHM_FONT);
		
		int string_y_padding = 20,
			string_x_padding = 200,
			string_tab		 = 20;
		
		// current algorithm
		x = 20;	y = 20;
		g2d.drawString("Current Algorithm : ", (int)x, (int)y);
		
		x += string_x_padding;
		String algorithm = current_algorithm.toString().replace("_", " ");
		g2d.drawString(algorithm, (int)x, (int)y);
		
		// array access
		x -= string_x_padding;
		y += string_y_padding;
		g2d.drawString("Array Access : ", (int)x, (int)y);
		
		x += string_x_padding;
		String array_access = String.valueOf(current_algorithm.get_array_access());
		g2d.drawString(array_access, (int)x, (int)y);
		
		// time complexity
		x -= string_x_padding;
		y += string_y_padding;
		g2d.drawString("Time Complexity :", (int)x, (int)y);
		
		// worst time complexity
		x += string_tab;
		y += string_y_padding;
		g2d.drawString("Worst Time Complexity : ", (int)x, (int)y);
		
		x += string_x_padding;
		String worst_time_complexity = current_algorithm.get_worst_time_complexity();
		g2d.drawString(worst_time_complexity, (int)x, (int)y);

		// average time complexity
		x -= string_x_padding;
		y += string_y_padding;
		g2d.drawString("Average Time Complexity : ", (int)x, (int)y);
		
		x += string_x_padding;
		String average_time_complexity = current_algorithm.get_average_time_complexity();
		g2d.drawString(average_time_complexity, (int)x, (int)y);
		
		// best time complexity
		x -= string_x_padding;
		y += string_y_padding;
		g2d.drawString("Best Time Complexity : ", (int)x, (int)y);
		
		x += string_x_padding;
		String best_time_complexity = current_algorithm.get_best_time_complexity();
		g2d.drawString(best_time_complexity, (int)x, (int)y);
		
		// space complexity
		y += string_y_padding;
		x -= (string_x_padding + string_tab);
		String space_complexity = "Space Complexity : ";
		g2d.drawString(space_complexity, (int)x, (int)y);
		
		// worst space complexity
		y += string_y_padding;
		x += string_tab;
		g2d.drawString("Worst Space Complexity : ", (int)x, (int)y);
		
		x += string_x_padding;
		String worst_space_complexity = current_algorithm.get_worst_space_complexity();
		g2d.drawString(worst_space_complexity, (int)x, (int)y);
	}
	
	public void sort() {
		sortingWorker = new CustomSwingWorker<>() {
			@Override
			protected Void doInBackground() {
				current_algorithm.performAlgorithm(bar_height, speed);
				
				return null;
			}

			@Override
			protected void done() {
				if(isCancelled())	return;
				
				super.done();
				mainScreen.doneProcess(MainScreen.PROCESS.SORT);
			}
		};
		sortingWorker.execute();
	}
	
	public void pause() {
		System.out.println("Process is paused...");
		current_algorithm.pause();
	}
	
	public synchronized void resume() {
		System.out.println("Continue...");
		current_algorithm.resume();
	}
	
	public void setBarSize(int size) {
		this.size = size;	
		initBarsDimension();
	}
	
	public void setSpeed(long speed) {
		this.speed = speed;
		if(current_algorithm != SortingAlgorithm.NO_ALGORITHM)	current_algorithm.set_speed(speed);
	}
	
	public void setAlgorithm(SortingAlgorithm algorithm) {
		this.current_algorithm = algorithm;
		if(algorithm != SortingAlgorithm.NO_ALGORITHM)	current_algorithm.set_sorting_panel(this);
		resetScreen();
	}
	
	public synchronized void resetScreen() {
		if(current_algorithm != SortingAlgorithm.NO_ALGORITHM)	current_algorithm.reset();
		current_bar_index		= -1;
		traversing_bar_index	= -1;
		selected_bar_index		= -1;
		
		/* This is to terminate undone CustomSwingWorker before
		 * executing another CustomSwingWorker. 
		 */
		if(sortingWorker != null) {
			sortingWorker.cancel(true);
		}			
	}
}