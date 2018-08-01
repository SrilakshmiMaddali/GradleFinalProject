package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class AppUtils {

    private static final String LOG_TAG = "AppUtils";

    public static void summonNormalSnackbar(final View rootView, String message, String action, View.OnClickListener onClickListener) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        if (action != null && onClickListener != null) {
            snackbar.setAction(action, onClickListener);
        }
        snackbar.show();
    }

    public static void summonSnackbarSelfClosing(final View rootView, String message) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(rootView.getContext().getResources().getString(R.string.ok), v -> snackbar.dismiss());
        snackbar.show();
    }

    public static void makeNormalToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            Log.e(LOG_TAG, "Error accessing ConnectivityManager");
            return false;
        }
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
