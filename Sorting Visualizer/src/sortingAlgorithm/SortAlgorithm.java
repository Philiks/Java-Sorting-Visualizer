package sortingAlgorithm;

import screen.SortingPanel;

// this is a framework for sorting algorithms
// this is different from SortingAlgorithm (enum)
abstract class SortAlgorithm {
	protected SortingPanel sorting_panel;
	
	protected int current_index		= -1;
	protected int traversing_index	= -1;
	protected int selected_index	= -1;
	
	// increments depending on the given unsorted array
	// not theoretical
	protected int array_access;
	protected volatile boolean isPaused = false;
	protected final long PAUSED_INTERVAL = 1000;
	protected double[] array;
	protected long speed = 0;
	
	/*
	 * the sort method will go here once the pause() is triggered.
	 * pause() method shouldn't call this because the method would 
	 * be the one that'll be interrupted.
	 */
	protected final synchronized void actionWhenPaused() {
		while(isPaused()) {
			System.out.println("waiting...");
			
			try {
				Thread.sleep(PAUSED_INTERVAL);
			} catch (InterruptedException e) {
				// interrupts the sleep without crashing the program
				System.out.println("Thread Closed");
			}
		}
	}
	
	protected final void swap(int a_index, int b_index) {
		double temp = array[a_index];
		array[a_index] = array[b_index];
		array[b_index] = temp;
		
		sorting_panel.repaint();
	}
	
	public final void pause() {	
		if(!isPaused())
			isPaused = true;
	}
	
	public final void resume() {	
		if(isPaused())
			isPaused = false;
	}
	
	public final void reset() {
		isPaused = false;
		current_index = -1;
		traversing_index = -1;
		selected_index = -1;
		speed = 0;
		array_access = 0;
	}
	
	public void sort(double[] array, long initial_speed) {
		this.array = array;
		speed = initial_speed;
	}
	
	public final void set_sorting_panel(SortingPanel sorting_panel) {	this.sorting_panel = sorting_panel;	}
	public final void set_speed(long speed) 	{	this.speed = speed;			}
	
	public final boolean isPaused() 			{	return isPaused;			}
	public final int get_array_access() 		{	return array_access;		}
	public final int get_current_index() 		{	return current_index;		}
	public final int get_traversing_index() 	{	return traversing_index;	}
	public final int get_selected_index()	 	{	return selected_index;		}
}