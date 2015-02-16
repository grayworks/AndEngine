package org.andengine.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import android.content.res.AssetManager;

public class LoadString {
	public static String readFileFromPath(String path) throws IOException {
		FileInputStream stream = new FileInputStream(new File(path));
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
					fc.size());
			/* Instead of using default, pass in a decoder. */
			return Charset.defaultCharset().decode(bb).toString();
		} finally {
			stream.close();
		}
	}

	public static String readFileFromAssets(AssetManager pAssetManager,
			String pPath) {
		InputStream input;

		try {

			input = pAssetManager.open(pPath);

			int size = input.available();

			byte[] buffer = new byte[size];

			input.read(buffer);

			input.close();

			// byte buffer into a string

			String text = new String(buffer);

			return text;

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();
			return "";
		}

	}
}
