/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package titutorial.demo3;

import java.io.IOException;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiC;
import org.appcelerator.titanium.util.Log;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.util.TiUIHelper;
import org.appcelerator.titanium.io.TiBaseFile;
import org.appcelerator.titanium.io.TiFileFactory;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiCompositeLayout;
import org.appcelerator.titanium.view.TiCompositeLayout.LayoutArrangement;
import org.appcelerator.titanium.view.TiDrawableReference;
import org.appcelerator.titanium.view.TiUIView;

import org.appcelerator.titanium.TiBlob;

import android.content.res.Resources;
import android.graphics.Bitmap;

import android.widget.Button;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;


// This proxy can be created by calling Demo3.createExample({message: "hello world"})
@Kroll.proxy(creatableInModule=Demo3Module.class)
public class ExampleProxy extends TiViewProxy
{
	// Standard Debugging variables
	private static final String TAG = "ExampleProxy";
	private String imageUrl;
	
	private class ExampleView extends TiUIView
	{
		public ExampleView(TiViewProxy proxy) {
			super(proxy);

			//layoutParams for holder view
			LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			//holder view 
			LinearLayout holder = new LinearLayout(proxy.getActivity());
			holder.setOrientation(LinearLayout.VERTICAL);
			holder.setLayoutParams(lp);

			//creating new button
			Button moduleButton = new Button(proxy.getActivity());  
			moduleButton.setText("Image from module");  
			moduleButton.setTextSize(20);  
			
			//since we can't access R.java in Android module, we are getting the resource 
			//using packageName and resource type
			String packageName = proxy.getActivity().getPackageName();
			Resources resources = proxy.getActivity().getResources();
			
			//getIdentifier method will return the resource id 
			int textStyle = resources.getIdentifier("facebook_loginbutton_blue", "drawable", packageName);
			moduleButton.setBackgroundResource(textStyle);

			holder.addView(moduleButton);

			//creating new image button
			ImageButton appButton = new ImageButton(proxy.getActivity());  
			
			//getting the blob object of the application image
			TiBlob imgObj = loadImageFromApplication(imageUrl);
			//creating the bitmap from the blob object
			TiDrawableReference ref = TiDrawableReference.fromBlob(proxy.getActivity(), imgObj);
			appButton.setImageBitmap(ref.getBitmap());
			holder.addView(appButton);
			
			setNativeView(holder);
		}

		@Override
		public void processProperties(KrollDict d)
		{
			super.processProperties(d);
		}
	}

	// Constructor
	public ExampleProxy()
	{
		super();
	}

	@Override
	public TiUIView createView(Activity activity)
	{
		TiUIView view = new ExampleView(this);
		view.getLayoutParams().autoFillsHeight = true;
		view.getLayoutParams().autoFillsWidth = true;
		return view;
	}
	
	// Handle creation options
	@Override
	public void handleCreationDict(KrollDict options) {
		super.handleCreationDict(options);

		if (options.containsKey("imageUrl")) {
			Log.d("@@##", "obj2 imageUrl vale = " + options.getString("imageUrl"));
			System.out.println("@@## imageUrl = "+options.getString("imageUrl"));
			imageUrl = options.getString("imageUrl");
		}
	}
	
	@Kroll.setProperty
	@Kroll.method
	public void setImageUrl(String bg) {
		imageUrl = bg;
	}

	@Kroll.getProperty
	@Kroll.method
	public String getImageUrl() {
		return imageUrl;
	}
	
	private String getPathToApplicationAsset(String assetName) {
		// The url for an application asset can be created by resolving the
		// specified
		// path with the proxy context. This locates a resource relative to the
		// application resources folder

		String result = resolveUrl(null, assetName);

		return result;
	}
	
	public TiBlob loadImageFromApplication(String imageName) {
		Log.d(TAG, " loadImageFromApplication " + imageName);

		TiBlob result = null;
		try {
			// Load the image from the application assets
			String url = getPathToApplicationAsset(imageName);
			TiBaseFile file = TiFileFactory.createTitaniumFile(
					new String[] { url }, false);
			Bitmap bitmap = TiUIHelper.createBitmap(file.getInputStream());

			// The bitmap must be converted to a TiBlob before returning
			result = TiBlob.blobFromImage(bitmap);
		} catch (IOException e) {
			Log.e(TAG, " EXCEPTION - IO");
		}

		Log.d(TAG, " " + result);

		return result;
	}
}