package com.example.test.programmingmama.Model.Challenge3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Challenge3 extends RealmObject {
    @SerializedName("pchallenge3")
    @Expose
    private RealmList<Pchallenge3> pchallenge = null;

    public RealmList<Pchallenge3> getPchallenge() {
        return pchallenge;
    }

    public void setPchallenge(RealmList<Pchallenge3> pchallenge) {
        this.pchallenge = pchallenge;
    }

    public Challenge3(RealmList<Pchallenge3> pchallenge) {
        this.pchallenge = pchallenge;
    }

    public Challenge3() {
    }

    @Override
    public String toString() {
        return "Challenge2{" +
                "pchallenge=" + pchallenge +
                '}';
    }
}
