package com.yyyu.baselibrary.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：维护Activity
 *
 * @author yyyu
 * @date 2016/5/18
 */
public class ActivityHolder {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity act){
        activities.add(act);
    }

    public static void removeActivity(Activity act){
        activities.remove(act);
    }

    public static void finishedAll(){
        for (Activity act: activities) {
            if (!act.isFinishing()){
                act.finish();
            }
        }
    }
}