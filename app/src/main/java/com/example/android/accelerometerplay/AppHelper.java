package com.example.android.accelerometerplay;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by ericchiu on 24/11/2016.
 */

public class AppHelper {

    private static final int PERMISSION_SYSTEM_ALERT_WINDOW = 1234;

    public static boolean checkPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.SYSTEM_ALERT_WINDOW},
                    PERMISSION_SYSTEM_ALERT_WINDOW);
            return false;
        }
        return true;
    }

    public static boolean requestSystemAlertPermission(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        final String packageName = activity.getPackageName();
        final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName));
        activity.startActivityForResult(intent, PERMISSION_SYSTEM_ALERT_WINDOW);
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isSystemAlertPermissionGranted(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(context);
    }

    public static void saveImage(Context context, Bitmap b, String name, String extension) {
        name = name + "." + extension;
        FileOutputStream out;
        try {
            out = context.openFileOutput(name, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getImageBitmap(Context context, String name, String extension) {
        name = name + "." + extension;
        try {
            FileInputStream fis = context.openFileInput(name);
            Bitmap b = BitmapFactory.decodeStream(fis);
            fis.close();
            return b;
        } catch (Exception e) {
        }
        return null;
    }
}
