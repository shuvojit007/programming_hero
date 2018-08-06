package com.example.test.programmingmama.Di.Community_Fragment_feature;

import com.example.test.programmingmama.View.Fragment.Community;


import dagger.Module;

@Module
public class CommunityFragmentModule {
    private final Community community;
    public CommunityFragmentModule(Community community){
        this.community = community;
    }
}
