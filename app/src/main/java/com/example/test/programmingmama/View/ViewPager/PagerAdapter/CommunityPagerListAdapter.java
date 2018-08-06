package com.example.test.programmingmama.View.ViewPager.PagerAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.test.programmingmama.Model.NewModel.List;
import com.example.test.programmingmama.View.Fragment.SlideFragment.DetailsFragment;
import com.example.test.programmingmama.View.Fragment.SlideFragment.FillinTheBlanks2;
import com.example.test.programmingmama.View.Fragment.SlideFragment.FillinTheBlanksCondition;
import com.example.test.programmingmama.View.Fragment.SlideFragment.FillinthBlanks;

import com.example.test.programmingmama.View.Fragment.SlideFragment.FillintheblanksMath;
import com.example.test.programmingmama.View.Fragment.SlideFragment.QuizFrag;

public class CommunityPagerListAdapter extends FragmentStatePagerAdapter {
    private Context ctx;

    private Fragment[] fragments;
    int count;
    public static List lm;
    public static int id;

    public CommunityPagerListAdapter(Context ctx, FragmentManager fm, int size, List lm, int id) {
        super(fm);
        this.ctx = ctx;
        count = size;
        this.lm = lm;
        this.id = id;
        fragments = new Fragment[count];


    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (lm.getMdes().get(position).getQtype().equals("normal") && lm.getMdes().get(position).getBlankstype().equals("null")) {

            String ptxt = "";
            if(lm.getMdes().get(position).getPtxt()!=null){
                ptxt =lm.getMdes().get(position).getPtxt();
            }

            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setItem2(
                    lm.getMdes().get(position).getName(),
                    id,
                    lm.getMdes().get(position).getDes01(),
                    lm.getMdes().get(position).getDes02(),
                    lm.getMdes().get(position).getDes03(),
                    lm.getMdes().get(position).getLink01(),
                    lm.getMdes().get(position).getLink02(),
                    lm.getMdes().get(position).getCode(),
                    lm.getMdes().get(position).getOutput(),
                    lm.getMdes().get(position).getFinish(),

                    lm.getFmodule(),
                    lm.getMdes().get(position).getId(),
                    lm.getMdes().get(position).getStatus(),
                    lm.getId(),
                    lm.getMdes().get(position).getBookmark(),
                    lm.getMdes().get(position).getPopup(),ptxt);

            fragment = detailsFragment;

        } else if (lm.getMdes().get(position).getQtype().equals("quiz")) {
            QuizFrag quizFrag = new QuizFrag();

            quizFrag.setItem2(id,
                    lm.getMtitle(),
                    lm.getMdes().get(position).getQuiz(),
                    lm.getMdes().get(position).getFinish(),
                    lm.getFmodule(),
                    lm.getMdes().get(position).getId(),
                    lm.getMdes().get(position).getStatus(),
                    lm.getId(),
                    lm.getMdes().get(position).getBid(),
                    lm.getMdes().get(position).getBtype());
            fragment = quizFrag;


        } else if (lm.getMdes().get(position).getBlankstype().equals("print")) {
            FillinthBlanks fillinthBlanks = new FillinthBlanks();
            fillinthBlanks.setItem2(id,
                    lm.getMdes().get(position).getName(),
                    lm.getMdes().get(position).getDes01(),
                    lm.getMdes().get(position).getBlanks(),
                    lm.getMdes().get(position).getFinish(),
                    lm.getFmodule(),
                    lm.getMdes().get(position).getId(),
                    lm.getMdes().get(position).getStatus(),
                    lm.getId());
            fragment = fillinthBlanks;

        } else if (lm.getMdes().get(position).getBlankstype().equals("printsln")) {
            FillinTheBlanks2 fillinthBlanks2 = new FillinTheBlanks2();
            fillinthBlanks2.setItem2(
                    lm.getMdes().get(position).getName(),
                    lm.getMdes().get(position).getDes01(),
                    lm.getMdes().get(position).getBlanks(),
                    lm.getMdes().get(position).getFinish(),
                    lm.getFmodule(),
                    id,
                    lm.getMdes().get(position).getId(),
                    lm.getMdes().get(position).getStatus(),
                    lm.getId(),
                    lm.getMdes().get(position).getBid(),
                    lm.getMdes().get(position).getBtype());
            fragment = fillinthBlanks2;

       } else if (lm.getMdes().get(position).getBlankstype().equals("condition")) {
            FillinTheBlanksCondition fillinTheBlanksCondition = new FillinTheBlanksCondition();
            fillinTheBlanksCondition.setItem2(
                    lm.getMdes().get(position).getName(),
                    lm.getMdes().get(position).getDes01(),
                    lm.getMdes().get(position).getBlanks(),
                    lm.getMdes().get(position).getFinish(),
                    lm.getFmodule(),
                    id,
                    lm.getMdes().get(position).getId(),
                    lm.getMdes().get(position).getStatus(),
                    lm.getId(),
                    lm.getMdes().get(position).getBid(),
                    lm.getMdes().get(position).getBtype());
            fragment = fillinTheBlanksCondition;

        } else if (lm.getMdes().get(position).getBlankstype().equals("compute")) {
            FillintheblanksMath fillintheblanksMath = new FillintheblanksMath();
            fillintheblanksMath.setItem2(
                    lm.getMdes().get(position).getName(),
                    lm.getMdes().get(position).getDes01(),
                    lm.getMdes().get(position).getBlanks(),
                    lm.getMdes().get(position).getFinish(),
                    lm.getFmodule(),
                    id,
                    lm.getMdes().get(position).getId(),
                    lm.getMdes().get(position).getStatus(),
                    lm.getId(),
                    lm.getMdes().get(position).getBid(),
                    lm.getMdes().get(position).getBtype());
            fragment = fillintheblanksMath;

            Log.d("List Module", "compute: "+    lm.getMdes().get(position).getFinish());
        }


        if (fragments[position] == null) {
            fragments[position] = fragment;
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return count;
    }
}
