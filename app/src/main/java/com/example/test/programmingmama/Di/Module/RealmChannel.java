package com.example.test.programmingmama.Di.Module;


import com.example.test.programmingmama.Di.Scope.AppScope;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class RealmChannel {
    @Provides
    @AppScope
    Realm ProvideRealm(RealmConfiguration realmConfiguration){
               return Realm.getInstance(realmConfiguration);
          }

    @Provides
    @AppScope
    RealmConfiguration ProvideConfiguration (){
                return  new RealmConfiguration.Builder()
                    .name("programmingmama.realm")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            }
}
