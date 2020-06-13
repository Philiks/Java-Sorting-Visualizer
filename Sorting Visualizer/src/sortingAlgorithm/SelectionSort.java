package sortingAlgorithm;

public class SelectionSort extends SortAlgorithm {

	@Override
	public void sort(double[] array, long initial_speed) {
		super.sort(array, initial_speed);

		for(current_index = 0; current_index < array.length - 1; current_index++) {
			selected_index = current_index;	// holds the index of lowest value
			
			for(traversing_index = current_index; traversing_index < array.length; traversing_index++) {
				if(isPaused())	actionWhenPaused();
				
				try {
					array_access++;
					sorting_panel.repaint();
					Thread.sleep(speed);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				
				if(array[traversing_index] < array[selected_index])
					selected_index = traversing_index;
			}
			
			traversing_index = -1;	// resets traversing_index
			swap(current_index, selected_index);
		}

		current_index = -1;	// resets current_index
	}
}