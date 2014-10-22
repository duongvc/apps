/*
 * Copyright 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.v4.actionbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

/**
 * A base activity that defers common functionality across app activities to an {@link
 * ActionBarHelper}.
 *
 * NOTE: dynamically marking menu items as invisible/visible is not currently supported.
 *
 * NOTE: this may used with the Android Compatibility Package by extending
 * android.support.v4.app.FragmentActivity instead of {@link Activity}.
 */
public abstract class ActionBarActivity extends Activity {
    final ActionBarHelper mActionBarHelper = ActionBarHelper.createInstance(this);

    /**
     * Returns the {@link ActionBarHelper} for this activity.
     */
    protected ActionBarHelper getActionBarHelper() {
        return mActionBarHelper;
    }

    /**{@inheritDoc}*/
    @Override
    public MenuInflater getMenuInflater() {
        return mActionBarHelper.getMenuInflater(super.getMenuInflater());
    }

    /**{@inheritDoc}*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);        
        mActionBarHelper.onCreate(savedInstanceState);
    }

    /**{@inheritDoc}*/
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarHelper.onPostCreate(savedInstanceState);
    }

    /**
     * Base action bar-aware implementation for
     * {@link Activity#onCreateOptionsMenu(android.view.Menu)}.
     *
     * Note: marking menu items as invisible/visible is not currently supported.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean retValue = false;
        retValue |= mActionBarHelper.onCreateOptionsMenu(menu);
        retValue |= super.onCreateOptionsMenu(menu);
        //setMenuBackground();
        return retValue;
    }

    /**{@inheritDoc}*/
    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        mActionBarHelper.onTitleChanged(title, color);
        super.onTitleChanged(title, color);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	// TODO Auto-generated method stub
    	super.onConfigurationChanged(newConfig);
    }
    
	/*IconMenuItemView is the class that creates and controls the options menu  
	* which is derived from basic View class. So We can use a LayoutInflater  
	* object to create a view and apply the background. 
	*/  
	protected void setMenuBackground(){  
		getLayoutInflater().setFactory( new Factory() {
			@Override  
			public View onCreateView ( String name, Context context, AttributeSet attrs ) {  
			    if ( name.equalsIgnoreCase( "com.android.internal.view.menu.IconMenuItemView" ) ) {  
	                try { // Ask our inflater to create the view  
	                     LayoutInflater f = getLayoutInflater();  
	                     final View view = f.createView( name, null, attrs );  
	                     /*  
	                      * The background gets refreshed each time a new item is added the options menu.  
	                      * So each time Android applies the default background we need to set our own  
	                      * background. This is done using a thread giving the background change as runnable 
	                      * object 
	                      */  
	                     new Handler().post( new Runnable() {  
	                         public void run () {  
	                            view.setBackgroundResource( android.R.color.black);  
	                         }  
	                     } );  
	                     return view;  
	                 }  
	                 catch ( InflateException e ) {
	                	 Log.e("ActionBarActivity", "InflateException - " + e.getMessage(), e);
	                 }  
	                 catch ( ClassNotFoundException e ) {
	                	 Log.e("ActionBarActivity", "ClassNotFoundException - " + e.getMessage(), e);
	                 }  
	             }  
	             return null;  
	         }  
		 });  
	}
	
}
