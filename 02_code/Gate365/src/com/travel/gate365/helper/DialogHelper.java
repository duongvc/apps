package com.travel.gate365.helper;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public final class DialogHelper {

	private static AlertDialog sAlert = null;

	public static void alert(Context pContext, int pTitleResId, int pMessageResId, OnClickListener pHandler) {
		sAlert = buildAlert(pContext, pTitleResId, pMessageResId, pHandler).create();
		sAlert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		sAlert.show();
	}

	public static void alert(Context pContext, String pTitle, String pMessage) {
		sAlert = buildAlert(pContext, pTitle, pMessage, null).create();
		sAlert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		sAlert.show();
	}

	private static AlertDialog.Builder buildAlert(Context pContext, String pTitle, String pMessage, OnClickListener pHandler) {
		Builder builder = new Builder(pContext).setTitle(pTitle).setMessage(pMessage).setCancelable(true);

		OnClickListener handler = pHandler;
		if (handler == null) {
			handler = new OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
					sAlert = null;
				}
			};
		}

		builder.setNegativeButton(android.R.string.ok, handler);
		return builder;
	}

	private static Builder buildAlert(Context pContext, int pTitleResId, int pMessageResId, OnClickListener pHandler) {
		return buildAlert(pContext, pContext.getString(pTitleResId), pContext.getString(pMessageResId), pHandler);
	}

	public static AlertDialog yesNoAlert(Context pContext, String pTitle, String pMessage, String pPositivebutton, String pNegativeButton,
			OnClickListener pPositiveHandler, OnClickListener pNegativeHandler) {

		Builder builder = new Builder(pContext).setTitle(pTitle).setMessage(pMessage).setCancelable(true);

		builder.setNegativeButton(pNegativeButton, pNegativeHandler);
		builder.setPositiveButton(pPositivebutton, pPositiveHandler);

		sAlert = builder.create();
		sAlert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		sAlert.show();
		return sAlert;
	}

	public static void alert(Context pContext, int pTitleResId, int pMessageResId) {
		alert(pContext, pTitleResId, pMessageResId, null);
	}
}
