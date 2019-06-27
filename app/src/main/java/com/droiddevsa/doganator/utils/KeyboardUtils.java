package com.droiddevsa.doganator.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.droiddevsa.doganator.views.MainActivity;

public class KeyboardUtils {
    public static void hideKeyboard(MainActivity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity.getApplicationContext());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
