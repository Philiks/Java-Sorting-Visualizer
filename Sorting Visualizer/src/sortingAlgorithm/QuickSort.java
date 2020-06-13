package sortingAlgorithm;

public class QuickSort extends SortAlgorithm {

	@Override
	public void sort(double[] array, long initial_speed) {
		super.sort(array, initial_speed);
		
		int left = 0;
		int right = array.length - 1;
		int middle = (left + right) / 2;
		int pivot = medianOfThree(left, middle, right);
		quickSort(pivot, left, right);
		
		traversing_index = -1;	// reset traversing index
		current_index = -1;		// reset current index
	}
	
	/***
	 * @param pivot	result of medianOfThree method
	 * @param start	start pointer of the lookup array
	 * @param end	end pointer of the lookup array
	 */
	private void quickSort(int pivot, int start, int end) {
		if(start >= end)	return;
		
		int selected_index = start - 1;
		swap(pivot, end);
		pivot = end;
		
		current_index = pivot;
		for(traversing_index = start; traversing_index < pivot; traversing_index++) {
			if(isPaused())	actionWhenPaused();
				
			if(array[traversing_index] < array[pivot])
				swap(++selected_index, traversing_index);
			
			try {
				array_access++;
				sorting_panel.repaint();
				Thread.sleep(speed);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		int new_pivot_pos = selected_index + 1;
		swap(new_pivot_pos, pivot);
		
		// quick sort the left side
		int less_middle = (start + selected_index) / 2;
		int less_pivot = medianOfThree(start, less_middle, selected_index);
		quickSort(less_pivot, start, selected_index);
		
		// quick sort the right side
		int greater_start = new_pivot_pos + 1;
		int greater_middle = (greater_start + end) / 2;
		int greater_pivot = medianOfThree(greater_start, greater_middle, end);
		quickSort(greater_pivot, greater_start, end);
	}
	
	/***
	 * @param a start of array
	 * @param b middle of array
	 * @param c end of array
	 * @return index of median of three
	 */
	private int medianOfThree(int a, int b, int c) {
		double left		= array[a];
		double middle	= array[b];
		double right	= array[c];
		
		if((left > middle) != (left > right))
			return a;
		else if((middle > left) != (middle > right))	
			return b;
		else
			return c;
	}
}