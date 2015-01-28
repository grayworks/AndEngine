package org.andengine.opengl.texture.region;

import org.andengine.opengl.texture.ITexture;

/**
 * (c) Zynga 2011
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 20:07:17 - 07.08.2011
 */
public interface ITextureRegion {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public int getTextureX();
	public int getTextureY();

	public void setTextureX(final int pTextureX);
	public void setTextureY(final int pTextureY);
	public void setTexturePosition(final int pTextureX, final int pTextureY);

	/**
	 * Note: Takes {@link ITextureRegion#getScale()} into account!
	 */
	public int getWidth();
	/**
	 * Note: Takes {@link ITextureRegion#getScale()} into account!
	 */
	public int getHeight();

	public void setTextureWidth(final int pTextureWidth);
	public void setTextureHeight(final int pTextureHeight);
	public void setTextureSize(final int pTextureWidth, final int pTextureHeight);

	public void set(final int pTextureX, final int pTextureY, final int pTextureWidth, final int pTextureHeight);

	public float getU();
	public float getU2();
	public float getV();
	public float getV2();

	public boolean isScaled();
	public float getScale();
	public boolean isRotated();

	public ITexture getTexture();

	public ITextureRegion deepCopy();
}