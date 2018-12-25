package com.product.productmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.product.productmanager.Adapter.MyFragmentPagerAdapter;
import com.product.productmanager.View.MyViewPager;
import com.product.productmanager.scanner.CaptureActivity;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;

    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;

    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel;
    private RadioButton rb_message;
    private RadioButton rb_better;
    private MyViewPager mVp;
    private MyFragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        rg_tab_bar = findViewById(R.id.rg_tab_bar);
        rb_channel = findViewById(R.id.rb_1);
        rb_message = findViewById(R.id.rb_2);
        rb_better = findViewById(R.id.rb_3);
        rg_tab_bar.setOnCheckedChangeListener(this);
        mVp = (MyViewPager) findViewById(R.id.vp);
        mVp.setAdapter(mAdapter);
        //ViewPager监听
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 2) {
                    switch (mVp.getCurrentItem()) {
                        case PAGE_ONE:
                            rb_channel.setChecked(true);
                            break;
                        case PAGE_TWO:
                            rb_message.setChecked(true);
                            break;
                        case PAGE_THREE:
                            rb_better.setChecked(true);
                            break;
                    }
                }
            }
        });
        rb_channel.setChecked(true);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_1:
                mVp.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rb_2:
                //mVp.setCurrentItem(PAGE_TWO);
                if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    //扫码
                    goScan();
                }

                break;
            case R.id.rb_3:
                mVp.setCurrentItem(PAGE_THREE);
                break;
        }
    }

    private void goScan(){
        Intent intent=new Intent(HomeActivity.this,CaptureActivity.class);
        startActivityForResult(intent,101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 101:
                if(resultCode==RESULT_OK){
                    /**
                     * 扫码成功
                     */
                    String s = data.getStringExtra("qrCode");
                    Log.d("QRCode",s);

                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //扫码
                    goScan();
                } else {
                    Toast.makeText(this, "你拒绝了权限申请，无法打开相机扫码哟！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
