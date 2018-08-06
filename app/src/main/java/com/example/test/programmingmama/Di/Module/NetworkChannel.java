package com.example.test.programmingmama.Di.Module;


import com.example.test.programmingmama.Di.Scope.AppScope;
import com.example.test.programmingmama.Networking.Community_Api;
import com.example.test.programmingmama.Networking.SuperHero_Api;
import com.example.test.programmingmama.Networking.URL_MANAGER;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = OkHttpClientChannel.class)
public class NetworkChannel {




    @Provides
    public Community_Api getCommunitySection(Retrofit retrofit){
        return retrofit.create(Community_Api.class);
    }

    @Provides
    public SuperHero_Api getSuperhero(Retrofit retrofit){
        return retrofit.create(SuperHero_Api.class);
    }


    @AppScope
    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient,
                             GsonConverterFactory gsonConverterFactory,
                             Gson gson){
        return new Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(URL_MANAGER.API_HOST)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    @Provides
    public Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }
}
