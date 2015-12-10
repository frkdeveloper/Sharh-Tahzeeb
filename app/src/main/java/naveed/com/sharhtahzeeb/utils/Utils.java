package naveed.com.sharhtahzeeb.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by naveed on 6/3/15.
 */
public class Utils {

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static Boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    public static boolean checkAppExists(Context context) {
        return (context.getPackageManager().queryIntentActivities(new Intent(Intent.ACTION_VIEW).setType("application/pdf"),
                PackageManager.MATCH_DEFAULT_ONLY)).size()>0;
    }
}
