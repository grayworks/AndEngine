package org.andengine.entity.parallax;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.opengl.util.GLState;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 15:36:26 - 19.07.2010
 */
public class ParallaxComposite extends Entity {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final ArrayList<ParallaxCompositeEntity> mParallaxEntities = new ArrayList<ParallaxCompositeEntity>();
	private int mParallaxEntityCount;

	protected float mParallaxValue;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ParallaxComposite() {
		super();
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
	public void onDraw(final GLState pGLState, final Camera pCamera) {
		super.onDraw(pGLState, pCamera);

		final float parallaxValue = this.mParallaxValue;
		final ArrayList<ParallaxCompositeEntity> parallaxEntities = this.mParallaxEntities;

		for(int i = 0; i < this.mParallaxEntityCount; i++) {
			parallaxEntities.get(i).onDraw(pGLState, pCamera, parallaxValue);
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void attachParallaxEntity(final ParallaxCompositeEntity pParallaxEntity) {
		this.mParallaxEntities.add(pParallaxEntity);
		this.mParallaxEntityCount++;
	}

	public boolean detachParallaxEntity(final ParallaxCompositeEntity pParallaxEntity) {
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

	public static class ParallaxCompositeEntity {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		final float mParallaxFactor;
		final Entity mEntity;
		final float mShapeDistance;
		
		// ===========================================================
		// Constructors
		// ===========================================================

		public ParallaxCompositeEntity(final float pParallaxFactor, final float pShapesDistance, final Entity pEntity) {
			this.mParallaxFactor = pParallaxFactor;
			this.mEntity = pEntity;
			this.mShapeDistance = pShapesDistance;
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
				final float cameraWidth = pCamera.getWidth();
				final float shapeWidthScaled = mShapeDistance;
				float baseOffset = (pParallaxValue * this.mParallaxFactor) % shapeWidthScaled;

				while(baseOffset > 0) {
					baseOffset -= shapeWidthScaled;
				}
				
				pGLState.translateModelViewGLMatrixf(baseOffset - shapeWidthScaled, 0, 0);

				float currentMaxX = baseOffset;

				do {
					this.mEntity.onDraw(pGLState, pCamera);
					pGLState.translateModelViewGLMatrixf(shapeWidthScaled, 0, 0);
					currentMaxX += shapeWidthScaled;
				} while(currentMaxX < cameraWidth * 2f + shapeWidthScaled);
			}
			pGLState.popModelViewGLMatrix();
		}

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}
}
