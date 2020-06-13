package screen;

import java.awt.Dimension;
import java.awt.Rectangle;

import customJComponent.CustomJPanel;
import sortingAlgorithm.SortingAlgorithm;

public class MainScreen extends CustomJPanel {
	private static final long serialVersionUID = 1L;
	
	private SortingAlgorithm selected_algorithm = SortingAlgorithm.NO_ALGORITHM;
	private Menu menuScreen;
	private SortingPanel sortingScreen;
	private SidePanel sidePanel;
	
	public static enum PROCESS {
		SORT, PAUSE, RESUME, SHUFFLE, RESET, SIDEPANEL_ANIMATION;
	}
	
	public MainScreen(Dimension dim) {
		super(dim);
		createAndDisplayGUI();
	}
	
	private void createAndDisplayGUI() {
		menuScreen = new Menu(this, new Rectangle(0, 0, 
									  			this.getBounds().width, 
									  			this.getBounds().height / 10));
		
		sortingScreen = new SortingPanel(this, new Rectangle(0, 
												menuScreen.getY() + menuScreen.getHeight(), 
												this.getBounds().width, 
												this.getBounds().height - menuScreen.getHeight()));
		
		sidePanel = new SidePanel(this, new Rectangle(-1 * this.getBounds().width / 4, 
												sortingScreen.getY(), 
												this.getBounds().width / 4, 
												sortingScreen.getHeight()));
		
		add(menuScreen);
		add(sidePanel);
		add(sortingScreen);
	}
	
	public void startProcess(PROCESS process) {
		switch(process) {
		case SORT:
			sortingScreen.sort();
			break;
		case PAUSE:
			sortingScreen.pause();
			break;
		case RESUME:
			sortingScreen.resume();
			break;
		case SHUFFLE:
			sortingScreen.randomizeBarHeight();
			sortingScreen.setAlgorithm(selected_algorithm);
			break;
		case RESET:
			sortingScreen.resetScreen();
			break;
		case SIDEPANEL_ANIMATION:
			sidePanel.startSidePanelAnimation();
			break;
		default:
			System.out.println("Process Start Request Error : " + 
								process + " Undefined Process");
			break;
		}
	}
	
	public void doneProcess(PROCESS process) {
		switch(process) {
		case SORT:
			menuScreen.doneSorting();
			break;
		case SHUFFLE:
			menuScreen.doneShuffling();
			break;
		default:
			System.out.println("Process Done Flag Error : " + 
								process + " Undefined Process");
			break;
		}
	}

	public void changeValue(String name, int value) {
		switch(name) {
		case "SIZE":
			sortingScreen.setBarSize(value);
			break;
		case "SPEED":
			sortingScreen.setSpeed((long)value);
			break;
		default:
			System.out.println("Change Value Error : " + 
								name + " Undefined variable");
			break;
		}
	}
	
	public void setAlgorithm(SortingAlgorithm algorithm) {
		this.selected_algorithm = algorithm;
		sortingScreen.setAlgorithm(algorithm);
		menuScreen.donePicking();
	}
	
	public boolean isSidePanelShown() {
		return sidePanel.isShown();
	}
	
	public boolean hasChosenAnAlgorithm() {
		return selected_algorithm != SortingAlgorithm.NO_ALGORITHM;
	}
}