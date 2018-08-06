package com.example.test.programmingmama.Di.Details_Fragment_Feature;

import com.example.test.programmingmama.Di.Component.AppComponent;
import com.example.test.programmingmama.View.Fragment.SlideFragment.DetailsFragment;
import dagger.Component;

@Component(modules = DetailsFragmentModule.class, dependencies = AppComponent.class)
@DetailsFragmentScope
public interface DetailsFragmentComponent {
    void injectDetails(DetailsFragment detailsFragment);
}
