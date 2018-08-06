package com.example.test.programmingmama.Di.Details_Fragment_Feature;

import com.example.test.programmingmama.View.Fragment.SlideFragment.DetailsFragment;

import dagger.Module;

@Module
public class DetailsFragmentModule {
    private final DetailsFragment detailsFragment;
    public DetailsFragmentModule(DetailsFragment detailsFragment){
        this.detailsFragment = detailsFragment;
    }
}
