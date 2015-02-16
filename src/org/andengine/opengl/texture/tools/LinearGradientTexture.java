package org.andengine.opengl.texture.tools;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.EmptyBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Shader;

public class LinearGradientTexture {
	BitmapTextureAtlas mTextureAtlas;
	TextureRegion mTextureRegion;
	
	public LinearGradientTexture (TextureManager pTextureManager, final int pWidth, final int pHeight, final int pStartColor, final int pFinishColor) {
		this(pTextureManager, pWidth, pHeight, 0, 0, 0, pHeight, pStartColor, pFinishColor);
	}
	
	public LinearGradientTexture (TextureManager pTextureManager, final int pWidth, final int pHeight, final float x1, final float y1, final float x2, final float y2, final int pStartColor, final int pFinishColor) {
		mTextureAtlas = new BitmapTextureAtlas(pTextureManager, pWidth, pHeight, TextureOptions.NEAREST);
		
		final IBitmapTextureAtlasSource baseTextureSource = new EmptyBitmapTextureAtlasSource(pWidth, pHeight);
		final IBitmapTextureAtlasSource decoratedTextureAtlasSource = new BaseBitmapTextureAtlasSourceDecorator(baseTextureSource) {
			@Override
			protected void onDecorateBitmap(Canvas pCanvas) throws Exception {
				LinearGradient f = new LinearGradient(x1, y1, x2, y2, pStartColor, pFinishColor, Shader.TileMode.MIRROR);
				mPaint.setShader(f);
				pCanvas.drawPaint(mPaint);
			}
			
			@Override
			public BaseBitmapTextureAtlasSourceDecorator deepCopy() {
				throw new DeepCopyNotSupportedException();
			}
		};
		
		this.mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromSource(this.mTextureAtlas, decoratedTextureAtlasSource, 0, 0);
		this.mTextureAtlas.load();
	}
	
	public BitmapTextureAtlas getBitmapTextureAtlas() {
		return mTextureAtlas;
	}
	
	public TextureRegion getTextureRegion() {
		return mTextureRegion;
	}
	
	
}
