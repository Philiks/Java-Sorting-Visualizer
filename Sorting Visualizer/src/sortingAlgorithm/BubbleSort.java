package sortingAlgorithm;

public class BubbleSort extends SortAlgorithm {

	@Override
	public void sort(double[] array, long initial_speed) {
		super.sort(array, initial_speed);
		
		// this is an optimized Bubble Sort since the swapped flag is used
		boolean swapped;
		for(current_index = array.length - 1; current_index >= 0; current_index--) {
			swapped = false;
			for(traversing_index = 0, selected_index = traversing_index + 1;
					selected_index <= current_index;
						traversing_index++, selected_index++) {
				
				if(isPaused())	actionWhenPaused();
				
				try {
					array_access++;
					sorting_panel.repaint();
					Thread.sleep(speed);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				
				if(array[traversing_index] > array[selected_index]) {
					swapped = true;
					swap(traversing_index, selected_index);
				}
			}
			
			// the array is already sorted if there's no swapping occured
			if(swapped == false)	break;
		}
	}	
}