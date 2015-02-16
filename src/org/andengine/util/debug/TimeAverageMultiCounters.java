package org.andengine.util.debug;

import java.util.ArrayList;

public class TimeAverageMultiCounters {
	
	Counter[] mCounters;
	final int mCounterCount;
	final int mSize;
	
	public TimeAverageMultiCounters(int pCounterCount, int pSize) {
		mCounterCount = pCounterCount;
		mSize = pSize;
		mCounters = new Counter[pCounterCount];
	}
	
	public float getNewWithUpdate(int pCounterNumber, float pNewData){
		float value;
		
		if (mCounters[pCounterNumber] == null) {
			mCounters[pCounterNumber] = new Counter();
			value = mCounters[pCounterNumber].getNewWithUpdate(pNewData);
		} else {
			value = mCounters[pCounterNumber].getNewWithUpdate(pNewData);
		}

		return value;
	}
	
	public int getSize(int pCounterNumber) {
		if (mCounters[pCounterNumber] == null) {
			return 0;
		} else {
			return mCounters[pCounterNumber].getSize();
		}
	}
	
	private class Counter {
		final ArrayList<Float> secondUpdateDelta = new ArrayList<Float>();
		private Counter() {
			
		}
		
		public float getNewWithUpdate(float p){
			secondUpdateDelta.add(0, p);
			if (secondUpdateDelta.size() > mSize) secondUpdateDelta.remove(secondUpdateDelta.size()-1);

			float dsumm = 0;
			for (int i = 0; i < secondUpdateDelta.size(); i++) {
				dsumm += secondUpdateDelta.get(i);
			}
			
			dsumm /= secondUpdateDelta.size();
			return dsumm;
		}
		
		public int getSize() {
			return secondUpdateDelta.size();
		}
	}

}
