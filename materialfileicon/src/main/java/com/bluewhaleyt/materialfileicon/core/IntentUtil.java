package com.bluewhaleyt.materialfileicon.core;

import android.app.Activity;
import android.content.Intent;

public class IntentUtil {
    private static Intent intent = new Intent();

    public static void intent(Activity activity, Class<?> targetActivity) {
        activity.startActivity(new Intent(activity, targetActivity));
//        AnimationUtil.setIntentActivityEnterAnimation(activity);
    }
    public static void intentPutString(Activity activity, Class<?> targetActivity, String dataName, String dataValue) {
        intent = new Intent(activity,  targetActivity);
        intent.putExtra(dataName, dataValue);
        activity.startActivity(intent);
//        AnimationUtil.setIntentActivityEnterAnimation(activity);
    }
    public static String intentGetString(Activity activity, String dataName) {
        return activity.getIntent().getExtras().getString(dataName);
    }
}
