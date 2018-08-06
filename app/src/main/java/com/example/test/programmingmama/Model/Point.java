package com.example.test.programmingmama.Model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Point extends RealmObject{
    String ModuleName;
    int ModuleId;
    RealmList<String> subModule;
    int Score;

    public Point() {
    }

    public Point(String moduleName, int moduleId, RealmList<String> subModule, int score) {
        ModuleName = moduleName;
        ModuleId = moduleId;
        this.subModule = subModule;
        Score = score;
    }

    public String getModuleName() {
        return ModuleName;
    }

    public void setModuleName(String moduleName) {
        ModuleName = moduleName;
    }

    public int getModuleId() {
        return ModuleId;
    }

    public void setModuleId(int moduleId) {
        ModuleId = moduleId;
    }

    public RealmList<String> getSubModule() {
        return subModule;
    }

    public void setSubModule(RealmList<String> subModule) {
        this.subModule = subModule;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
