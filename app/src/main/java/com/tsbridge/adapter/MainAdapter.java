package com.tsbridge.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class MainAdapter extends FragmentStatePagerAdapter {
    private String[] mTabNames;
    private List<Fragment> mFragments;

    public MainAdapter(FragmentManager fragmentManager, String[] tabNames, List<Fragment> fragments) {
        super(fragmentManager);

        mTabNames = tabNames;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    //设置TabBar标题名，如果要加入图标得另作处理
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabNames[position];
    }
}
