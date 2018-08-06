package com.example.test.programmingmama.Model.Challenge2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Challenge2 extends RealmObject{
    @SerializedName("pchallenge")
    @Expose
    private RealmList<Pchallenge> pchallenge = null;

    public RealmList<Pchallenge> getPchallenge() {
        return pchallenge;
    }

    public void setPchallenge(RealmList<Pchallenge> pchallenge) {
        this.pchallenge = pchallenge;
    }

    public Challenge2(RealmList<Pchallenge> pchallenge) {
        this.pchallenge = pchallenge;
    }

    public Challenge2() {
    }

    @Override
    public String toString() {
        return "Challenge2{" +
                "pchallenge=" + pchallenge +
                '}';
    }
}
