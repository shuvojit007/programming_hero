package com.example.test.programmingmama;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.test.programmingmama.Di.Component.AppComponent;
import com.example.test.programmingmama.Di.Component.DaggerAppComponent;
import com.example.test.programmingmama.Di.Module.ContextChannel;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;
import com.onesignal.OneSignal;


import io.github.kbiakov.codeview.classifier.CodeProcessor;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class App extends Application{
    private static App sInstance;
    private AppComponent appComponent;
    private DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    public static App get(Activity activity){
        return (App)activity.getApplication();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }


    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("programmingmama.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);

        appComponent= DaggerAppComponent.builder()
                .contextChannel(new ContextChannel(this))
                .build();


        new Instabug.Builder(this, "36c6b8bc64cbceadb66b4f2effc68774")
                .setInvocationEvent(InstabugInvocationEvent.NONE)
                .build();

        Instabug.setPromptOptionsEnabled(false, true, true);

        // OneSignalUtils Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();




   }
    public static App getInstance() {
        if (sInstance == null) {
            sInstance = new App();
        }
        return sInstance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
