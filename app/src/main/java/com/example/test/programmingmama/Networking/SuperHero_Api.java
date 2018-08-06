package com.example.test.programmingmama.Networking;

import com.example.test.programmingmama.Model.Achievement;
import com.example.test.programmingmama.Model.NewModel.NewHome;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface SuperHero_Api {

    @GET("16t7ke")
    Observable<List<Achievement>> getAcievementSuperHeroList();
}
