package org.andengine.util.debug;

import java.util.ArrayList;

public class TimeAverageCounter {
	final ArrayList<Integer> secondUpdateDelta = new ArrayList<Integer>();
	final int mSize;
	
	public TimeAverageCounter(int pSize) {
		mSize = pSize;
	}
	
	public float getNewWithUpdate(int p){
		secondUpdateDelta.add(0, p);
		if (secondUpdateDelta.size() > mSize) secondUpdateDelta.remove(secondUpdateDelta.size()-1);

		long dsumm = 0;
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
