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
	 * @param pFrameDuration - ¬рем€ отображени€ кадра (скорость)
	 * @param pAnimationOrder - »ндекс анимации (соответствует массиву текстур)
	 * @param pReversed - ѕроигрывать в обратном пор€дке
	 * @param pFlippedHorizontal - ќтразить по горизонтали
	 * @param pRepeatCont -  оличество повторов (количество показов кадра в режиме одного кадра)
	 * @param pFrameNumber - номер кадра дл€ отображени€ (требуетс€ указание количества повторов с расчетом длины). -1 дл€ полного цикла кадров в анимации.
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
	 * @param pFrameDuration - ¬рем€ отображени€ кадра (скорость)
	 * @param pAnimationOrder - »ндекс анимации (соответствует массиву текстур)
	 * @param pReversed - ѕроигрывать в обратном пор€дке
	 * @param pFlippedHorizontal - ќтразить по горизонтали
	 * @param pRepeatCont -  оличество повторов
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
	 * @param pFrameDuration - ¬рем€ отображени€ кадра (скорость)
	 * @param pAnimationOrder - »ндекс анимации (соответствует массиву текстур)
	 * @param pReversed - ѕроигрывать в обратном пор€дке
	 * @param pFlippedHorizontal - ќтразить по горизонтали
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
	 * @param pFrameDuration - ¬рем€ отображени€ кадра (скорость)
	 * @param pAnimationOrder - »ндекс анимации (соответствует массиву текстур)
	 * @param pReversed - ѕроигрывать в обратном пор€дке
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
