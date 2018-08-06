package com.example.test.programmingmama.Di.Community_Fragment_feature;

import com.example.test.programmingmama.Di.Component.AppComponent;
import com.example.test.programmingmama.View.Fragment.Community;

import dagger.Component;

@Component(modules = CommunityFragmentModule.class, dependencies = AppComponent.class)
@CommunityFragmentScope
public interface CommunityFragmentComponent {
    void injectCommunity(Community community);
}
