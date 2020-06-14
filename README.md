# Java-Sorting-Visualizer
Java Sorting Visualizer using *Swing Components | Swing Worker | Graphics 2D

## Sorting Algorithms :
* Insertion Sort
* Selection Sort
* Bubble Sort
* Merge Sort
* Quick Sort
* Bogo Sort

## Main Libraries Used :
* Swing Worker
* Swing Components
* Graphics 2D

## Skills Learned :
* Multi-Threading using Swing Worker
* Customising Swing Components
* Graphics 2D
* Apply Sorting Algorithms

## Program Structure :
### main
* contains *MainApp* which sets up the JFrame.
### screen
* constains 4 *CustomJPanels*
* *Menu* has the control buttons such as Pause, Sort, and Shuffle.
* *SidePanel* has the different sorting algorithms and its task is to set the `current_algorithm`.
* *SortingPanel* is the *CustomJPanel* that handles the sorting and painting it on the Component.
* *MainScreen* is the `contentPane` of *MainApp* and it holds the 3 *CustomJPanel*.
### sortingAlgorithm
* contains all the sorting algorithm used
* *SortAlgorithm* is the Parent Class of the sorting algorithms. It has all the methods that the sorting algorithm needs.
* *SortingAlgorithm* is an `enum` that controls the request from other panels. It also handles methods that are being called in the `current_algorithm`.
### customJComponent
* JComponents that have various constructors to meet the needs of the program.

There are three main indeces here : `current_index`, `traversing_index`, and `selected_index`. Their task is to visually guide the user on the current state of the sorting process. Their uses varies from each Sorting Algorithms.
The information displayed such as Time Complexities and Space Complexity are being set up in the *SortingAlgorithm* enum.
Lastly, the only dynamic value on the HUD is the `array_access` which is being updated during the sorting process.

Here's my email : codeitphiliks@gmail.com if you have found a bug or if you have any queries regarding this project.
