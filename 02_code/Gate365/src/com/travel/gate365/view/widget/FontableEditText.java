package com.travel.gate365.view.widget;

import com.travel.gate365.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class FontableEditText extends EditText {

    public FontableEditText(Context context) {
        super(context);
    }

    public FontableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        UiUtil.setCustomFont(this,context,attrs,
                R.styleable.com_travel_gate365_view_widget_FontableEditText,
                R.styleable.com_travel_gate365_view_widget_FontableEditText_font);
    }

    public FontableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        UiUtil.setCustomFont(this,context,attrs,
                R.styleable.com_travel_gate365_view_widget_FontableEditText,
                R.styleable.com_travel_gate365_view_widget_FontableEditText_font);
    }
}
