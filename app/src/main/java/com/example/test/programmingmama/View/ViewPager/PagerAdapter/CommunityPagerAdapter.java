package com.example.test.programmingmama.View.ViewPager.PagerAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.test.programmingmama.Model.NewModel.Home;
import com.example.test.programmingmama.Utils.CustomViewPager;
import com.example.test.programmingmama.View.Fragment.SlideFragment.DetailsFragment;
import com.example.test.programmingmama.View.Fragment.SlideFragment.FillinTheBlanks2;
import com.example.test.programmingmama.View.Fragment.SlideFragment.FillinTheBlanksCondition;
import com.example.test.programmingmama.View.Fragment.SlideFragment.FillinthBlanks;
import com.example.test.programmingmama.View.Fragment.SlideFragment.FillintheblanksMath;
import com.example.test.programmingmama.View.Fragment.SlideFragment.QuizFrag;

public class CommunityPagerAdapter extends FragmentStatePagerAdapter {
    private Context ctx;

    int fragcount;
    private Fragment[] fragments;
    int id;
    ViewPager viewPager;
    Home home;

    public CommunityPagerAdapter(Context ctx, FragmentManager fm, int size, Home home, CustomViewPager viewPager) {
        super(fm);
        this.ctx = ctx;
        id = size;
        this.home = home;
        fragments = new Fragment[id];
        this.viewPager = viewPager;


    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if (home.getDes()
                .get(position)
                .getQtype()
                .equals("normal") &&
                home.getDes()
                        .get(position)
                        .getBlankstype()
                        .equals("null")) {
            String ptxt = "";
            if(home.getDes().get(position).getPtxt()!=null){
                ptxt =home.getDes().get(position).getPtxt();
            }

            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setItem(home.getDes().get(position).getName(),
                    home.getId(),
                    home.getDes().get(position).getDes01(),
                    home.getDes().get(position).getDes02(),
                    home.getDes().get(position).getDes03(),
                    home.getDes().get(position).getLink01(),
                    home.getDes().get(position).getLink02(),
                    home.getDes().get(position).getCode(),
                    home.getDes().get(position).getOutput(),
                    home.getDes().get(position).getFinish(),
                    home.getDes().get(position).getStatus(),
                    home.getDes().get(position).getId(),
                    home.getDes().get(position).getBookmark(),
                    home.getDes().get(position).getPopup(),ptxt
                );

            fragment = detailsFragment;

        } else if (home.getDes().get(position).getQtype().equals("quiz")) {
            QuizFrag quizFrag = new QuizFrag();
            quizFrag.setItem(home.getTitle(),
                    home.getId(),
                    home.getDes().get(position).getQuiz(),
                    home.getDes().get(position).getFinish(),
                    home.getDes().get(position).getStatus(),
                    home.getDes().get(position).getId(),
                    home.getDes().get(position).getBid(),
                    home.getDes().get(position).getBtype());
            fragment = quizFrag;

        } else if (home.getDes().get(position).getBlankstype().equals("print")) {
            FillinthBlanks fillinthBlanks = new FillinthBlanks();
            fillinthBlanks.setItem(
                    home.getId(),
                    home.getDes().get(position).getName(),
                    home.getDes().get(position).getDes01(),
                    home.getDes().get(position).getBlanks(),
                    home.getDes().get(position).getFinish(),
                    home.getDes().get(position).getStatus(),
                    home.getDes().get(position).getId());
            fragment = fillinthBlanks;
        }

        else if (home.getDes().get(position).getBlankstype().equals("printsln")) {
            FillinTheBlanks2 fillinthBlanks2 = new FillinTheBlanks2();
            fillinthBlanks2.setItem(
                    home.getId(),
                    home.getDes().get(position).getName(),
                    home.getDes().get(position).getDes01(),
                    home.getDes().get(position).getBlanks(),
                    home.getDes().get(position).getFinish(),
                    home.getDes().get(position).getStatus(),
                    home.getDes().get(position).getId(),
                    home.getDes().get(position).getBid(),
                    home.getDes().get(position).getBtype());
            fragment = fillinthBlanks2;
        }
        else if (home.getDes().get(position).getBlankstype().equals("condition")) {
            FillinTheBlanksCondition fillinTheBlanksCondition = new FillinTheBlanksCondition();
            fillinTheBlanksCondition.setItem(
                    home.getId(),
                    home.getDes().get(position).getName(),
                    home.getDes().get(position).getDes01(),
                    home.getDes().get(position).getBlanks(),
                    home.getDes().get(position).getFinish(),
                    home.getDes().get(position).getStatus(),
                    home.getDes().get(position).getId(),
                    home.getDes().get(position).getBid(),
                    home.getDes().get(position).getBtype());
            fragment = fillinTheBlanksCondition;
        }


        else if (home.getDes().get(position).getBlankstype().equals("compute")) {
            FillintheblanksMath fillintheblanksMath = new FillintheblanksMath();
            fillintheblanksMath.setItem(
                    home.getId(),
                    home.getDes().get(position).getName(),
                    home.getDes().get(position).getDes01(),
                    home.getDes().get(position).getBlanks(),
                    home.getDes().get(position).getFinish(),
                    home.getDes().get(position).getStatus(),
                    home.getDes().get(position).getId(),
                    home.getDes().get(position).getBid(),
                    home.getDes().get(position).getBtype());
            fragment = fillintheblanksMath;
        }


        if (fragments[position] == null) {
            fragments[position] = fragment;
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return id;
    }
}
