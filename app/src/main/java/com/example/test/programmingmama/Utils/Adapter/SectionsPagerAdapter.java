package com.example.test.programmingmama.Utils.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.test.programmingmama.View.Fragment.Forum_Fragment.Forum;
import com.example.test.programmingmama.View.Fragment.Forum_Fragment.My_Forum;
import com.example.test.programmingmama.View.Fragment.Forum_Fragment.My_Question;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    Context ctxt=null;
    public SectionsPagerAdapter(Context ctxt,FragmentManager fm) {
        super(fm);
        this.ctxt=ctxt;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Forum();
            case 1:
                return new My_Forum();
            case 2:
                return new My_Question();

         default:
             return  null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "All FORUM";
            case 1:
                return "MY FORUM";
            case 2:
                return "MY QUESTION";
            default:
                return  null;
        }
    }
}
