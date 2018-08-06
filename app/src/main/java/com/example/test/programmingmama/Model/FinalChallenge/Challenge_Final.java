package com.example.test.programmingmama.Model.FinalChallenge;

import com.example.test.programmingmama.Model.Challenge3.Pchallenge3;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Challenge_Final extends RealmObject {
    @SerializedName("finalchallenge")
    @Expose
    private RealmList<FinalChallenge> pchallenge = null;

    public RealmList<FinalChallenge> getPchallenge() {
        return pchallenge;
    }

    public void setPchallenge(RealmList<FinalChallenge> pchallenge) {
        this.pchallenge = pchallenge;
    }

    public Challenge_Final(RealmList<FinalChallenge> pchallenge) {
        this.pchallenge = pchallenge;
    }

    public Challenge_Final() {
    }

    @Override
    public String toString() {
        return "Challenge2{" +
                "pchallenge=" + pchallenge +
                '}';
    }
}
