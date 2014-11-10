package com.travel.gate365.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.travel.gate365.R;

public class FontableWebView extends WebView {

	private boolean isPageLoaded = false;
	
	public FontableWebView(Context context) {
		super(context);
		setBackgroundColor(0x00000000);
		setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);		
	}

	public FontableWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundColor(0x00000000);
		setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		
        UiUtil.setCustomFont(this,context,attrs,
                R.styleable.com_travel_gate365_view_widget_FontableWebView,
                R.styleable.com_travel_gate365_view_widget_FontableWebView_font);
		
	}

	public FontableWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setBackgroundColor(0x00000000);
		setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);		
        UiUtil.setCustomFont(this,context,attrs,
                R.styleable.com_travel_gate365_view_widget_FontableWebView,
                R.styleable.com_travel_gate365_view_widget_FontableWebView_font);
	}

	@Override
	public void loadUrl(String url) {
		if(!isPageLoaded){
			setWebViewClient(webClient);
			super.loadUrl(url);
		}
	}
	
	private WebViewClient webClient = new WebViewClient(){
		
		@Override
		public void onPageFinished(WebView view, String url) {
			isPageLoaded = true;
			setBackgroundColor(0x00000000);
			setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);		
		};
		
		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			isPageLoaded = true;
		};
		
	};	
	
}
