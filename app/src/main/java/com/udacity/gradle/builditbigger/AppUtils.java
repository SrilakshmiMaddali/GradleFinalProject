package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

public class AppUtils {

    public static void summonSnackbar(final View rootView, String action, View.OnClickListener tapListener) {
        Snackbar snackbar = Snackbar.make(rootView, "We have encountered an error", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(action, tapListener);
        snackbar.show();
    }

    public static void makeNormalToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}
