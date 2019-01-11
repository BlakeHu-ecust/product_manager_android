package com.product.productmanager.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.product.productmanager.HomeActivity;
import com.product.productmanager.Tab.TabFragment1;
import com.product.productmanager.Tab.TabFragment2;
import com.product.productmanager.Tab.TabFragment3;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 3;
    public TabFragment1 myFragment1;
    public TabFragment2 myFragment2;
    public TabFragment3 myFragment3;


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        myFragment1 = new TabFragment1();
        myFragment2 = new TabFragment2();
        myFragment3 = new TabFragment3();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case HomeActivity.PAGE_ONE:
                fragment = myFragment1;
                break;
            case HomeActivity.PAGE_TWO:
                fragment = myFragment2;
                break;
            case HomeActivity.PAGE_THREE:
                fragment = myFragment3;
                break;
                default:
                    break;
        }
        return fragment;
    }

}
