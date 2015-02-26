package org.andengine.entity.parallax;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.opengl.util.GLState;
import org.andengine.util.color.Color;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 15:36:26 - 19.07.2010
 */
public class ParallaxObject extends Entity {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final ArrayList<ParallaxObjectEntity> mParallaxEntities = new ArrayList<ParallaxObjectEntity>();
	private int mParallaxEntityCount;

	protected float mParallaxValue;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ParallaxObject() {
		//super(pRed, pGreen, pBlue);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void setParallaxValue(final float pParallaxValue) {
		this.mParallaxValue = pParallaxValue;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		super.onUpdate(pSecondsElapsed);
		for(int i = 0; i < this.mParallaxEntityCount; i++) {
			for (int j = 0; j < mParallaxEntities.get(i).mAreaShapes.length; j++) {
				mParallaxEntities.get(i).mAreaShapes[j].setColor(mColor);
			}
		}
	};
	
	@Override
	public void onDraw(final GLState pGLState, final Camera pCamera) {
		super.onDraw(pGLState, pCamera);

		final float parallaxValue = this.mParallaxValue;
		final ArrayList<ParallaxObjectEntity> parallaxEntities = this.mParallaxEntities;

		for(int i = 0; i < this.mParallaxEntityCount; i++) {
			parallaxEntities.get(i).onDraw(pGLState, pCamera, parallaxValue);
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================
	
	public void attachParallaxEntity(final ParallaxObjectEntity pParallaxEntity) {
		this.mParallaxEntities.add(pParallaxEntity);
		this.mParallaxEntityCount++;
	}

	public boolean detachParallaxEntity(final ParallaxObjectEntity pParallaxEntity) {
		this.mParallaxEntityCount--;
		final boolean success = this.mParallaxEntities.remove(pParallaxEntity);
		if(!success) {
			this.mParallaxEntityCount++;
		}
		return success;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public static class ParallaxObjectEntity {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		final float mParallaxFactor;
		final IAreaShape[] mAreaShapes;
		final float mDistance;
		final float mWidthMultiplier;

		// ===========================================================
		// Constructors
		// ===========================================================
		
		/**
		 * 
		 * @param pParallaxFactor - move speed.
		 * @param pWidthMultiplier - screen wide multiplier.
		 * @param pDistance - distance between shapes.
		 * @param pAreaShapes - shape or shapes array.
		 */
		public ParallaxObjectEntity(final float pParallaxFactor, final float pWidthMultiplier, final float pDistance, final IAreaShape... pAreaShapes) {
			this.mParallaxFactor = pParallaxFactor;
			this.mAreaShapes = pAreaShapes;
			this.mDistance = pDistance;
			this.mWidthMultiplier = pWidthMultiplier;
		}

		// ===========================================================
		// Getter & Setter
		// ===========================================================

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================

		public void onDraw(final GLState pGLState, final Camera pCamera, final float pParallaxValue) {
			pGLState.pushModelViewGLMatrix();
			{
				float cameraWidth = pCamera.getWidth() * mWidthMultiplier;
				float shapesWidthScaled = 0;
				for (int i = 0; i < mAreaShapes.length; i++) shapesWidthScaled += mAreaShapes[i].getWidthScaled() + mDistance;
				
				float baseOffset = (pParallaxValue * this.mParallaxFactor) % shapesWidthScaled;

				while(baseOffset > 0) {
					baseOffset -= shapesWidthScaled;
				}
				
				pGLState.translateModelViewGLMatrixf(baseOffset, 0, 0);

				float currentMaxX = baseOffset;
				
				do {
					for (int i = 0; i < mAreaShapes.length; i++){
						this.mAreaShapes[i].onDraw(pGLState, pCamera);
						pGLState.translateModelViewGLMatrixf(mAreaShapes[i].getWidthScaled() + mDistance, 0, 0);
					}
					currentMaxX += shapesWidthScaled;
				} while(currentMaxX < cameraWidth);
			}
			pGLState.popModelViewGLMatrix();
		}

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}
}
