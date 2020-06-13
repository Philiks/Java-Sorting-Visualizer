package sortingAlgorithm;

public class InsertionSort extends SortAlgorithm {
	@Override
	public void sort(double[] array, long initial_speed) {
		super.sort(array, initial_speed);
		
		for(current_index = 1; current_index < array.length; current_index++) 
			for(traversing_index = current_index, selected_index = traversing_index - 1; 
						selected_index >= 0 && array[selected_index] > array[traversing_index]; 
							traversing_index--, selected_index--) {
				
				if(isPaused())	actionWhenPaused();
				
				try {
					array_access++;
					sorting_panel.repaint();
					Thread.sleep(speed);
					swap(traversing_index, selected_index);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
		current_index = -1;	// resets current_index
	}
}