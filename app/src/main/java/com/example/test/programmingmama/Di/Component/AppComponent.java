package com.example.test.programmingmama.Di.Component;


import com.example.test.programmingmama.Di.Module.NetworkChannel;
import com.example.test.programmingmama.Di.Module.PicassoChannel;
import com.example.test.programmingmama.Di.Module.RealmChannel;
import com.example.test.programmingmama.Di.Scope.AppScope;
import com.example.test.programmingmama.Networking.Community_Api;
import com.example.test.programmingmama.Networking.SuperHero_Api;
import com.squareup.picasso.Picasso;

import dagger.Component;
import io.realm.Realm;

@AppScope
@Component(modules = {RealmChannel.class,NetworkChannel.class,PicassoChannel.class})
public interface AppComponent {

    Community_Api getCommunitySection();
    SuperHero_Api getSuperhero();
    Picasso getPicasso();
    Realm GetRealm();
}
