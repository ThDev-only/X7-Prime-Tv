package com.ultra.vision.debug;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.ultra.vision.debug.connection.DebugConnection;
import java.io.InputStream;

public class DebugActivity extends Activity {
	String[] exceptionType = {
			"StringIndexOutOfBoundsException",
			"IndexOutOfBoundsException",
			"ArithmeticException",
			"NumberFormatException",
			"ActivityNotFoundException"
	};
	String[] errMessage= {
			"Invalid string operation\n",
			"Invalid list operation\n",
			"Invalid arithmetical operation\n",
			"Invalid toNumber block operation\n",
			"Invalid intent operation"
	};
    
    Context context = DebugActivity.this;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String errMsg = "";
		String madeErrMsg = "";
		if(intent != null){
			errMsg = intent.getStringExtra("error");
			String[] spilt = errMsg.split("\n");
			//errMsg = spilt[0];
			try {
				for (int j = 0; j < exceptionType.length; j++) {
					if (spilt[0].contains(exceptionType[j])) {
						madeErrMsg = errMessage[j];
						int addIndex = spilt[0].indexOf(exceptionType[j]) + exceptionType[j].length();
						madeErrMsg += spilt[0].substring(addIndex, spilt[0].length());
						break;
					}
				}
				if(madeErrMsg.isEmpty()) madeErrMsg = errMsg;
			}catch(Exception e){}
		}
        
       // sendBug(madeErrMsg);
        
        AlertDialog.Builder bld = new AlertDialog.Builder(context);
	        	bld.setTitle("Ocorreu um erro inesperado!");
	        	bld.setMessage(madeErrMsg);
	        		bld.create().show();
                    bld.setNeutralButton("Fechar Aplicativo", new DialogInterface.OnClickListener() {
		        @Override
			public void onClick(DialogInterface dialog, int which) {
				finishAndRemoveTask();
			}
		});
            
    }
    
    private void sendBug(String bug){
        DebugConnection conn = new DebugConnection(bug, new DebugConnection.DebugConnectionEvent(){
            @Override
            public void response(String s) {
                // TODO: Implement this method
                AlertDialog.Builder bld = new AlertDialog.Builder(context);
	        	bld.setTitle("Ocorreu um erro inesperado!");
	        	bld.setMessage(s);
	        		bld.create().show();
                    bld.setNeutralButton("Fechar Aplicativo", new DialogInterface.OnClickListener() {
		        @Override
			public void onClick(DialogInterface dialog, int which) {
				finishAndRemoveTask();
			}
		});
            }
            
        });
    }
}
