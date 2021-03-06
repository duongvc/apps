package com.travel.gate365.view.widget;

import com.travel.gate365.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontableTextView extends TextView {

    public FontableTextView(Context context) {
        super(context);
    }

    public FontableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        UiUtil.setCustomFont(this,context,attrs,
                R.styleable.com_travel_gate365_view_widget_FontableTextView,
                R.styleable.com_travel_gate365_view_widget_FontableTextView_font);
    }

    public FontableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        UiUtil.setCustomFont(this,context,attrs,
                R.styleable.com_travel_gate365_view_widget_FontableTextView,
                R.styleable.com_travel_gate365_view_widget_FontableTextView_font);
    }
    
    @Override
    public void setText(CharSequence text, BufferType type) {
    	String endText = text.toString() + "\u00A0";
    	super.setText(endText, type);
    }
}
