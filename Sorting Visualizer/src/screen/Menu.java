package screen;

import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import customJComponent.CustomJButton;
import customJComponent.CustomJLabel;
import customJComponent.CustomJPanel;
import customJComponent.CustomJSlider;

public class Menu extends CustomJPanel {
	private static final long serialVersionUID = 1L;
	private MainScreen mainScreen;
	private CustomJButton burger, sort, stop, shuffle;
	private CustomJLabel title, sliderName, sliderValue;
	private CustomJSlider sizeSlider, speedSlider;
	
	private Timer sliderAnimationTimer;
	
	private final String size_slider_name = "SIZE", speed_slider_name = "SPEED";
	private String current_slider;
	
	public static final int MIN_SIZE_SLIDER_VAL = 10,
				MAX_SIZE_SLIDER_VAL = 500, 
				INIT_SIZE_SLIDER_VAL = 250,
				SIZE_SLIDER_MINOR_TICK = 10,
				SIZE_SLIDER_MAJOR_TICK = 100;

	public static final int MIN_SPEED_SLIDER_VAL = 10, 
				MAX_SPEED_SLIDER_VAL = 1000, 
				INIT_SPEED_SLIDER_VAL = 500,
				SPEED_SLIDER_MINOR_TICK = 10,
				SPEED_SLIDER_MAJOR_TICK = 250;
	
	private enum COMPONENT  {
		//enable | disable
		BURGER, SORT, SHUFFLE, SLIDER,
		//visible | invisible
		SORT_VISIBILITY, STOP_VISIBILITY, SIZE_SLIDER_VISIBILITY; 	
	}
	
	/*
	 *  Identifies whether sort or resume when sort 
	 *  button is triggered.
	 *  First click triggers the sort but the following
	 *  clicks triggers the resume (that means the sorting
	 *  was just interrupted by a pause.
	 *  This goes back to false when the sortingPanel is
	 *  reset.
	 */
	private boolean wasPaused = false;
	
	public Menu(MainScreen mainScreen, Rectangle rect) {
		super(rect);
		this.mainScreen = mainScreen;
		
		initComponent();
	}
	
	private void initComponent() {
		// 1/6 padding in all sides giving 2/3 of dimension for its content
		final int PADDING = CUSTOM_JPANEL_HEIGHT * 1 / 6;
		final int 	FIXED_HEIGHT = CUSTOM_JPANEL_HEIGHT - (2 * PADDING), 
					FIXED_WIDTH = FIXED_HEIGHT,
					FIXED_Y = CUSTOM_JPANEL_HEIGHT / 2 - FIXED_HEIGHT / 2;
		final Font SLIDER_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 13);
		
		// width is important for identifying the dimension (square) of icons
		ImageIcon icons[] = setUpIcon(FIXED_HEIGHT);
		final int 	BURGER_ENABLED_INDEX = 0, 	BURGER_DISABLED_INDEX = 1,
					SORT_ENABLED_INDEX = 2, 	SORT_DISABLED_INDEX = 3,
					SHUFFLE_ENABLED_INDEX = 4, 	SHUFFLE_DISABLED_INDEX = 5,
					STOP_ENABLED_INDEX = 6;
		
		final String burger_tooltip = "ALGORITHM MENU",	no_algorithm_tooltip = "PICK AN ALGORITHM FIRST :)",
					shuffle_tooltip = "SHUFFLE",		stop_tooltip = "STOP";				
		
