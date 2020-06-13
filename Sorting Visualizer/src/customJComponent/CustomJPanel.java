package customJComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;

/**
 * CustomJPanel
 * @author philiks
 * Just like an ordinary JPanel, CustomJPanel component 
 * provides an area as a container of various components
 * (supports both native Swing and Custom components). 
 * 
 * CustomJPanel was created to serve as a modified version 
 * of JPanel, its purpose is to provide funtionalities
 * (same of JPanel) with some modifications to suit the needs
 * of the project, Sorting Visualizer. Such modifications are: 
 * constructors, Look and Feel, and more default values to its
 * properties.
 * 
 * The rectangular bounds specifies the location
 * and dimension of the CustomJPanel. 
 */
public class CustomJPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	/***
	 * CUSTOM_JPANEL_WIDTH and CUSTOM_JPANEL_HEIGHT sets the
	 * PreferredSize of the CustomJPanel. CUSTOM_JPANEL_WIDTH
	 * and CUSTOM_JPANEL_HEIGHT overrules the dimension that is
	 * set by Swing using @see setSize() by overriding
	 * @see getPreferredSize(). Therefore CustomJPanel
	 * always use the dimension in rectangular bounds.
	 * CustomJPanel's dimension can be changed by 
	 * calling the @see setBounds() method.
	 */
	protected int CUSTOM_JPANEL_WIDTH;
	protected int CUSTOM_JPANEL_HEIGHT;
	
	/***
	 * Creates a CustomJPanel instance with the specified
	 * rectangular bounds.
	 * The CustomJPanel has a null layout.
	 * 
	 * @param rect		The rectangular bounds that defines the 
	 * 					CustomJPanel's location and dimension.
	 * @param action	The action that will take place when
	 * 					the CustomJPanel is triggered.
	 * @param icon		The image to be displayed by the CustomJPanel.
	 * @param text		The text to be displayed by the CustomJPanel.
	 */
	public CustomJPanel(Rectangle rect) {
		CUSTOM_JPANEL_WIDTH  = rect.width;
		CUSTOM_JPANEL_HEIGHT = rect.height;
		
		setBounds(rect);
		setLayout(null);
		setBackground(Color.BLACK);
	}
	
	/***
	 * Creates a CustomJPanel instance with the specified dimension.
	 * The CustomJPanel's location will be at (0,0) (x,y) coordinate.
	 * 
	 * @param dim	The Dimension of the CustomJPanel.
	 */
	public CustomJPanel(Dimension dim) {
		this(new Rectangle(0, 0, dim.width, dim.height));
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(CUSTOM_JPANEL_WIDTH, CUSTOM_JPANEL_HEIGHT);
	}
}