package com.toocms.tab.map.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;

/**
 * Author：Zero
 * Date：2020/6/11 16:10
 *
 * @version v1.0
 */

public class LocationUtils {

    /**
     * 判断定位服务是否开启
     *
     * @param context
     * @return true 表示开启
     */
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    /**
     * 提示用户去开启定位服务
     *
     * @param activity
     */
    public static void toOpenGPS(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("提示")
                .setMessage("手机定位服务未开启，无法获取到您的准确位置信息，是否前往开启？")
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                })
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        activity.startActivity(intent);
                    }
                })
                .show();
    }
}
