package com.product.productmanager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.product.productmanager.Adapter.MyFragmentPagerAdapter;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;

    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel;
    private RadioButton rb_message;
    private RadioButton rb_better;
    private ViewPager mVp;
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
        mVp = (ViewPager) findViewById(R.id.vp);
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
                mVp.setCurrentItem(PAGE_TWO);
                break;
            case R.id.rb_3:
                mVp.setCurrentItem(PAGE_THREE);
                break;
        }
    }

}