		// ==== BUTTON ====
		AbstractAction burger_action = new AbstractAction("burger") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				mainScreen.startProcess(MainScreen.PROCESS.SIDEPANEL_ANIMATION);
				if(wasPaused)
					resetSorting();
				mainScreen.repaint();
			}
		};
		
		burger 	=	new CustomJButton(new Rectangle(PADDING, 
											FIXED_Y, 
											FIXED_WIDTH, FIXED_HEIGHT),
								burger_action,
								icons[BURGER_ENABLED_INDEX],
								icons[BURGER_DISABLED_INDEX],
								burger_tooltip);
		
		AbstractAction sort_action = new AbstractAction("sort") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!mainScreen.hasChosenAnAlgorithm())	{
					sort.setToolTipText("Please choose an algorithm first :)");
					sort.createToolTip().setVisible(true);
					return;
				} else {
					sort.setToolTipText("");
				}
				
				if(wasPaused) {
					mainScreen.startProcess(MainScreen.PROCESS.RESUME);
				}else {
					resetSorting();
					mainScreen.startProcess(MainScreen.PROCESS.SORT);
				}
				
				if(mainScreen.isSidePanelShown())	mainScreen.startProcess(MainScreen.PROCESS.SIDEPANEL_ANIMATION);
				
				changeComponentState(COMPONENT.BURGER, COMPONENT.SHUFFLE, COMPONENT.SLIDER, 
									COMPONENT.SORT_VISIBILITY, COMPONENT.STOP_VISIBILITY);
			}
		};
		
		sort 	= 	new CustomJButton(new Rectangle(burger.getX() + burger.getWidth() + PADDING, 
											FIXED_Y, 
											FIXED_WIDTH, 
											FIXED_HEIGHT),
								sort_action,
								icons[SORT_ENABLED_INDEX],
								icons[SORT_DISABLED_INDEX],
								no_algorithm_tooltip);
		
		AbstractAction stop_action = new AbstractAction("stop") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				wasPaused = true;	//remains true until sortingPanel was reset
				
				changeComponentState(COMPONENT.BURGER, COMPONENT.SHUFFLE, COMPONENT.SLIDER, 
									COMPONENT.SORT_VISIBILITY, COMPONENT.STOP_VISIBILITY);
				
				mainScreen.startProcess(MainScreen.PROCESS.PAUSE);
			}
		};
		
		stop 	= 	new CustomJButton(new Rectangle(burger.getX() + burger.getWidth() + PADDING, 
											FIXED_Y, 
											FIXED_WIDTH, 
											FIXED_HEIGHT),
								stop_action,
								icons[STOP_ENABLED_INDEX],
								stop_tooltip);
		
		AbstractAction shuffle_action = new AbstractAction("shuffle") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainScreen.isSidePanelShown())	mainScreen.startProcess(MainScreen.PROCESS.SIDEPANEL_ANIMATION);
				
				changeComponentState(COMPONENT.BURGER, COMPONENT.SORT, COMPONENT.SHUFFLE, 
									COMPONENT.SIZE_SLIDER_VISIBILITY);
				mainScreen.startProcess(MainScreen.PROCESS.SHUFFLE);
			}
		};
		
		shuffle = 	new CustomJButton(new Rectangle(sort.getX() + sort.getWidth() + PADDING, 
											FIXED_Y, 
											FIXED_WIDTH, 
											FIXED_HEIGHT),
								shuffle_action,
								icons[SHUFFLE_ENABLED_INDEX],
								icons[SHUFFLE_DISABLED_INDEX],
								shuffle_tooltip);

		// ==== SLIDER ====
		current_slider = size_slider_name;
		sliderName	=	new CustomJLabel(new Rectangle(shuffle.getX() + shuffle.getWidth() + PADDING, 
											FIXED_Y, 
											FIXED_WIDTH * 5 / 4,	// increased width to see "SPEED"
											FIXED_HEIGHT), current_slider, SLIDER_FONT);
		
		UIManager.put("Slider.paintValue", false);

		sizeSlider	= 	new CustomJSlider( 
								new Rectangle(sliderName.getX() + sliderName.getWidth() + PADDING, 
											FIXED_Y, 
											FIXED_WIDTH * 2,
											FIXED_HEIGHT),
								new DefaultBoundedRangeModel(INIT_SIZE_SLIDER_VAL, 0, 
											MIN_SIZE_SLIDER_VAL, MAX_SIZE_SLIDER_VAL),
								SIZE_SLIDER_MINOR_TICK, SIZE_SLIDER_MAJOR_TICK);
		sizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(mainScreen.isSidePanelShown())	mainScreen.startProcess(MainScreen.PROCESS.SIDEPANEL_ANIMATION);
				
				resetSorting();
				int size_value = ((CustomJSlider)e.getSource()).getValue();
				mainScreen.changeValue(size_slider_name, size_value);
				sliderValue.setText(String.valueOf(size_value));
			}
		});
		sizeSlider.addMouseListener(new MouseListener() {
			@Override
			public void mouseEntered(MouseEvent e) {
				sliderAnimationTimer.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sliderAnimationTimer.start();
			}

			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		
		speedSlider	= 	new CustomJSlider(sizeSlider.getBounds(),
								new DefaultBoundedRangeModel(INIT_SPEED_SLIDER_VAL, 0, 
											MIN_SPEED_SLIDER_VAL, MAX_SPEED_SLIDER_VAL),
								SPEED_SLIDER_MINOR_TICK, SPEED_SLIDER_MAJOR_TICK);
		speedSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int speed_value = ((CustomJSlider)e.getSource()).getValue();
				mainScreen.changeValue(speed_slider_name, speed_value);
				sliderValue.setText(String.valueOf(speed_value) + "ms");
			}
		});
		speedSlider.addMouseListener(new MouseListener() {
			@Override
			public void mouseEntered(MouseEvent e) {
				sliderAnimationTimer.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sliderAnimationTimer.start();
			}

			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		
		sliderValue	=	new CustomJLabel(new Rectangle(sizeSlider.getX(),
											FIXED_Y,
											FIXED_WIDTH * 3 / 2,	// increased width to see "x.xx ms"
											FIXED_HEIGHT), 
								String.valueOf(sizeSlider.getValue()), SLIDER_FONT);
		
		// ==== TITLE ====
		// x depends on the current width of CustomJSlider
		String titleAndDev = "<html><span style = \"font-weight: bold; color: #DDDDDD; font-size: 25px;\">Sorting Visualizer</span>"
				   + "<span style = \"font-weight: bold; color: #AAAAAA; font-size: 10px\"> a program of Felix L. Janopol Jr.</span></html>";
		title 	= 	new CustomJLabel(new Rectangle(sizeSlider.getX() + sizeSlider.getStartOfCoveredRegion(), 
											FIXED_Y, 
											CUSTOM_JPANEL_WIDTH - (sizeSlider.getX() + sizeSlider.getWidth() + PADDING), 
											FIXED_HEIGHT), titleAndDev);
		
		// initial state of components
		sort.setEnabled(false);			// there's no algorithm yet
		stop.setVisible(false);			// sort is currently shown
		speedSlider.setVisible(false);	// size slider is currently shown
		
		initAnimationTimer();
		
		add(burger);
		add(sort);
		add(shuffle);
		add(stop);
		add(sliderName);
		add(sliderValue);
		add(sizeSlider);	add(speedSlider);
		add(title);
	}
	
	private void initAnimationTimer() {
		sliderAnimationTimer = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int startOfCoveredRegion = sizeSlider.isVisible() ? 
						sizeSlider.getStartOfCoveredRegion()
						: speedSlider.getStartOfCoveredRegion();
				
				// sizeSlider and speedSlider have the same X coordinate relative to Menu panel.
				// Must add the X coordinate since the startOfCoveredRegion is relative to the 
				// slider's X coordinate and not on the panel.
				startOfCoveredRegion += sizeSlider.getX();
				
				boolean animationRunning = sizeSlider.isVisible() ? 
						sizeSlider.isAnimationRunning()
						: speedSlider.isAnimationRunning();
				
				if(animationRunning) {
					sliderValue.setLocation(startOfCoveredRegion, sliderValue.getY());
					title.setLocation(sliderValue.getX() + sliderValue.getWidth(), title.getY());
				}else
					sliderAnimationTimer.stop();
			}
		});
	}
	
	/***
	 * @param dim dimension of the icons when scaled.
	 */
	private ImageIcon[] setUpIcon(int dim) {
		String paths[] = 	{	"/burger-icon-enabled.png",	"/burger-icon-disabled.png",
								"/sort-icon-enabled.png",	"/sort-icon-disabled.png",
								"/shuffle-icon-enabled.png","/shuffle-icon-disabled.png",
								"/stop-icon-enabled.png"
							};
		
		ImageIcon icons[] = new ImageIcon[paths.length];
		
		// only occupy the 2 / 3 of the button 
		final int ICON_WIDTH = dim * 2 / 3, 
				  ICON_HEIGHT = ICON_WIDTH;
		
		for(int i = 0; i < icons.length; i++) {
			try {
				Image image = ImageIO.read(getClass()
									 .getResource(paths[i]))
									 .getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
				icons[i] = new ImageIcon(image);
			} catch (IOException e) {
				System.out.println("Failed to get Image File");
				e.printStackTrace();
			}
		}
		
		return icons;
	}
	
	private void changeComponentState(COMPONENT...components) {
		for(COMPONENT component : components) {
			switch(component) {
			case BURGER:
				burger.setEnabled(!burger.isEnabled());
				break;
			case SORT:
				// sort will remain disabled until there's an algorithm chosen
				// therefore, any attempt to change it (i.e. via shuffle),
				// sort will remain disabled
				if(!mainScreen.hasChosenAnAlgorithm()) sort.setEnabled(false);
				else	sort.setEnabled(!sort.isEnabled());
				break;
			case SORT_VISIBILITY:
				sort.setVisible(!sort.isVisible());
				break;
			case STOP_VISIBILITY:
				stop.setVisible(!stop.isVisible());
				break;
			case SHUFFLE:
				shuffle.setEnabled(!shuffle.isEnabled());
				break;
			case SLIDER:
				sizeSlider.setVisible(!sizeSlider.isVisible());
				speedSlider.setVisible(!speedSlider.isVisible());
				
				current_slider = current_slider == size_slider_name ? 
								speed_slider_name 
								: size_slider_name;
				sliderName.setText(current_slider);
				
				int value = current_slider == size_slider_name ? 
						sizeSlider.getValue() 
						: speedSlider.getValue();
				
				String strValue = current_slider == size_slider_name ? 
						String.valueOf(value) 
						: String.valueOf(value) + "ms";						
				sliderValue.setText(strValue);
				break;
			case SIZE_SLIDER_VISIBILITY:
				sizeSlider.setVisible(!sizeSlider.isVisible());
				break;
			default:
				System.out.println("Undefined Component");
				break;
			}
		}
	}
	
	public void donePicking() {
		sort.setEnabled(true);
		sort.setToolTipText("SORT");
	}
	
	public void doneShuffling() {
		changeComponentState(COMPONENT.BURGER, COMPONENT.SORT, COMPONENT.SHUFFLE, 
							COMPONENT.SIZE_SLIDER_VISIBILITY);
		resetSorting();
	}
	
	public void doneSorting() {
		changeComponentState(COMPONENT.BURGER, COMPONENT.SHUFFLE, COMPONENT.SLIDER, 
							COMPONENT.SORT_VISIBILITY, COMPONENT.STOP_VISIBILITY);
		resetSorting();
	}
	
	public void resetSorting() {
		wasPaused = false;
		mainScreen.startProcess(MainScreen.PROCESS.RESET);
	}
}