package customJComponent;

import javax.swing.SwingWorker;

/**
 * CustomSwingWorker
 * @author philiks
 * Just like an ordinary SwingWorker, CustomSwingWorker  
 * provides a thread to execute certain tasks. 
 * 
 * CustomSwingWorker was created to serve as a modified version 
 * of SwingWorker, its purpose is to provide funtionalities
 * (same of SwingWorker) with one specific modifications to 
 * suit the needs of the project, Sorting Visualizer. This 
 * modification is its ability to be paused, resume and 
 * terminate (termination of SwingWorker already exists but 
 * overridden for this purpose).
 */
public abstract class CustomSwingWorker<K, V> extends SwingWorker<K, V> {
	private volatile boolean isPaused;
	
	public final void pause() {
		if(!isPaused() && !isDone()) {
			isPaused = true;
			firePropertyChange("paused", false, true);
		}
	}
	
	public final void resume() {
		if(isPaused() && !isDone()) {
			isPaused = false;
			firePropertyChange("paused", true, false);
		}
	}
	
	public final boolean isPaused() {
		return isPaused;
	}
}