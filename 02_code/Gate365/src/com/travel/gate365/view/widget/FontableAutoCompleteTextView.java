/**
 * 
 */
package com.travel.gate365.view.widget;

import com.travel.gate365.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * @author Duong.Vo
 *
 */
public class FontableAutoCompleteTextView extends AutoCompleteTextView {

	/**
	 * @param context
	 */
	public FontableAutoCompleteTextView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public FontableAutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
        UiUtil.setCustomFont(this,context,attrs,
                R.styleable.com_travel_gate365_view_widget_FontableAutoCompleteTextView,
                R.styleable.com_travel_gate365_view_widget_FontableEditText_font);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public FontableAutoCompleteTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
        UiUtil.setCustomFont(this,context,attrs,
                R.styleable.com_travel_gate365_view_widget_FontableEditText,
                R.styleable.com_travel_gate365_view_widget_FontableEditText_font);
	}

}
