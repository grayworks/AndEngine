package org.andengine.entity.parallax;



/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 19:44:31 - 19.07.2010
 */
public class AutoParallaxObject extends ParallaxObject {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private float mParallaxChangePerSecond;

	// ===========================================================
	// Constructors
	// ===========================================================

	public AutoParallaxObject(final float pParallaxChangePerSecond) {
		
		this.mParallaxChangePerSecond = pParallaxChangePerSecond;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void setParallaxChangePerSecond(final float pParallaxChangePerSecond) {
		this.mParallaxChangePerSecond = pParallaxChangePerSecond;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onUpdate(final float pSecondsElapsed) {
		super.onUpdate(pSecondsElapsed);

		this.mParallaxValue += this.mParallaxChangePerSecond * pSecondsElapsed;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
