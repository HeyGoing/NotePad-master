package com.example.android.notepad.alert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Toast.makeText(context, "Master,This is NotePad", Toast.LENGTH_LONG).show();
		intent.setClass(context, AlertDialogActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   //�������ӣ����򱨴�
		context.startActivity(intent);
	}
}
