package com.travel.gate365.view.widget;

import com.travel.gate365.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontableTextView2 extends TextView {

    public FontableTextView2(Context context) {
        super(context);
    }

    public FontableTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        UiUtil.setCustomFont(this,context,attrs,
                R.styleable.com_travel_gate365_view_widget_FontableTextView,
                R.styleable.com_travel_gate365_view_widget_FontableTextView_font);
    }

    public FontableTextView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        UiUtil.setCustomFont(this,context,attrs,
                R.styleable.com_travel_gate365_view_widget_FontableTextView,
                R.styleable.com_travel_gate365_view_widget_FontableTextView_font);
    }
}
