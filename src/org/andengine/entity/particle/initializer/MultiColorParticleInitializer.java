package org.andengine.entity.particle.initializer;

import java.util.Random;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;
import org.andengine.util.color.Color;


/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 10:17:42 - 29.06.2010
 */
public class MultiColorParticleInitializer<T extends IEntity> implements IParticleInitializer<T> {
	// ===========================================================
	// Constants
	// ===========================================================
	final Random RANDOM = new Random();
	// ===========================================================
	// Fields
	// ===========================================================
	
	Color[] mColor;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public MultiColorParticleInitializer(final Color[] pColor) {
		mColor = pColor;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onInitializeParticle(Particle<T> pParticle) {
		pParticle.getEntity().setColor(mColor[RANDOM.nextInt(mColor.length)]);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
