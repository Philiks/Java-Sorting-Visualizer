package customJComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * CustomJButton
 * @author philiks
 * Just like an ordinary JButton, CustomJButton component 
 * provides an action when trigerred. This allows 
 * interaction between various components or do abstract
 * action. 
 * 
 * CustomJButton was created to serve as a modified version 
 * of JButton, its purpose is to provide funtionalities
 * (same of JButton) with some modifications to suit the needs
 * of the project, Sorting Visualizer. Such modifications are: 
 * constructors, Look and Feel, and more default values to its
 * properties.
 * 
 * Warning:
 * The rectangular bounds is a required argument in
 * CustomJButton because the Container @see CustomJPanel 
 * that it will be placed has a null layout. Therefore, 
 * specifying its rectangular bounds defines the location
 * and dimension of the CustomJButton. 
 */
public class CustomJButton extends JButton{
	private static final long serialVersionUID = 1L;

	/***
	 * CUSTOM_JBUTTON_WIDTH and CUSTOM_JBUTTON_HEIGHT sets the
	 * PreferredSize of the CustomJButton. CUSTOM_JBUTTON_WIDTH
	 * and CUSTOM_JBUTTON_HEIGHT overrules the dimension that is
	 * set by Swing using @see setSize() by overriding
	 * @see getPreferredSize(). Therefore CustomJButton
	 * always use the dimension in rectangular bounds.
	 * CustomJButton's dimension can be changed by 
	 * calling the @see setBounds() method.
	 */
	protected int CUSTOM_JBUTTON_WIDTH;
	protected int CUSTOM_JBUTTON_HEIGHT;
	
	/***
	 * Creates a CustomJButton instance with the specified
	 * rectangular bounds, abstact action, icon (enabled and 
	 * disabled), and text.
	 * The CustomJButton is centered horizontally and vertically
	 * in its display area.
	 * The image displays first following by a trailing text.
	 * 
	 * @param rect			The rectangular bounds that defines the 
	 * 						CustomJButton's location and dimension.
	 * @param action		The action that will take place when
	 * 						the CustomJButton is triggered.
	 * @param enabled_icon	The image to be displayed by the CustomJButton when enabled.
	 * @param disabled_icon	The image to be displayed by the CustomJButton when disabled.
	 * @param text			The text to be displayed by the CustomJButton.
	 */
	public CustomJButton(Rectangle rect, AbstractAction action, 
						ImageIcon enabled_icon, ImageIcon disabled_icon, 
						String text, String tooltip) {
		CUSTOM_JBUTTON_WIDTH  = rect.width;
		CUSTOM_JBUTTON_HEIGHT = rect.height;
		
		setBounds(rect);
		setAction(action);
		setIcon(enabled_icon);
		setDisabledIcon(disabled_icon);
		setText(text);
		setToolTipText(tooltip);
		
		setOpaque(true);
		setForeground(Color.WHITE);
		setContentAreaFilled(false);
		setHorizontalAlignment(JButton.CENTER);
		setVerticalAlignment(JButton.CENTER);
		setBorder(BorderFactory.createLineBorder(Color.WHITE));		
	}
	
	/***
	 * Creates a CustomJButton instance with the specified
	 * rectangular bounds, abstact action and icon (enabled and 
	 * disabled).
	 * The CustomJButton is centered horizontally and vertically
	 * in its display area.
	 * 
	 * @param rect			The rectangular bounds that defines the 
	 * 						CustomJButton's location and dimension.
	 * @param action		The action that will take place when
	 * 						the CustomJButton is triggered.
	 * @param enabled_icon	The image to be displayed by the CustomJButton when enabled.
	 * @param disabled_icon	The image to be displayed by the CustomJButton when disabled.
	 * @param tooltip		The tooltip to be displayed by the CustomJButton
	 */
	public CustomJButton(Rectangle rect, AbstractAction action, 
						ImageIcon enabled_icon, ImageIcon disabled_icon, String tooltip) {
		this(rect, action, enabled_icon, disabled_icon, "", tooltip);
	}
	
	/***
	 * Creates a CustomJButton instance with the specified
	 * rectangular bounds, abstact action and icon.
	 * The CustomJButton is centered horizontally and vertically
	 * in its display area.
	 * 
	 * @param rect			The rectangular bounds that defines the 
	 * 						CustomJButton's location and dimension.
	 * @param action		The action that will take place when
	 * 						the CustomJButton is triggered.
	 * @param enabled_icon	The image to be displayed by the CustomJButton when enabled.
	 * @param tooltip		The tooltip to be displayed by the CustomJButton
	 */
	public CustomJButton(Rectangle rect, AbstractAction action, ImageIcon enabled_icon, String tooltip) {
		this(rect, action, enabled_icon, null, "", tooltip);
	}
	
	/***
	 * Creates a CustomJButton instance with the specified
	 * rectangular bounds, abstact action, and text.
	 * The CustomJButton is centered horizontally and vertically
	 * in its display area.
	 * 
	 * @param rect		The rectangular bounds that defines the 
	 * 					CustomJButton's location and dimension.
	 * @param action	The action that will take place when
	 * 					the CustomJButton is triggered.
	 * @param text		The text to be displayed by the CustomJButton.
	 */
	public CustomJButton(Rectangle rect, AbstractAction action, String text) {
		this(rect, action, null, null, text, "");
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(CUSTOM_JBUTTON_WIDTH, CUSTOM_JBUTTON_HEIGHT);
	}
}