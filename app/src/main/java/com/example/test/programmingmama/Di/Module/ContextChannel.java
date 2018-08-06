package com.example.test.programmingmama.Di.Module;

import android.content.Context;

import com.example.test.programmingmama.Di.ApplicationContext;
import com.example.test.programmingmama.Di.Scope.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextChannel {
    Context cn;

    public ContextChannel(Context cn) {
        this.cn = cn;
    }
    @ApplicationContext
    @AppScope
    @Provides
    public Context context(){
        return cn.getApplicationContext();
    }
}
