package sortingAlgorithm;

import screen.SortingPanel;

public enum SortingAlgorithm {
	/*
		OMEGA	= '\u03a9',
		THETA	= '\u0398',
		OMICRON	= '\u039f';
	*/
	NO_ALGORITHM(),
	INSERTION_SORT	(new InsertionSort(),	new String[] {"\u03a9(n)",			"\u0398(n^2)",		"\u039f(n^2)"},		"\u039f(1)"),
	SELECTION_SORT	(new SelectionSort(),	new String[] {"\u03a9(n^2)",		"\u0398(n^2)",		"\u039f(n^2)"},		"\u039f(1)"),
	BUBBLE_SORT		(new BubbleSort(),		new String[] {"\u03a9(n)",			"\u0398(n^2)",		"\u039f(n^2)"},		"\u039f(1)"),
	MERGE_SORT		(new MergeSort(),		new String[] {"\u03a9(n log(n))",	"\u0398(n log(n))",	"\u039f(n log(n))"},"\u039f(n)"),
	QUICK_SORT		(new QuickSort(),		new String[] {"\u03a9(n log(n))",	"\u0398(n log(n))",	"\u039f(n^2)"},		"\u039f(log(n))"),
	BOGO_SORT		(new BogoSort(),		new String[] {"\u03a9(n)",			"\u0398((n+1)!)",	"\u039f((n+1)!)"},	"\u039f(n)");
	
	private final int NUM_OF_COMPLEXITY = 3;
	// could use enum but the use of ENUM_NAME.FIELD.ORDINAL() is quite long
	private final int BEST = 0, AVERAGE = 1, WORST = 2;
	
	private String[] time_complexity = new String[NUM_OF_COMPLEXITY]; 
	private String worst_space_complexity;
	private SortAlgorithm algorithm;
	
	// NO_ALGORITHM
	SortingAlgorithm(){
	}
	
	SortingAlgorithm(SortAlgorithm algorithm, String[] time_complexity, String worst_space_complexity) {
		this.algorithm = algorithm;
		
		//copies the time_complexity of the sorting algorithm
		for(int i = 0; i < time_complexity.length; i++)
			this.time_complexity[i] = time_complexity[i];
		
		this.worst_space_complexity = worst_space_complexity;
	}
	
	/*
	 * Requires a separate class in performing algorithm
	 * because some algorithms requires the same data structures
	 * with different specifications. And also to prevent this enum to be bulky. 
	 */
	public void performAlgorithm(double[] array, long initial_speed) { 
		algorithm.sort(array, initial_speed);
	}
	
	public String get_worst_space_complexity()	{	return worst_space_complexity;				}
	public String get_best_time_complexity()	{	return time_complexity[BEST];				}
	public String get_average_time_complexity()	{	return time_complexity[AVERAGE];			}
	public String get_worst_time_complexity()	{	return time_complexity[WORST];				}
	
	public int get_current_index() 				{	return algorithm.get_current_index();		}
	public int get_traversing_index() 			{	return algorithm.get_traversing_index();	}
	public int get_selected_index() 			{	return algorithm.get_selected_index();		}
	public int get_array_access() 				{	return algorithm.get_array_access();		}
	public boolean isPaused()					{	return algorithm.isPaused();				}
	
	public void pause()							{	algorithm.pause();				}
	public void resume()						{	algorithm.resume();				}
	public void reset()							{	algorithm.reset();				}
	public void set_speed(long speed)			{	algorithm.set_speed(speed);		}
	public void set_sorting_panel(SortingPanel sorting_panel) {	algorithm.set_sorting_panel(sorting_panel);	}
	
	public static SortingAlgorithm getAlgorithmViaText(String text) {
		switch(text) {
		case "INSERTION SORT":
			return INSERTION_SORT;
		case "SELECTION SORT":
			return SELECTION_SORT;
		case "BUBBLE SORT":
			return BUBBLE_SORT;
		case "MERGE SORT":
			return MERGE_SORT;
		case "QUICK SORT":
			return QUICK_SORT;
		case "BOGO SORT":
			return BOGO_SORT;
		default:
			return NO_ALGORITHM;
		}
	}
}