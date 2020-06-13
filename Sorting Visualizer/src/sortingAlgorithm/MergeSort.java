package sortingAlgorithm;

public class MergeSort extends SortAlgorithm {

	@Override
	public void sort(double[] array, long initial_speed) {
		super.sort(array, initial_speed);
		
		mergeSort(0, array.length - 1);
		
		traversing_index= -1;
		selected_index	= -1;
		sorting_panel.repaint();		
	}
	
	/***
	 * double[] array was defined int SortAlgorithm so there's 
	 * no need for it to be in the list of parameters.
	 * 
	 * @param start	defines where to start the lookup in the array
	 * @param end	defines where to end the lookup in the array
	 */
	private void mergeSort(int start, int end) {
		if(start == end) return;
		
		// add one since the last element is inclusive
		int lookup_length = end - start + 1;
		// start serves as offset
		int middle = ((lookup_length) / 2) + start;
		
		// subtract 1 to make middle exclusive on left and inclusive on right
		mergeSort(start, middle - 1);
		mergeSort(middle, end);
		
		current_index = start;
		merge(start, middle, end);
	}
	
	/***
	 * double[] array was defined int SortAlgorithm so there's 
	 * no need for it to be in the list of parameters.
	 * 
	 * The array is being created inside this method to avoid stacking resources on to the memory stack
	 * and start serves as the offset therefore, in order to copy the array, this method adds the value
	 * of start to get its value from the main array. Same logic was applied in calculation of the range
	 * of the lookup.
	 * 
	 * @param start	 defines where to start the lookup in the array
	 * @param middle defines where is the middle within the lookup range
	 * @param end	 defines where to end the lookup in the array
	 */
	private void merge(int start, int middle, int end) {
		int lookup_length = end - start + 1;
		double[] temp = new double[lookup_length];
		
		// copies elements from array to temp
		// note that temp = array should not be used since it will create a pointer
		// to the array therefore, changing temp would reflect on array (main)
		for(int i = 0; i < lookup_length; i++)
			temp[i] = array[start + i];
		
		// start serves as offset on the main array but not on temp
		int temp_middle = middle - start;
		int a_ptr = start;
		int l_ptr = 0;
		int r_ptr = temp_middle; 
		
		while(l_ptr < temp_middle && r_ptr < temp.length) {
			if(isPaused())	actionWhenPaused();
				
			array_access++;
			traversing_index= l_ptr + start;
			selected_index	= r_ptr + start;
			
			if(temp[l_ptr] < temp[r_ptr])
				array[a_ptr] = temp[l_ptr++];
			else
				array[a_ptr] = temp[r_ptr++];
			
			try {
				a_ptr++;
				sorting_panel.repaint();
				Thread.sleep(speed);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// copy remaining elements 
		while(l_ptr < temp_middle) {
			if(isPaused())	actionWhenPaused();
			
			try {
				traversing_index= l_ptr + start;
				sorting_panel.repaint();
				Thread.sleep(speed);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			array[a_ptr++] = temp[l_ptr++];
		}
		
		while(r_ptr < temp.length) {
			if(isPaused())	actionWhenPaused();
			
			try {
				selected_index	= r_ptr + start;
				sorting_panel.repaint();
				Thread.sleep(speed);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			array[a_ptr++] = temp[r_ptr++];
		}		
	}
}