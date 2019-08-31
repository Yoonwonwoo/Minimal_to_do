package com.example.minimaltodo;

import android.app.Activity;
import android.content.Intent;

public class ThemeUtils {
    private static int mTheme;
    public final static int LIGHT = 0;
    public final static int DARK = 1;

    public static void changeToTheme(Activity activity, int theme){
        mTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivitySetTheme(Activity activity){
        switch (mTheme){
            default:
            case LIGHT :
                activity.setTheme(R.style.AppTheme);
                break;
            case DARK :
                activity.setTheme(R.style.AppTheme_Dark);
                break;
        }
    }
}
