package customJComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * CustomJLabel
 * @author philiks
 * Just like an ordinary JLabel, CustomJLabel component 
 * provides an area for short text strings, an image, 
 * or both.
 * 
 * CustomJLabel was created to serve as a modified version 
 * of JLabel, its purpose is to provide funtionalities
 * (same of JLabel) with some modifications to suit the needs
 * of the project, Sorting Visualizer. Such modifications are: 
 * constructors, Look and Feel, and more default values to its
 * properties.
 * 
 * Warning:
 * The rectangular bounds is a required argument in
 * CustomJLabel because the Container @see CustomJPanel 
 * that it will be placed has a null layout. Therefore, 
 * specifying its rectangular bounds defines the location
 * and dimension of the CustomJLabel.
 */
public class CustomJLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	
	/***
	 * CUSTOM_JLABEL_WIDTH and CUSTOM_JLABEL_HEIGHT sets the
	 * PreferredSize of the CustomJLabel. CUSTOM_JLABEL_WIDTH
	 * and CUSTOM_JLABEL_HEIGHT overrules the dimension that is
	 * set by Swing using @see setSize() by overriding
	 * @see getPreferredSize(). Therefore CustomJLabel
	 * always use the dimension in rectangular bounds.
	 * CustomJLabel's dimension can be changed by 
	 * calling the @see setBounds() method.
	 */
	protected int CUSTOM_JLABEL_WIDTH;
	protected int CUSTOM_JLABEL_HEIGHT;
	
	/***
	 * Creates a CustomJLabel instance with the specified
	 * rectangular bounds, image, text.
	 * The CustomJLabel is centered horizontally and vertically
	 * in its display area.
	 * The image displays first following by a trailing text.
	 *  
	 * @param rect	The rectangular bounds that defines the 
	 * 				CustomJLabel's location and dimension.
	 * @param icon	The image to be displayed by the CustomJLabel.
	 * @param text	The text to be displayed by the CustomJLabel.
	 */
	public CustomJLabel(Rectangle rect, ImageIcon icon, String text) {
		this.CUSTOM_JLABEL_WIDTH  = rect.width;
		this.CUSTOM_JLABEL_HEIGHT = rect.height;
		
		setBounds(rect);
		setIcon(icon);
		setText(text);
		
		setOpaque(false);
		setForeground(Color.WHITE);
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
	}
	
	/***
	 * Creates a CustomJLabel instance with the specified
	 * rectangular bounds and image.
	 * The CustomJLabel is centered horizontally and vertically
	 * in its display area.
	 * 
	 * @param rect	The rectangular bounds that defines the 
	 * 				CustomJLabel's location and dimension.
	 * @param icon	The image to be displayed by the CustomJLabel.
	 */
	public CustomJLabel(Rectangle rect, ImageIcon icon) {
		this(rect, icon, "");
	}
	
	/***
	 * Creates a CustomJLabel instance with the specified
	 * rectangular bounds, text, and its font.
	 * The CustomJLabel is centered horizontally and vertically
	 * in its display area.
	 *  
	 * @param rect	The rectangular bounds that defines the 
	 * 				CustomJLabel's location and dimension.
	 * @param text	The text to be displayed by the CustomJLabel.
	 * @param font	The font that formats the text to be displayed 
	 * 				by the CustomJLabel.
	 */
	public CustomJLabel(Rectangle rect, String text, Font font) {
		this(rect, null, text);
		setFont(font);
	}
	
	/***
	 * Creates a CustomJLabel instance with the specified
	 * rectangular bounds and text.
	 * The CustomJLabel is centered horizontally and vertically
	 * in its display area.
	 * 
	 * @param rect	The rectangular bounds that defines the 
	 * 				CustomJLabel's location and dimension.
	 * @param text	The text to be displayed by the CustomJLabel.
	 */
	public CustomJLabel(Rectangle rect, String text) {
		this(rect, null, text);
	}
	
	/***
	 * Creates a CustomJLabel instance with the specified
	 * rectangular bounds, with no image, empty string for content. 
	 * 
	 * @param rect	The rectangular bounds that defines the 
	 * 				CustomJLabel's location and dimension.
	 */
	public CustomJLabel(Rectangle rect) {
		this(rect, null, "");
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(this.CUSTOM_JLABEL_WIDTH, this.CUSTOM_JLABEL_HEIGHT);
	}
}