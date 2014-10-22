package com.travel.gate365.view.widget;

import com.travel.gate365.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class FontableCheckBox extends CheckBox {
    public FontableCheckBox(Context context) {
        super(context);
    }

    public FontableCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        UiUtil.setCustomFont(this, context, attrs,
                R.styleable.com_travel_gate365_view_widget_FontableCheckbox,
                R.styleable.com_travel_gate365_view_widget_FontableCheckbox_font);
    }

    public FontableCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        UiUtil.setCustomFont(this, context, attrs,
                R.styleable.com_travel_gate365_view_widget_FontableCheckbox,
                R.styleable.com_travel_gate365_view_widget_FontableCheckbox_font);
    }
}
