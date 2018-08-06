package com.example.test.programmingmama.Di.Module;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.example.test.programmingmama.Di.ApplicationContext;
import com.example.test.programmingmama.Utils.NertworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module(includes = ContextChannel.class)
public class OkHttpClientChannel {
    @Provides
    public OkHttpClient okHttpClient( @ApplicationContext Context context,Cache cache, HttpLoggingInterceptor httpLoggingInterceptor){
        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    if (!NertworkUtils.haveNetworkConnection(context))
                    {
                        CacheControl cacheControl = new CacheControl.Builder()
                                .maxStale( 7, TimeUnit.DAYS )
                                .build();
                        request = request.newBuilder()
                                .cacheControl( cacheControl )
                                .build();
                    }
                    return chain.proceed( request );
                })
                .addNetworkInterceptor(chain -> {
                    Response response = chain.proceed( chain.request() );

                    // re-write response header to force use of cache
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxAge( 1, TimeUnit.MINUTES )
                            .build();

                    return response.newBuilder()
                            .header( "Cache-Control", cacheControl.toString() )
                            .build();
                })
                .cache(cache)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    @Provides
    public Cache cache(File cacheFile){
        return new Cache(cacheFile, 10 * 1000 * 1000); //10 MB
    }

    @Provides
    public File file( @ApplicationContext Context context){
        File file = new File(context.getCacheDir(), "HttpCache");
        file.mkdirs();
        return file;
    }

    @Provides
    public HttpLoggingInterceptor httpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.d(message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

}
