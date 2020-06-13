package sortingAlgorithm;

public class BogoSort extends SortAlgorithm {
	@Override
	public void sort(double[] array, long initial_speed) {
		super.sort(array, initial_speed);
		
		while(!isSorted()) {
			if(isPaused())	actionWhenPaused();
			
			bogoSort();
			
			try {
				current_index	= (int)(Math.random() * array.length);
				traversing_index= (int)(Math.random() * array.length);
				selected_index	= (int)(Math.random() * array.length);
				sorting_panel.repaint();
				Thread.sleep(speed);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void bogoSort() {
		for(int i = 0; i < array.length; i++) {
			int rand_index = (int)(Math.random() * array.length);
			double temp = array[i];
			array[i] = array[rand_index];
			array[rand_index] = temp;
		}
	}
	
	private boolean isSorted() {
		for(int i = 0; i < array.length - 1; i++) {
			array_access++;
			if(array[i] > array[i + 1])
				return false;
		}
		
		return true;
	}
}