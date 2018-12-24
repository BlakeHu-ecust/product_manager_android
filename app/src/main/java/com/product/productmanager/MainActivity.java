package com.product.productmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class MainActivity extends Activity {

    @BindView(R.id.company_edit)
    EditText companyEdit;
    @BindView(R.id.company_button)
    Button companyButton;
    @BindView(R.id.company_rootView)
    LinearLayout companyRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Flowable.just("Hello world")
                .subscribe(new Consumer<String>() {
                    @Override public void accept(String s) {
                        System.out.println(s);
                    }
                });
    }

    @OnClick({R.id.company_button, R.id.company_rootView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.company_button:
//                if (companyEdit.getText().toString().length() == 0){
////                    ToolClass.showMessage("请输入企业码",MainActivity.this);
////                    return;
////                }
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.company_rootView:
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                break;
        }
    }
}
