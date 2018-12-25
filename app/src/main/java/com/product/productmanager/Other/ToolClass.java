package com.product.productmanager.Other;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.product.productmanager.R;
import com.product.productmanager.View.Loading_view;

public class ToolClass {
    private static Loading_view loading;
    static public void showMessage(String message, Context context){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    static public void showProgress(Context context){
        loading = new Loading_view(context,R.style.CustomDialog);
        loading.show();
    }

    static public void progressDismisss(){
        loading.dismiss();
    }
}

