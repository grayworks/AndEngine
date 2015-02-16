package org.andengine.entity.particle.modifier;


import org.andengine.entity.particle.Particle;
import org.andengine.entity.shape.RectangularShape;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 21:21:10 - 14.03.2010
 */
public class LeftBorderExpireParticleModifier<T extends RectangularShape> implements IParticleModifier<T> {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final int mExpirePosition;

	// ===========================================================
	// Constructors
	// ===========================================================

	public LeftBorderExpireParticleModifier(final int pExpirePosition) {
		this.mExpirePosition = pExpirePosition;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public int getExpirePosition() {
		return this.mExpirePosition;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onInitializeParticle(final Particle<T> pParticle) {

	}

	@Override
	public void onUpdateParticle(final Particle<T> pParticle) {
		if(pParticle.getEntity().getX() < mExpirePosition - pParticle.getEntity().getWidth()) {
			pParticle.setExpired(true);
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
