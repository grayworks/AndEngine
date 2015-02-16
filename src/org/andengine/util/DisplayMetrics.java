package org.andengine.util;

import android.view.Display;

public class DisplayMetrics {
	public enum DisplayFixedSide {WIDTH, HEIGHT}
	
	Display mDisplay;
	int mBigSide, mSmallSide;
	DisplayFixedSide mFixedSide;
	
	int mWidthSide;
	int mDisplayWidth;
	int mDisplayHeight;
	boolean isLandscape;

	public DisplayMetrics(Display pDisplay, int pBigSide, int pSmallSide, DisplayFixedSide pFixedSide) {
		mDisplay = pDisplay;
		mBigSide = pBigSide;
		mSmallSide = pSmallSide;
		mFixedSide = pFixedSide;
		
		if (mFixedSide == DisplayFixedSide.WIDTH) {
			this.calculateMetricsFixedWidth();
		} else {
			this.calculateMetricsFixedHeight();
		}
	}
	
	public boolean isLandscape() {
		if (mFixedSide == DisplayFixedSide.WIDTH) {
			this.calculateMetricsFixedWidth();
		} else {
			this.calculateMetricsFixedHeight();
		}
		
		if (mDisplayWidth > mDisplayHeight) return true;
		else return false;
	}
	
	public int getWidth() {
		if (mFixedSide == DisplayFixedSide.WIDTH) {
			this.calculateMetricsFixedWidth();
		} else {
			this.calculateMetricsFixedHeight();
		}
		
		return mDisplayWidth;
	}
	
	public int getHeight() {
		if (mFixedSide == DisplayFixedSide.WIDTH) {
			this.calculateMetricsFixedWidth();
		} else {
			this.calculateMetricsFixedHeight();
		}
		
		return mDisplayHeight;
	}
	
	//=========================================
	
	private void calculateMetricsFixedWidth() {
		//int base;	//~1280*800	//~1024*640
		if (mDisplay.getWidth() > mDisplay.getHeight()) { //LANDSCAPE
			float aspect = (float) mDisplay.getHeight() / mDisplay.getWidth() ; //Landscape 800 / 1280
			mDisplayWidth = mBigSide; //FIXED
			mDisplayHeight = (int) (mBigSide * aspect);
			
			//Log.d("MAINSERV", "METRICS = Landscape: " + " mDisplay.getWidth() - " + mDisplay.getWidth() + ", mDisplay.getHeight() - " + mDisplay.getHeight());
			//Log.d("MAINSERV", "METRICS = Aspect: " + aspect + " mDisplayWidth - " + mDisplayWidth  + ". mDisplayHeight - " + mDisplayHeight);
		} else {
			float aspect = (float) mDisplay.getHeight() / mDisplay.getWidth();  //Portrait 800 / 1280
			mDisplayWidth = mSmallSide; //FIXED
			mDisplayHeight =  (int) (mSmallSide * aspect); //FIXED
			
			//Log.d("MAINSERV", "METRICS = Portrait: " + " display.getWidth() - " + mDisplay.getWidth() + ", display.getHeight() - " + mDisplay.getHeight());
			//Log.d("MAINSERV", "METRICS = Aspect: " + aspect + " mDisplayWidth - " + mDisplayWidth  + ". mDisplayHeight - " + mDisplayHeight);
		}
		
		if (mDisplayWidth >= mDisplayHeight) {isLandscape = true;}
		else {isLandscape = false;}		
	}
	
	
	private void calculateMetricsFixedHeight() {
		//int base;
		//~1280*800
		if (mDisplay.getWidth() > mDisplay.getHeight()) { //LANDSCAPE
			float aspect = (float) mDisplay.getWidth() / mDisplay.getHeight(); //Landscape 1280*800
			mDisplayHeight = mSmallSide; //FIXED 800
			mDisplayWidth = (int) (mSmallSide * aspect); //800*
			
			//Log.d("MAINSERV", "METRICS = Landscape: " + " mDisplay.getWidth() - " + mDisplay.getWidth() + ", mDisplay.getHeight() - " + mDisplay.getHeight());
			//Log.d("MAINSERV", "METRICS = Aspect: " + aspect + " mDisplayWidth - " + mDisplayWidth  + ". mDisplayHeight - " + mDisplayHeight);
		} else {
			float aspect = (float) mDisplay.getWidth() / mDisplay.getHeight();  //Portrait 800*1280
			mDisplayHeight = mBigSide; //FIXED
			mDisplayWidth = (int) (mBigSide * aspect); 
			
			//Log.d("MAINSERV", "METRICS = Portrait: " + " display.getWidth() - " + mDisplay.getWidth() + ", display.getHeight() - " + mDisplay.getHeight());
			//Log.d("MAINSERV", "METRICS = Aspect: " + aspect + " mDisplayWidth - " + mDisplayWidth  + ". mDisplayHeight - " + mDisplayHeight);
		}
		
		if (mDisplayWidth >= mDisplayHeight) {isLandscape = true;}
		else {isLandscape = false;}
	}
}
