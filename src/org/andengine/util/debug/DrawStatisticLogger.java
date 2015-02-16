package org.andengine.util.debug;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;

public class DrawStatisticLogger implements IUpdateHandler {
	final ArrayList<EntityLogger> entityLiggerArray = new ArrayList<EntityLogger>();
	
	final int mBufferSize;
	
	public DrawStatisticLogger(int pBufferSize) {
		mBufferSize = pBufferSize;
	}
	
	public void addEntity(Entity pEntity) {
		entityLiggerArray.add(new EntityLogger(pEntity));
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {

	}

	@Override
	public void reset() {

	}
	
	private class EntityLogger {
		final ArrayList<Long> drawTimeDelta = new ArrayList<Long>();
		
		public EntityLogger(Entity pEntity) {
			
		}
		
		public long getNewWithUpdate(long p){
			
			drawTimeDelta.add(0, p);
			if (drawTimeDelta.size() > mBufferSize) drawTimeDelta.remove(drawTimeDelta.size()-1);

			long dsumm = 0;
			for (int i = 0; i < drawTimeDelta.size(); i++) {
				dsumm += drawTimeDelta.get(i);
			}
			
			dsumm /= drawTimeDelta.size();
			return dsumm;
		}

	}

}
