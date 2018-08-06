package com.example.test.programmingmama.Networking;


import com.example.test.programmingmama.Model.NewModel.NewHome;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Community_Api {
    //@GET("h95ce")
    //@GET("mmiri")
    //@GET("l0nfi")
    //@GET("159dzy")
    //@GET("tgd2m")
    //@GET("zrrsu")
    //@GET("c2pxa")
    //@GET("16ypk6")
    //@GET("th1ri")
    //@GET("1du2ni")
    //@GET("1aca3q")
    //@GET("15myfa")
    //@GET("7tqza")
    //@GET("ccwfq")
    //@GET("ejkzy")
    //@GET("1bjx62")
    //@GET("hvc16")
    //@GET("tmn8a")
    //@GET("1gi6lu")
    //@GET("15c1zi")
    //@GET("kbe3y")
    //@GET("1cba26")
    //@GET("11oowu")
    //@GET("u5qcy")
    //@GET("ddl4q")
    //@GET("179kk0")
    @GET("mkpqo")



    Observable<List<NewHome>> GetCommunitySection();

}
