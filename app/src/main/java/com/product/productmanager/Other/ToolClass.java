package com.product.productmanager.Other;

import android.content.Context;
import android.widget.Toast;

public class ToolClass {
    static public void showMessage(String message, Context context){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
