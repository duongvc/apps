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

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

/**
 * An extension of {@link ActionBarHelper} that provides Android 3.0-specific functionality for
 * Honeycomb tablets. It thus requires API level 11.
 */
public class ActionBarHelperHoneycomb extends ActionBarHelper {

    protected ActionBarHelperHoneycomb(Activity activity) {
        super(activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setRefreshActionItemState(boolean refreshing) {
    }

    /**
     * Returns a {@link Context} suitable for inflating layouts for the action bar. The
     * implementation for this method in {@link ActionBarHelperICS} asks the action bar for a
     * themed context.
     */
    protected Context getActionBarThemedContext() {
        return mActivity;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);    	
    }
    
    @Override
    public void onPostCreate(Bundle savedInstanceState) {    	
    	super.onPostCreate(savedInstanceState);
    	ActionBar bar= mActivity.getActionBar();
    	if(bar != null && bar.getCustomView() == null){
    		Log.i("ActionBarHelperHoneycomb", "onPostCreate - mActivity.getActionBar() != null");
    	}
    }
}
