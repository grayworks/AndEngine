package org.andengine.engine;

import org.andengine.engine.Engine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.util.time.TimeConstants;

public class FlexibleFPSEngine extends Engine {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private long mPreferredFrameLengthNanoseconds;
	private int mFramesPerSecond;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public FlexibleFPSEngine(final EngineOptions pEngineOptions, final int pFramesPerSecond) {
		super(pEngineOptions);
		this.mFramesPerSecond = pFramesPerSecond;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onUpdate(final long pNanosecondsElapsed) throws InterruptedException {
		this.mPreferredFrameLengthNanoseconds = TimeConstants.NANOSECONDS_PER_SECOND / mFramesPerSecond;
		
		final long preferredFrameLengthNanoseconds = this.mPreferredFrameLengthNanoseconds;
		final long deltaFrameLengthNanoseconds = preferredFrameLengthNanoseconds - pNanosecondsElapsed;

		if(deltaFrameLengthNanoseconds <= 0) {
			super.onUpdate(pNanosecondsElapsed);
		} else {
			final int sleepTimeMilliseconds = (int) (deltaFrameLengthNanoseconds / TimeConstants.NANOSECONDS_PER_MILLISECOND);

			Thread.sleep(sleepTimeMilliseconds);
			super.onUpdate(pNanosecondsElapsed + deltaFrameLengthNanoseconds);
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void setFramesPerSecond (final int pFramesPerSecond) {
		mFramesPerSecond = pFramesPerSecond;
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
