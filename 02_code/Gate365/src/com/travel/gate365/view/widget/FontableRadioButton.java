package com.travel.gate365.view.widget;

import com.travel.gate365.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.widget.RadioButton;

public class FontableRadioButton extends RadioButton {
    public FontableRadioButton(Context context) {
        super(context);
    }

    public FontableRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        UiUtil.setCustomFont(this, context, attrs,
                R.styleable.com_travel_gate365_view_widget_FontableRadioButton,
                R.styleable.com_travel_gate365_view_widget_FontableRadioButton_font);
    }

    public FontableRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        UiUtil.setCustomFont(this, context, attrs,
                R.styleable.com_travel_gate365_view_widget_FontableRadioButton,
                R.styleable.com_travel_gate365_view_widget_FontableRadioButton_font);
    }
    
    @Override
	public void setEnabled(boolean enabled) {
		if(isEnabled() == enabled)
	        return;

	    float from = enabled ? .3f : 1.0f;
	    float to = enabled ? 1.0f : .3f;

	    AlphaAnimation a = new AlphaAnimation(from, to);

	    a.setDuration(500);
	    a.setFillAfter(true);

	    super.setEnabled(enabled);
	    startAnimation(a);		
	}    
}
