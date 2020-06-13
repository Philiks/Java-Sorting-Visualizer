package customJComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.Timer;

/**
 * CustomJSlider
 * @author philiks
 * Just like an ordinary JSlider, CustomJSlider component 
 * provides a way of adjust certain values via changing
 * the state of its thumb.
 * 
 * CustomJSlider was created to serve as a modified version 
 * of JSlider, its purpose is to provide funtionalities
 * (same of JSlider) with some modifications to suit the needs
 * of the project, Sorting Visualizer. Such modifications are: 
 * constructors, Look and Feel, and its ability to cover/uncover.
 */
public class CustomJSlider extends JSlider {
	private static final long serialVersionUID = 1L;
	
	/***
	 * CUSTOM_JSLIDER_WIDTH and CUSTOM_JSLIDER_HEIGHT sets the
	 * PreferredSize of the CustomJSlider. CUSTOM_JSLIDER_WIDTH
	 * and CUSTOM_JSLIDER_HEIGHT overrules the dimension that is
	 * set by Swing using @see setSize() by overriding
	 * @see getPreferredSize(). Therefore CustomJSlider
	 * always use the dimension in rectangular bounds.
	 * CustomJSlider's dimension can be changed by 
	 * calling the @see setBounds() method.
	 */
	protected int CUSTOM_JSLIDER_WIDTH;
	protected int CUSTOM_JSLIDER_HEIGHT;
	
	private Timer sliderCoverAnimation;
	private volatile boolean isCovered = true;
	private volatile int startOfCoveredRegion;
	
	public CustomJSlider(Rectangle rect, BoundedRangeModel brm, 
						int minor_tick, int major_tick) {
		CUSTOM_JSLIDER_WIDTH	= rect.width;
		CUSTOM_JSLIDER_HEIGHT	= rect.height;
		
		setOrientation(HORIZONTAL);
		setBounds(rect);
		setModel(brm);
		setMinorTickSpacing(minor_tick);
		setMajorTickSpacing(major_tick);
		
		setOpaque(false);
		setFocusable(false);
		setSnapToTicks(true);
		
		initAnimationTimer();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!isCovered)	return;
				isCovered = false;
				setEnabled(false);
				sliderCoverAnimation.start();				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				Rectangle bounds = new Rectangle(0, 0, CUSTOM_JSLIDER_WIDTH, CUSTOM_JSLIDER_HEIGHT);
				if(bounds.contains(e.getPoint()))	return;
				
				isCovered = true;
				setEnabled(false);
				sliderCoverAnimation.start();
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(startOfCoveredRegion, 0, 
				this.getWidth() - startOfCoveredRegion, this.getHeight());
	}
	
	public void initAnimationTimer() {
		sliderCoverAnimation = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(startOfCoveredRegion <= getWidth() && !isCovered())	
					startOfCoveredRegion++;
				else if(startOfCoveredRegion >= 0 && isCovered())	
					startOfCoveredRegion--;
				else {
					sliderCoverAnimation.stop();
					setEnabled(true);
				}
				
				repaint();
			}
		});
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(CUSTOM_JSLIDER_WIDTH, CUSTOM_JSLIDER_HEIGHT);
	}
	
	public final int getStartOfCoveredRegion() {
		return startOfCoveredRegion;
	}
	
	public final boolean isCovered() {
		return isCovered;
	}
	
	public final boolean isAnimationRunning() {
		return sliderCoverAnimation.isRunning();
	}
}