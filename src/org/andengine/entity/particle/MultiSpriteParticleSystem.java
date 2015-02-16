package org.andengine.entity.particle;

import java.util.Random;

import org.andengine.entity.IEntityFactory;
import org.andengine.entity.particle.Particle;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.IParticleEmitter;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * (c) Zynga 2011
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 21:00:23 - 04.12.2011
 */
public class MultiSpriteParticleSystem extends ParticleSystem<Sprite> {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	static Random random = new Random();
	private static int previous;
	// ===========================================================
	// Constructors
	// ===========================================================

	public MultiSpriteParticleSystem(final IParticleEmitter pParticleEmitter, final float pRateMinimum, final float pRateMaximum, final int pParticlesMaximum, final ITextureRegion[] pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
		this(0, 0, pParticleEmitter, pRateMinimum, pRateMaximum, pParticlesMaximum, pTextureRegion, pVertexBufferObjectManager);
	}

	public MultiSpriteParticleSystem(final float pX, final float pY, final IParticleEmitter pParticleEmitter, final float pRateMinimum, final float pRateMaximum, final int pParticlesMaximum, final ITextureRegion[] pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, new IEntityFactory<Sprite>() {
			@Override
			public Sprite create(final float pX, final float pY) {
				int n = random.nextInt(pTextureRegion.length);
				if (previous == n) n = random.nextInt(pTextureRegion.length);
				previous = n;
				
				//Log.d("MultiSpriteParticleSystem", "Sprite region number = " + n + "/" + pTextureRegion.length);
				return new Sprite(pX, pY, pTextureRegion[n], pVertexBufferObjectManager);
			}
		}, pParticleEmitter, pRateMinimum, pRateMaximum, pParticlesMaximum);
		
	}

	protected MultiSpriteParticleSystem(final float pX, final float pY, final IEntityFactory<Sprite> pEntityFactory, final IParticleEmitter pParticleEmitter, final float pRateMinimum, final float pRateMaximum, final int pParticlesMaximum) {
		super(pX, pY, pEntityFactory, pParticleEmitter, pRateMinimum, pRateMaximum, pParticlesMaximum);
	}
	
	public Particle<Sprite>[] getParticlesSprite() {
		return mParticles;
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

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
