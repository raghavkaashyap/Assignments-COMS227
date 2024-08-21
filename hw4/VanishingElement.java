package hw4;

/**
 * An element that does not move. Instead, it is intended to appear on the
 * screen for a fixed number of frames.
 * 
 * @author raghavk
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class VanishingElement extends SimpleElement{
	
	/**
	 * stores initial life
	 */
	private int initialLife;

	/**
	 * Constructs a new VanishingElement.
	 * 
	 * @param x           x-coordinate of upper left corner
	 * @param y           y-coordinate of upper left corner
	 * @param width       element's width
	 * @param height      element's height
	 * @param initialLife the number of frames until this element marks itself for
	 *                    deletion
	 */
	public VanishingElement(double x, double y, int width, int height, int initialLife) {
		// TODO: everything
		super(x, y, width, height);
		this.initialLife = initialLife;
	}

	
	/**
	 * decrements the value of initial life and marks element for deletion if 0
	 */
	@Override
	public void update() {
		super.update();
		initialLife--;
		if(initialLife==0) {
			this.markForDeletion();
		}
	}

}
