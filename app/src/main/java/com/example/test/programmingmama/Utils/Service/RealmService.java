package com.example.test.programmingmama.Utils.Service;

import android.content.Context;
import android.util.Log;

import com.example.test.programmingmama.Model.Achievement;
import com.example.test.programmingmama.Model.AchievementFields;
import com.example.test.programmingmama.Model.Challenge2.Challenge2;
import com.example.test.programmingmama.Model.Challenge3.Challenge3;
import com.example.test.programmingmama.Model.FinalChallenge.Challenge_Final;
import com.example.test.programmingmama.Model.FinalChallenge.FinalChallenge;
import com.example.test.programmingmama.Model.NewModel.De;
import com.example.test.programmingmama.Model.NewModel.DeFields;
import com.example.test.programmingmama.Model.NewModel.Home;
import com.example.test.programmingmama.Model.NewModel.HomeFields;
import com.example.test.programmingmama.Model.NewModel.ListFields;
import com.example.test.programmingmama.Model.NewModel.Mde;
import com.example.test.programmingmama.Model.NewModel.MdeFields;
import com.example.test.programmingmama.Model.NewModel.NewHome;
import com.example.test.programmingmama.Model.pc1Model;
import com.example.test.programmingmama.Utils.PrefManager;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmService {



    //todo======== todo profile section  ========//
    public static Home FindModuleLast(){
        Realm realm =Realm.getDefaultInstance();
        Home hm =realm.where(Home.class)
                .equalTo(HomeFields.STATUS,"open")
                .findFirst();


        return  hm;
    }
    public static String  FindLast(int id){
        Realm realm =Realm.getDefaultInstance();
        Home hm =realm.where(Home.class)
                .equalTo(HomeFields.ID,id+1)
                .findFirst();



        return  hm.getTitle();
    }



    public static String FindSectionImage(int id){
        Realm realm =Realm.getDefaultInstance();
        Home hm =realm.where(Home.class)
                .equalTo(HomeFields.ID,id)
                .findFirst();

        return  hm.getSectionImage();
    }

    //todo======== super hero ========//
    public static void StoreAchievement(Achievement achievement) {
        Realm
                .getDefaultInstance()
                .executeTransaction(rr -> rr.
                        insertOrUpdate(achievement));

    }

    public static void UnlockSuperHero(int id) {
        Realm realm = Realm.getDefaultInstance();
        Achievement achievement = realm.where(Achievement.class)
                .equalTo(AchievementFields.ID, id)
                .findFirst();

        if (achievement != null) {
            realm.executeTransaction(realm1 -> {
                achievement.setActive("true");
            });

            realm.close();
        } else {
            realm.close();
        }
    }

    public static Achievement GetLatestAchievement() {
        Realm realm = Realm.getDefaultInstance();
        Achievement achievement = realm.where(Achievement.class)
                .equalTo(AchievementFields.ACTIVE, "true")
                .sort(AchievementFields.ID, Sort.DESCENDING)
                .findFirst();
        realm.close();
        if (achievement != null) {
            return achievement;
        }
        return null;
    }

    public static Achievement GetNExtAchievement(int id) {
        Realm realm = Realm.getDefaultInstance();
        Achievement achievement = realm.where(Achievement.class)
                .equalTo(AchievementFields.ID, id)
                .findFirst();
        realm.close();
        if (achievement != null) {
            return achievement;
        }
        return null;
    }


    public static List<Achievement> getAchievements() {
        Realm rr = Realm.getDefaultInstance();
        RealmResults<Achievement> achievements = rr.where(Achievement.class).findAll();
        rr.close();
        return achievements;
    }

    public static List<Achievement> getActiveAchievements() {
        Realm rr = Realm.getDefaultInstance();
        RealmResults<Achievement> achievements =
                rr
                        .where(Achievement.class)
                        .equalTo(AchievementFields.ACTIVE, "true").findAll();
        rr.close();
        return achievements;
    }

    public static Achievement findSuperHero(int id) {
        Realm rr = Realm.getDefaultInstance();
        Achievement achievement = rr.where(Achievement.class)
                .equalTo(AchievementFields.ID, id)
                .findFirst();
        rr.close();
        return achievement;
    }

    //todo======== super hero ========//


    public static List<pc1Model> getPC1Model(Realm rr) {
        RealmResults<pc1Model> lm = rr.where(pc1Model.class).findAll();
        return lm;
    }

    public static List<Home> getHome(Realm rr) {
        RealmResults<Home> hm = rr.where(Home.class).findAll();
        return hm;
    }



    //todo Profile section total module and achivements
    public static int TotalCompletedModule(Realm rr) {

        int result = rr.where(Home.class)
                .equalTo(HomeFields.STATUS, "completed")
                .findAll().size();

        return result;


    }

    public static int TotalCompleteAchivementModule(Realm rr) {

        int result = rr.where(Achievement.class)
                .equalTo(AchievementFields.ACTIVE, "true")
                .findAll().size();

        return result;


    }
    public static int TotalAchivementModule(Realm rr) {

        int result = rr.where(Achievement.class)
                .findAll().size();
        return result;


    }

    public static int TotaldModule(Realm rr) {

        int result = rr.where(Home.class)
                .findAll().size();
        return result;


    }


    public static void SaveProgrramingchallenge01(List<pc1Model> model, Realm rr) {
        rr.executeTransaction(realm1 -> realm1.insertOrUpdate(model));
    }

    public static void StoreJson(NewHome newHome, Realm rr) {
        rr.executeTransaction(realm -> realm.insertOrUpdate(newHome));

    }

    public static void StoreChallengeJson(Challenge2 challenge2) {
        Realm realm =Realm.getDefaultInstance();
        realm.executeTransaction(rr -> rr.insertOrUpdate(challenge2));

    }
    public static void StoreChallenge3Json(Challenge3 challenge3) {
        Realm realm =Realm.getDefaultInstance();
        realm.executeTransaction(rr -> rr.insertOrUpdate(challenge3));

    }
    public static void StoreFinalChallengeJson(Challenge_Final challenge_final) {
        Realm realm =Realm.getDefaultInstance();
        realm.executeTransaction(rr -> rr.insertOrUpdate(challenge_final));

    }

    public static List<Home> getRealmHome(Realm rr) {
        RealmResults<Home> lm = rr.where(Home.class).findAll();
        return lm;
    }


    public static Challenge2 getChallenge2() {
        Realm rr =Realm.getDefaultInstance();
      Challenge2 lm = rr.where(Challenge2.class).findFirst();
      return lm;
    }

    public static Challenge3 getChallenge3() {
        Realm rr =Realm.getDefaultInstance();
        Challenge3 lm = rr.where(Challenge3.class).findFirst();
        return lm;
    }

    public static Challenge_Final getFinalChallenge() {
        Realm rr =Realm.getDefaultInstance();
        Challenge_Final lm = rr.where(Challenge_Final.class).findFirst();
        return lm;
    }



    //todo Programming Challenge #01
    public static void UpdatePcModule(int i) {
        Realm rr = Realm.getDefaultInstance();
        if (i == 0) {

            Home hm = rr.where(Home.class)
                    .equalTo(HomeFields.ID, 6)
                    .findFirst();

            if (hm != null) {
                rr.executeTransaction(realm -> {
                    hm.setStatus("completed");

                });
            }

            rr.close();
        }

    }
    public static void UpdateChallengeModule(int i) {
        Realm rr = Realm.getDefaultInstance();
            Home hm = rr.where(Home.class)
                    .equalTo(HomeFields.ID, i)
                    .findFirst();
            if (hm != null) {
                rr.executeTransaction(realm -> {
                    hm.setStatus("completed");

                });
            rr.close();
        }

    }

    public static boolean GetPcMOdulestatus(int i) {
        Realm rr = Realm.getDefaultInstance();
        if (i == 0) {

            Home hm = rr.where(Home.class)
                    .equalTo(HomeFields.ID, 6)
                    .findFirst();

            rr.close();
            if (hm.getStatus().equals("open")) {
                return false;
            } else {
                return true;
            }

        }
        return false;


    }

    public static boolean GetChallengeMOdulestatus(int i) {
        Realm rr = Realm.getDefaultInstance();
            Home hm = rr.where(Home.class)
                    .equalTo(HomeFields.ID, i)
                    .findFirst();
            rr.close();

            if (hm.getStatus().equals("open")) {
                return false;
            } else {
                return true;
            }

    }

    //todo Programming Challenge #01


    public static void UpdateHomeModule(Context cn, Realm rr, int id) {
//        RealmList<com.example.test.programmingmama.Model.NewModel.List> lm =rr.where(Home.class)
//                .equalTo(HomeFields.ID, id)
//                .findFirst().getList();

//        if(lm==null||lm.size()==0){
//            int result = rr.where(Home.class)
//                    .equalTo(HomeFields.ID, id)
//                    .findFirst().getDes().sum("mark").intValue();
//            Log.d("Mark", "UpdateModule: "+result);
//
//        }else {
//            Log.d("Mark", "UpdateModule: null ");
//        }


        Home hm = rr.where(Home.class)
                .equalTo(HomeFields.ID, id)
                .findFirst();

        Home hm2 = rr.where(Home.class)
                .equalTo(HomeFields.ID, id + 1)
                .findFirst();

        int result = rr.where(Home.class)
                .equalTo(HomeFields.ID, id)
                .findFirst().getDes().sum("mark").intValue();


        Log.d("Result", "Update Home Module Value " + result);
        if (hm != null) {
            if (hm2 != null) {
                rr.executeTransaction(realm -> {
                    hm.setStatus("completed");
                    hm.setResult(result);
                    hm2.setStatus("open");
                    new PrefManager(cn).SetHomePosition(id+1);

                });
            } else {
                rr.executeTransaction(realm -> {
                    hm.setStatus("completed");
                    hm.setResult(result);
                });
            }

        }


    }

    public static List<com.example.test.programmingmama.Model.NewModel.List> GetListById(Realm rr, int id) {
        RealmList<com.example.test.programmingmama.Model.NewModel.List> lm =
                rr.where(Home.class)
                        .equalTo(HomeFields.ID, id)
                        .findFirst()
                        .getList();


        return lm;
    }

    public static void UnlockListModule(Realm rr, int id, int ListId) {
        com.example.test.programmingmama.Model.NewModel.List list = rr
                .where(Home.class)
                .equalTo(HomeFields.ID, id)
                .findFirst()
                .getList()
                .where()
                .equalTo(ListFields.ID, ListId)
                .findFirst();

        com.example.test.programmingmama.Model.NewModel.List list2 = rr
                .where(Home.class)
                .equalTo(HomeFields.ID, id)
                .findFirst()
                .getList()
                .where()
                .equalTo(ListFields.ID, ListId + 1)
                .findFirst();



        if (list != null) {
            if (list2 != null&&!list2.getStatus().equals("premium")) {
                rr.executeTransaction(realm -> {
                    list.setStatus("completed");
                    list2.setStatus("open");
                });
            } else {
                rr.executeTransaction(realm -> {
                    list.setStatus("completed");
                });
            }
        }
    }


    public static void UpdateListModule(Realm rr, int id) {
        Home hm = rr.where(Home.class)
                .equalTo(HomeFields.ID, id)
                .findFirst();

        Home hm2 = rr.where(Home.class)
                .equalTo(HomeFields.ID, id + 1)
                .findFirst();

        int result = rr.where(Home.class)
                .equalTo(HomeFields.ID, id)
                .findFirst().getList().sum("result").intValue();


        if (hm != null) {
            if (hm2 != null) {
                rr.executeTransaction(realm -> {
                    hm.setStatus("completed");
                    hm.setResult(result);
                    hm2.setStatus("open");

                });
            } else {
                rr.executeTransaction(realm -> {
                    hm.setStatus("completed");
                    hm.setResult(result);
                });
            }

        }

    }


    public static int TotalMark() {
        Realm realm = Realm.getDefaultInstance();
        int result = realm.where(Home.class)
                .findAll().sum("result").intValue();
        realm.close();
        return result;
    }

    //todo ========Add Mark=========
    public static void HomeMark(Realm realm, int id, String detailsHmId, int i) {
        Home hm = realm.where(Home.class)
                .equalTo(HomeFields.ID, id)
                .findFirst();


        De de = realm.where(De.class)
                .equalTo(DeFields.ID, detailsHmId)
                .findFirst();

        if (de != null) {
            realm.executeTransaction(rm -> {
                de.setMark(i);
                de.setStatus("true");
            });
            int result = realm.where(Home.class)
                    .equalTo(HomeFields.ID, id)
                    .findFirst().getDes().sum("mark").intValue();
            if (hm != null) {
                realm.executeTransaction(rm -> {
                    hm.setResult(result);
                });
            }
        }

    }


    public static void HomeListMark(Realm realm, String detailsListId, int i) {

        Mde de = realm.where(Mde.class)
                .equalTo(MdeFields.ID, detailsListId)
                .findFirst();

        if (de != null) {
            realm.executeTransaction(rm -> {
                de.setMark(i);
                de.setStatus("true");
            });

        }

    }

    public static void ADDLISTMODULEResult(Realm rr, int id, int ListId) {
        Home hm = rr.where(Home.class)
                .equalTo(HomeFields.ID, id)
                .findFirst();
        int ListDesValue = rr.where(com.example.test.programmingmama.Model.NewModel.List.class)
                .equalTo(ListFields.ID, ListId)
                .findFirst().getMdes().sum("mark").intValue();

        com.example.test.programmingmama.Model.NewModel.List lm = rr.where(com.example.test.programmingmama.Model.NewModel.List.class)
                .equalTo(ListFields.ID, ListId)
                .findFirst();

        if (lm != null) {
            rr.executeTransaction(rm -> {
                lm.setResult(ListDesValue);
            });

            int result = rr.where(Home.class)
                    .equalTo(HomeFields.ID, id)
                    .findFirst().getList().sum("result").intValue();

            if (hm != null) {
                rr.executeTransaction(rm -> {
                    hm.setResult(result);
                });
            }
        }
    }


    public static void UpdateBookmark(Realm rr, int id, String desId, int type) {
        if (type == 0) {
            De de = rr.where(De.class)
                    .equalTo(DeFields.ID, desId)
                    .findFirst();
            if (de != null) {
                if (de.getBookmark().equals("false")) {
                    rr.executeTransaction(rm -> {

                        de.setBookmark("true");
                    });
                } else {
                    rr.executeTransaction(rm -> {
                        de.setBookmark("false");
                    });
                }
            }
        } else {
            Mde de = rr.where(Mde.class)
                    .equalTo(MdeFields.ID, desId)
                    .findFirst();
            if (de != null) {
                if (de.getBookmark().equals("false")) {
                    rr.executeTransaction(rm -> {
                        de.setBookmark("true");
                    });
                } else {
                    rr.executeTransaction(rm -> {
                        de.setBookmark("false");
                    });
                }
            }

        }
    }

    public static void UpdateModule(Realm rr, int id) {
        int result = rr.where(Home.class)
                .equalTo(HomeFields.ID, id)
                .findFirst().getDes().sum("mark").intValue();

        Log.d("Mark", "UpdateModule: " + result);


        Home hm = rr.where(Home.class)
                .equalTo(HomeFields.ID, id)
                .findFirst();

        Home hm2 = rr.where(Home.class)
                .equalTo(HomeFields.ID, id + 1)
                .findFirst();


        if (hm != null) {
            if (hm2 != null) {
                rr.executeTransaction(realm -> {
                    hm.setStatus("completed");
                    hm2.setStatus("open");

                });
            } else {
                rr.executeTransaction(realm -> {
                    hm.setStatus("completed");
                });
            }

        }


    }


    public static void UnlockMOdule2(Realm rr) {
        Home hm = rr.where(Home.class)
                .equalTo(HomeFields.ID, 2)
                .findFirst();

        Home hm2 = rr.where(Home.class)
                .equalTo(HomeFields.ID, 3)
                .findFirst();
        Home hm3 = rr.where(Home.class)
                .equalTo(HomeFields.ID, 4)
                .findFirst();
        Home hm4 = rr.where(Home.class)
                .equalTo(HomeFields.ID, 5)
                .findFirst();


        if (hm != null) {
            if (hm2 != null) {
                if (hm3 != null) {
                    if (hm4 != null) {
                        rr.executeTransaction(realm -> {
                            hm.setStatus("open");
                            hm2.setStatus("open");
                            hm3.setStatus("open");
                            hm4.setStatus("open");

                        });
                    }

                }
            }
        }


    }

    public static void UnlockMOdule(Realm rr) {


        Home hm5 = rr.where(Home.class)
                .equalTo(HomeFields.ID, 7)
                .findFirst();

        if(hm5!=null){
            rr.executeTransaction(realm -> {
                hm5.setStatus("open");

            });
        }

    }
    public static void ChlngMOdule(int id) {
    Realm rr =Realm.getDefaultInstance();

        Home hm = rr.where(Home.class)
                .equalTo(HomeFields.ID, id)
                .findFirst();
        if(hm!=null){
            rr.executeTransaction(realm -> {
                hm.setStatus("open");

            });
        }

    }

}
