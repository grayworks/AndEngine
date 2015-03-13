package org.andengine.entity.sprite;

public class AnimatedSequenceData {

	long mFrameDuration;
	int mAnimationOrder;
	boolean isReversed;
	boolean isFlippedHorizontal;
	int mRepeatCont;
	int mFrameToPlay;


	/**
	 * Single Frame Mode
	 *
	 * @param pFrameDuration
	 * @param pAnimationOrder
	 * @param pReversed
	 * @param pFlippedHorizontal
	 * @param pRepeatCont
	 * @param pFrameNumber
	 */
	public AnimatedSequenceData(long pFrameDuration, int pAnimationOrder, boolean pReversed, boolean pFlippedHorizontal, int pRepeatCont, int pFrameNumber) {
		mFrameDuration = pFrameDuration;
		mAnimationOrder = pAnimationOrder;
		isReversed = pReversed;
		isFlippedHorizontal = pFlippedHorizontal;
		mRepeatCont = pRepeatCont;
		mFrameToPlay = pFrameNumber;
	}

	/**
	 * Normal Mode
	 *
	 * @param pFrameDuration
	 * @param pAnimationOrder
	 * @param pReversed
	 * @param pFlippedHorizontal
	 * @param pRepeatCont
	 */
	public AnimatedSequenceData(long pFrameDuration, int pAnimationOrder, boolean pReversed, boolean pFlippedHorizontal, int pRepeatCont) {
		mFrameDuration = pFrameDuration;
		mAnimationOrder = pAnimationOrder;
		isReversed = pReversed;
		isFlippedHorizontal = pFlippedHorizontal;
		mRepeatCont = pRepeatCont;
		mFrameToPlay = -1;
	}
	
	/**
	 * Normal Mode
	 * 
	 * @param pFrameDuration
	 * @param pAnimationOrder
	 * @param pReversed
	 * @param pFlippedHorizontal
	 */
	public AnimatedSequenceData(long pFrameDuration, int pAnimationOrder, boolean pReversed, boolean pFlippedHorizontal) {
		mFrameDuration = pFrameDuration;
		mAnimationOrder = pAnimationOrder;
		isReversed = pReversed;
		isFlippedHorizontal = pFlippedHorizontal;
		mRepeatCont = 1;
		mFrameToPlay = -1;
	}
	
	/**
	 * Normal Mode
	 * 
	 * @param pFrameDuration
	 * @param pAnimationOrder
	 * @param pReversed
	 */
	public AnimatedSequenceData(long pFrameDuration, int pAnimationOrder, boolean pReversed) {
		mFrameDuration = pFrameDuration;
		mAnimationOrder = pAnimationOrder;
		isReversed = pReversed;
		isFlippedHorizontal = false;
		mRepeatCont = 1;
		mFrameToPlay = -1;
	}
}
