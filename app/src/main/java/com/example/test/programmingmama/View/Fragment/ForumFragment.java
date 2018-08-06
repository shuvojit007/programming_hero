package com.example.test.programmingmama.View.Fragment;

import android.content.Context;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.Adapter.SectionsPagerAdapter;


public class ForumFragment extends Fragment {

    View rootView;

    private TabLayout tb;
    private ViewPager vp;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    Context cn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.forum_fragment, container, false);
        cn = this.getContext();
        init();
        return rootView;
    }

    private void init() {
        tb = rootView.findViewById(R.id.forum_tabs);
        vp = rootView.findViewById(R.id.forum_viewpager);
    }

    private PagerAdapter buildAdapter() {
        return ( new SectionsPagerAdapter(getActivity(),getChildFragmentManager()));

    }

    @Override
    public void onStart() {
        super.onStart();
        vp.setAdapter(buildAdapter());
        tb.setupWithViewPager(vp);
    }
}