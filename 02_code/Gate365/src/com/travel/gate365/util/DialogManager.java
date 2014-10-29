package com.travel.gate365.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public final class DialogManager {

	private static AlertDialog sAlert = null;

	/**
	 * Popups a alert with a given message.
	 * @param pContext
	 * @param pMessageResId
	 */
	public static void alert(Context pContext, int pTitleResId, int pMessageResId, DialogInterface.OnClickListener pHandler){
		sAlert = new AlertDialog(pContext){
			
		    protected void onCreate(Bundle savedInstanceState){
		        super.onCreate(savedInstanceState);
		    }			
		};
		DialogInterface.OnClickListener handler = pHandler;
		if(handler == null){
			handler = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
					sAlert = null;
				}
			};
		}
		sAlert.setTitle(pTitleResId);
		sAlert.setMessage(pContext.getString(pMessageResId));
		sAlert.setButton(pContext.getString(android.R.string.ok), handler);
		sAlert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		sAlert.show();
	}
	
	/**
	 * Popups a alert with a given message.
	 * @param pContext
	 * @param pMessageResId
	 */
	public static void alert(Context pContext, int pTitleResId, int pMessageResId, int iconResId, DialogInterface.OnClickListener pHandler){
		sAlert = new AlertDialog(pContext){
			
		    protected void onCreate(Bundle savedInstanceState){
		        super.onCreate(savedInstanceState);
		    }			
		};
		DialogInterface.OnClickListener handler = pHandler;
		if(handler == null){
			handler = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
					sAlert = null;
				}
			};
		}
		sAlert.setIcon(iconResId);
		sAlert.setTitle(pTitleResId);
		sAlert.setMessage(pContext.getString(pMessageResId));
		sAlert.setButton(pContext.getString(android.R.string.ok), handler);
		sAlert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		sAlert.show();
	}
	
	/**
	 * Popups a alert with a given message.
	 * @param pContext
	 * @param pMessage
	 */
	public static void alert(Context pContext, String pTitle, String pMessage, int iconResId, DialogInterface.OnClickListener pHandler){
		sAlert = new AlertDialog(pContext){
			
		    protected void onCreate(Bundle savedInstanceState){
		        super.onCreate(savedInstanceState);
		    }			
		};
		DialogInterface.OnClickListener handler = pHandler;
		if(handler == null){
			handler = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
					sAlert = null;
				}
			};
		}
		sAlert.setIcon(iconResId);
		sAlert.setTitle(pTitle);
		sAlert.setMessage(pMessage);
		sAlert.setButton(pContext.getString(android.R.string.ok), handler);
		sAlert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		sAlert.show();
	}
	
	/**
	 * 
	 * @param pContext
	 * @param pTitle
	 * @param pView
	 * @param iconResId
	 * @param pHandler
	 */
	public static AlertDialog alert(Context pContext, String pTitle, View pView, int iconResId, DialogInterface.OnClickListener pHandler){
		sAlert = new AlertDialog(pContext){
		    protected void onCreate(Bundle savedInstanceState){
		        super.onCreate(savedInstanceState);
		    }			
		};
		DialogInterface.OnClickListener handler = pHandler;
		if(handler == null){
			handler = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
					sAlert = null;
				}
			};
		}
		sAlert.setIcon(iconResId);
		sAlert.setTitle(pTitle);
		sAlert.setView(pView);
		sAlert.setButton(pContext.getString(android.R.string.ok), handler);
		sAlert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		sAlert.show();	
		return sAlert;
	}
	
	public static AlertDialog yesNoAlert(Context pContext, String pTitle, View pView, int pIconResId, String pPositiveButton, 
			String pNegativeButton, DialogInterface.OnClickListener pPositiveHandler, DialogInterface.OnClickListener pNegativeHandler) {
		sAlert = new AlertDialog(pContext){
		    protected void onCreate(Bundle savedInstanceState){
		        super.onCreate(savedInstanceState);
		    }			
		};
		
		sAlert.setIcon(pIconResId);
		sAlert.setTitle(pTitle);
		sAlert.setView(pView);
		sAlert.setButton(pPositiveButton, pPositiveHandler);
		sAlert.setButton(pNegativeButton, pNegativeHandler);
		sAlert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		sAlert.show();	
		return sAlert;
	}

	public static AlertDialog yesNoAlert(Context pContext, String pTitle, String pMessage, int pIconResId, String pPositivebutton, 
			String pNegativeButton, DialogInterface.OnClickListener pPositiveHandler, DialogInterface.OnClickListener pNegativeHandler) {
		sAlert = new AlertDialog(pContext){
		    protected void onCreate(Bundle savedInstanceState){
		        super.onCreate(savedInstanceState);
		    }			
		};
		
		sAlert.setIcon(pIconResId);
		sAlert.setTitle(pTitle);
		sAlert.setMessage(pMessage);
		sAlert.setButton(pPositivebutton, pPositiveHandler);
		sAlert.setButton2(pNegativeButton, pNegativeHandler);
		sAlert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		sAlert.show();	
		return sAlert;
	}
	
	public static void hide(){
		if(sAlert != null){
			try{
				if(sAlert.isShowing()){
					sAlert.cancel();
				}
			}catch(Exception e){
				Log.e("hide", e.getMessage(), e);
			}
			sAlert = null;
		}
	}
		
}
