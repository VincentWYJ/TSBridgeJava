package com.tsbridge.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tsbridge.R;
import com.tsbridge.adapter.MainAdapter;
import com.tsbridge.fragment.BulletinFragment;
import com.tsbridge.fragment.SendFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String[] mTabNames = null;
    private static int mPageCount = 0;

    private ViewPager mViewPager = null;
    private TabLayout mTabLayout = null;

    private List<Fragment> mFragments = null;
    private MainAdapter mainAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initParams();
        initViews();
    }

    private void initParams() {
        mTabNames = getResources().getStringArray(R.array.title_array);
        mPageCount = mTabNames.length;
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        mFragments = new ArrayList<>();
        mFragments.add(new BulletinFragment());
        mFragments.add(new SendFragment());

        mainAdapter = new MainAdapter(getSupportFragmentManager(), mTabNames, mFragments);
        mViewPager.setAdapter(mainAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
