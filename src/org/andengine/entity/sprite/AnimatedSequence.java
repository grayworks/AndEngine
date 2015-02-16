package org.andengine.entity.sprite;

import java.util.Arrays;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.array.ArrayUtils;
import org.andengine.util.color.Color;
import org.andengine.util.time.TimeConstants;

public class AnimatedSequence extends Entity {
	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================

	private AnimatedSprite[] sprites;
	private ISequenceListener mSequenceListener;
	
	private int mCurrentSprite;
	
	private int mCurrentOrderPosition = 0;
	private boolean mCurrentAnimationFinished = true;
	
	private long[] mFrameDuration;
	private int[] mAnimationOrder;
	private boolean[] mReversedAnimation;
	private boolean[] mFlippedHorizontal;
	private int[] mRepeatCount;
	private int[] mFrameToPlay;
	
	private int mRepeatCounter;
	private boolean mAnimationRunning;
	private long mFrameDeltaTime;
	
	public float mWidth, mHeight;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public AnimatedSequence(final float pX, final float pY, final ITiledTextureRegion[] pTiledTextureRegions, final VertexBufferObjectManager pVertexBufferObjectManager) {
		this.setX(pX);
		this.setY(pY);
		
		sprites = new AnimatedSprite[pTiledTextureRegions.length];
		
		for (int i = 0; i < pTiledTextureRegions.length; i++) {
			sprites[i] = new AnimatedSprite(-pTiledTextureRegions[i].getWidth(0)/2, -pTiledTextureRegions[i].getHeight(0)/2, pTiledTextureRegions[i], pVertexBufferObjectManager);
			sprites[i].setVisible(false);
			this.attachChild(sprites[i]);
			
			if (sprites[i].getWidth() > mWidth) mWidth = sprites[i].getWidth();
			if (sprites[i].getHeight() > mHeight) mHeight = sprites[i].getHeight();
		}
		
		sprites[0].setVisible(true);
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	@Override
	public void setColor(Color pColor) {
		for (int i = 0; i < sprites.length; i++) {
			sprites[i].setColor(pColor);
		}
	}
	
	// ===========================================================
	// Methods
	// ===========================================================
	
	public void setSpritePositionOffset(int pIndex, float pX, float pY) {
		sprites[pIndex].setPosition(-sprites[pIndex].getWidth()/2 + pX, -sprites[pIndex].getHeight()/2 + pY);
	}
	
	public void animate(AnimatedSequenceData[] pAnimatedSequenceData) {
		mCurrentOrderPosition = 0;
		
		mFrameDuration = new long[pAnimatedSequenceData.length];
		for (int i = 0; i < mFrameDuration.length; i++) mFrameDuration[i] = pAnimatedSequenceData[i].mFrameDuration;
		
		mAnimationOrder = new int[pAnimatedSequenceData.length];
		for (int i = 0; i < mAnimationOrder.length; i++) mAnimationOrder[i] = pAnimatedSequenceData[i].mAnimationOrder;
		
		mReversedAnimation = new boolean[pAnimatedSequenceData.length];
		for (int i = 0; i < mReversedAnimation.length; i++) mReversedAnimation[i] = pAnimatedSequenceData[i].isReversed;

		mFlippedHorizontal = new boolean[pAnimatedSequenceData.length];
		for (int i = 0; i < mFlippedHorizontal.length; i++) mFlippedHorizontal[i] = pAnimatedSequenceData[i].isFlippedHorizontal;
		
		mRepeatCount = new int[pAnimatedSequenceData.length];
		for (int i = 0; i < mRepeatCount.length; i++) mRepeatCount[i] = pAnimatedSequenceData[i].mRepeatCont;
		
		mFrameToPlay = new int[pAnimatedSequenceData.length];
		for (int i = 0; i < mFrameToPlay.length; i++) mFrameToPlay[i] = pAnimatedSequenceData[i].mFrameToPlay;
		
		
		mCurrentSprite = mAnimationOrder[mCurrentOrderPosition];
		mRepeatCounter = mRepeatCount[mCurrentOrderPosition];
		
		mAnimationRunning = true;
	}
	
	public void setSequenceListener(ISequenceListener pSequenceListener) {
		mSequenceListener = pSequenceListener;
	}
	
	public void start() {
		mAnimationRunning = true;
	}
	
	public void stop() {
		mAnimationRunning = false;
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		
		if(this.mAnimationRunning) {
			final long nanoSecondsElapsed = (long) (pSecondsElapsed * TimeConstants.NANOSECONDS_PER_SECOND);
			this.mFrameDeltaTime += nanoSecondsElapsed;
			
			if (mFrameDeltaTime >= mFrameDuration[mCurrentOrderPosition]) {
				for (int i = 0; i < sprites.length; i++) {
					sprites[i].setVisible(i == mCurrentSprite);
				}
				
				if (mCurrentAnimationFinished) {
					sprites[mCurrentSprite].setFlippedHorizontal(mFlippedHorizontal[mCurrentOrderPosition]);
					
					//Log.d("AnimatedSequence.java", "Position = " + mCurrentOrderPosition);
					//-------------------
					long[] durationArray;
					int[] frames;

					if (mFrameToPlay[mCurrentOrderPosition] == -1) { //Normal Play
						//Log.d("AnimatedSequence.java", "Normal Play Mode. Position = " + mCurrentOrderPosition);
						frames = new int[sprites[mCurrentSprite].getTileCount()];
						
						for (int i = 0; i < frames.length; i++) frames[i] = i;
						
						if (mReversedAnimation[mCurrentOrderPosition]) {
							ArrayUtils.reverse(frames); //Reverse Animation
						}
						
						durationArray = new long[frames.length];
						Arrays.fill(durationArray, mFrameDuration[mCurrentOrderPosition]);
					} else { //Single Frame Mode
						//Log.d("AnimatedSequence.java", "Single Frame Mode. Position = " + mCurrentOrderPosition + ", mRepeatCounter = " + mRepeatCounter);
						
						frames = new int[mRepeatCounter];
						for (int i = 0; i < frames.length; i++) frames[i] = mFrameToPlay[mCurrentOrderPosition];

						durationArray = new long[mRepeatCounter];
						Arrays.fill(durationArray, mFrameDuration[mCurrentOrderPosition]);

						mRepeatCounter = 1;
					}

					//-------------------
					
					sprites[mCurrentSprite].animate(durationArray, frames, false);
					sprites[mCurrentSprite].setAnimationListener(new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
							mCurrentAnimationFinished = false;
							mRepeatCounter--;
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,	int pRemainingLoopCount, int pInitialLoopCount) {

						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,	int pOldFrameIndex, int pNewFrameIndex) {

						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
							int oldSprite = mCurrentSprite;
							
							if (mRepeatCounter == 0) {
								if (mCurrentOrderPosition == mAnimationOrder.length - 1) {
									if (mSequenceListener != null) mSequenceListener.onAnimationSequenceFinished(sprites[mCurrentSprite]);
									mCurrentOrderPosition = 0;
									mCurrentSprite = mAnimationOrder[mCurrentOrderPosition];
								} else {
									mCurrentOrderPosition++;
									mCurrentSprite = mAnimationOrder[mCurrentOrderPosition];
								}
								
								mRepeatCounter = mRepeatCount[mCurrentOrderPosition];
							}
							
							if (mSequenceListener != null) mSequenceListener.onAnimationSpriteChanged(sprites[oldSprite], sprites[mCurrentSprite]);
							
							// Установка последнего кадра для следующей анимации (если она обращена) и первого кадра если нет.
							if (mReversedAnimation[mCurrentOrderPosition]) {
								sprites[mCurrentSprite].setCurrentTileIndex(sprites[mCurrentSprite].getTileCount()-1);
							} else {
								sprites[mCurrentSprite].setCurrentTileIndex(0);
							}
	
							mCurrentAnimationFinished = true;
						}
					});
				}
			}
		}
	}
	
	protected long[] getFilledArray(int size, long value) {
		long[] a = new long[size];
		for (int i = 0; i < size; i++) {
			a[i] = value;
		}
		return a;
	}
	
	public static interface ISequenceListener {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		public void onAnimationStarted(final AnimatedSprite pCurrentAnimatedSprite);

		public void onAnimationSpriteChanged(final AnimatedSprite pAnimatedOldSprite, final AnimatedSprite pAnimatedNewSprite);
		
		public void onAnimationSequenceFinished(final AnimatedSprite pCurrentAnimatedSprite);
	}
	
	@Override
	public void setColor(float pRed, float pGreen, float pBlue, float pAlpha) {
		for(int i = 0; i < sprites.length; i++) sprites[i].setColor(pRed, pGreen, pBlue, pAlpha);
		super.setColor(pRed, pGreen, pBlue, pAlpha);
	}
	
	@Override
	public void setColor(float pRed, float pGreen, float pBlue) {
		for(int i = 0; i < sprites.length; i++) sprites[i].setColor(pRed, pGreen, pBlue);
		super.setColor(pRed, pGreen, pBlue);
	}
}
