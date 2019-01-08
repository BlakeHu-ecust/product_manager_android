package com.product.productmanager.Other;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.product.productmanager.R;
import com.product.productmanager.View.Loading_view;

import java.io.FileOutputStream;

import static android.content.Context.MODE_APPEND;

public class ToolClass {
    private static Loading_view loading;
    static public void showMessage(final String message, final Context context){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            }
        });

}

    static public void showProgress(Context context){
        loading = new Loading_view(context,R.style.CustomDialog);
        loading.show();
    }

    static public void progressDismisss(){
        loading.dismiss();
    }

}

