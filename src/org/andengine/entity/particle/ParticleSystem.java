package org.andengine.entity.particle;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.particle.emitter.IParticleEmitter;
import org.andengine.entity.particle.initializer.IParticleInitializer;
import org.andengine.entity.particle.modifier.IParticleModifier;
import org.andengine.opengl.util.GLState;
import org.andengine.util.Constants;
import org.andengine.util.color.Color;
import org.andengine.util.math.MathUtils;

import android.util.FloatMath;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 19:42:27 - 14.03.2010
 */
public class ParticleSystem<T extends IEntity> extends Entity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final float[] POSITION_OFFSET_CONTAINER = new float[2];

	// ===========================================================
	// Fields
	// ===========================================================

	protected final IEntityFactory<T> mEntityFactory;
	protected final IParticleEmitter mParticleEmitter;

	protected final Particle<T>[] mParticles;

	protected final ArrayList<IParticleInitializer<T>> mParticleInitializers = new ArrayList<IParticleInitializer<T>>();
	protected final ArrayList<IParticleModifier<T>> mParticleModifiers = new ArrayList<IParticleModifier<T>>();

	private final float mRateMinimum;
	private final float mRateMaximum;

	private boolean mParticlesSpawnEnabled = true;
	private boolean mBindColorFromSystem = false;

	protected final int mParticlesMaximum;
	protected int mParticlesAlive;
	private float mParticlesDueToSpawn;
	
	private boolean mForceRecreateParticles;
	
	protected boolean mUseColorOffsets;
	protected float[] mColorOffsets = new float[] {1};
	
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public ParticleSystem(final IEntityFactory<T> pEntityFactory, final IParticleEmitter pParticleEmitter, final float pRateMinimum, final float pRateMaximum, final int pParticlesMaximum) {
		this(0, 0, pEntityFactory, pParticleEmitter, pRateMinimum, pRateMaximum, pParticlesMaximum);
	}

	@SuppressWarnings("unchecked")
	public ParticleSystem(final float pX, final float pY, final IEntityFactory<T> pEntityFactory, final IParticleEmitter pParticleEmitter, final float pRateMinimum, final float pRateMaximum, final int pParticlesMaximum) {
		super(pX, pY);

		this.mEntityFactory = pEntityFactory;
		this.mParticleEmitter = pParticleEmitter;
		this.mParticles = new Particle[pParticlesMaximum];
		this.mRateMinimum = pRateMinimum;
		this.mRateMaximum = pRateMaximum;
		this.mParticlesMaximum = pParticlesMaximum;

		this.registerUpdateHandler(this.mParticleEmitter);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public float[] getColorOffsets() {
		return mColorOffsets;
	}
	
	public void setColorOffsets(float...fs) {
		mUseColorOffsets = true;
		mColorOffsets = fs;
	}
	
	public void setUseColorOffsets(boolean pUse) {
		mUseColorOffsets = pUse;
	}
	
	public void setForceRecreateParticles(boolean pRecreate) {
		mForceRecreateParticles = pRecreate;
	}
	
	public boolean isParticlesSpawnEnabled() {
		return this.mParticlesSpawnEnabled;
	}

	public void setParticlesSpawnEnabled(final boolean pParticlesSpawnEnabled) {
		this.mParticlesSpawnEnabled = pParticlesSpawnEnabled;
	}

	public IEntityFactory<T> getParticleFactory() {
		return this.mEntityFactory;
	}

	public IParticleEmitter getParticleEmitter() {
		return this.mParticleEmitter;
	}

	public int getParticlesCount() {
		return mParticlesAlive;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void reset() {
		super.reset();

		this.mParticlesDueToSpawn = 0;
		this.mParticlesAlive = 0;
	}

	@Override
	protected void onManagedDraw(final GLState pGLState, final Camera pCamera) {
		for(int i = this.mParticlesAlive - 1; i >= 0; i--) {
			this.mParticles[i].onDraw(pGLState, pCamera);
		}
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if(this.isParticlesSpawnEnabled()) {
			this.spawnParticles(pSecondsElapsed);
		}

		final int particleModifierCountMinusOne = this.mParticleModifiers.size() - 1;
		for(int i = this.mParticlesAlive - 1; i >= 0; i--) {
			final Particle<T> particle = this.mParticles[i];

			/* Apply all particleModifiers */
			for(int j = particleModifierCountMinusOne; j >= 0; j--) {
				this.mParticleModifiers.get(j).onUpdateParticle(particle);
			}

			particle.onUpdate(pSecondsElapsed);
			if(particle.mExpired){
				this.mParticlesAlive--;

				this.moveParticleToEnd(i);
			}
		}
	}

	protected void moveParticleToEnd(final int pIndex) {
		final Particle<T> particle = this.mParticles[pIndex];

		final int particlesToCopy = this.mParticlesAlive - pIndex;
		if(particlesToCopy > 0) {
			System.arraycopy(this.mParticles, pIndex + 1, this.mParticles, pIndex, particlesToCopy);
		}
		this.mParticles[this.mParticlesAlive] = particle;

		/* This mode of swapping particles is faster than copying tons of array elements, 
		 * but it doesn't respect the 'lifetime' of the particles. */
//		particles[i] = particles[this.mParticlesAlive];
//		particles[this.mParticlesAlive] = particle;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void addParticleModifier(final IParticleModifier<T> pParticleModifier) {
		this.mParticleModifiers.add(pParticleModifier);
	}

	public void removeParticleModifier(final IParticleModifier<T> pParticleModifier) {
		this.mParticleModifiers.remove(pParticleModifier);
	}

	public void addParticleInitializer(final IParticleInitializer<T> pParticleInitializer) {
		this.mParticleInitializers.add(pParticleInitializer);
	}

	public void removeParticleInitializer(final IParticleInitializer<T> pParticleInitializer) {
		this.mParticleInitializers.remove(pParticleInitializer);
	}

	private void spawnParticles(final float pSecondsElapsed) {
		final float currentRate = this.determineCurrentRate();
		final float newParticlesThisFrame = currentRate * pSecondsElapsed;

		this.mParticlesDueToSpawn += newParticlesThisFrame;

		final int particlesToSpawnThisFrame = Math.min(this.mParticlesMaximum - this.mParticlesAlive, (int)FloatMath.floor(this.mParticlesDueToSpawn));
		this.mParticlesDueToSpawn -= particlesToSpawnThisFrame;

		for(int i = 0; i < particlesToSpawnThisFrame; i++){
			this.spawnParticle();
		}
	}

	private void spawnParticle() {
		if(this.mParticlesAlive < this.mParticlesMaximum){
			Particle<T> particle = this.mParticles[this.mParticlesAlive];
			//Log.d("mParticlesAlive", "mParticlesAlive = " + mParticlesAlive + "/" + mParticles.length);
			
			/* New particle needs to be created. */
			this.mParticleEmitter.getPositionOffset(ParticleSystem.POSITION_OFFSET_CONTAINER);

			final float x = ParticleSystem.POSITION_OFFSET_CONTAINER[Constants.VERTEX_INDEX_X];
			final float y = ParticleSystem.POSITION_OFFSET_CONTAINER[Constants.VERTEX_INDEX_Y];

			if(particle == null) {
				particle = new Particle<T>();
				particle.setColorOffset(mColorOffsets[new Random().nextInt(mColorOffsets.length)]);
				this.mParticles[this.mParticlesAlive] = particle;
				particle.setEntity(this.mEntityFactory.create(x, y));
			} else {
				
				if (mForceRecreateParticles) {
					particle.getEntity().dispose();
					particle.getEntity().detachSelf();
					
					particle = new Particle<T>();
					particle.setColorOffset(mColorOffsets[new Random().nextInt(mColorOffsets.length)]);
					this.mParticles[this.mParticlesAlive] = particle;
					particle.setEntity(this.mEntityFactory.create(x, y));
				} else {
					particle.reset();
					particle.getEntity().setPosition(x, y);
				}
			}

			/* Apply particle initializers. */
			{
				for(int i = this.mParticleInitializers.size() - 1; i >= 0; i--) {
					this.mParticleInitializers.get(i).onInitializeParticle(particle);
				}

				for(int i = this.mParticleModifiers.size() - 1; i >= 0; i--) {
					this.mParticleModifiers.get(i).onInitializeParticle(particle);
				}
			}
			
			if (mBindColorFromSystem) {
				if (mUseColorOffsets) particle.getEntity().setColor(getCorrectedColor(particle.getColorOffset()));
				else particle.getEntity().setColor(this.getColor());
			}

			this.mParticlesAlive++;
		}
	}

	protected float determineCurrentRate() {
		if(this.mRateMinimum == this.mRateMaximum){
			return this.mRateMinimum;
		} else {
			return MathUtils.random(this.mRateMinimum, this.mRateMaximum);
		}
	}
	
	public void bindColorFromSystem(boolean pBind) {
		mBindColorFromSystem = pBind;
	}
	
	@Override
	public void setColor(float pRed, float pGreen, float pBlue) {
		if (mParticles != null) {
			for (int i = 0; i < mParticles.length; i++) {
				if (mParticles[i] != null) {
					if (mUseColorOffsets) mParticles[i].getEntity().setColor(getCorrectedColor(mParticles[i].getColorOffset()));
					else mParticles[i].getEntity().setColor(this.getColor());
				}
			}
		}
		
		super.setColor(pRed, pGreen, pBlue);
	}

	private Color getCorrectedColor(float pColorOffset) {
		float r, g, b;
		
		r = this.getRed();
		g = this.getGreen();
		b = this.getBlue();
		
		if (pColorOffset >= 0) {
			r += (1 - r) * pColorOffset;
			g += (1 - g) * pColorOffset;
			b += (1 - b) * pColorOffset;
		} else {
			r *= -pColorOffset;
			g *= -pColorOffset;
			b *= -pColorOffset;
		}
		
		return new Color(r, g, b);
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	
}
