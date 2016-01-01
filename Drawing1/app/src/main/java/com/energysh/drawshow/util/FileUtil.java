package com.energysh.drawshow.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2015/12/10.
 */
public class FileUtil {

    // 获得U盘目录
    public static String getUdiskPath() {
        return File.separator + "udisk";
    }

    // 获得SD卡目录
    public static String getSdcardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    // 判断SD卡是否OK
    public static boolean getSdcardIsReady() {
        String sdready = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(sdready)
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(sdready)) {
            return true;
        }

        return false;
    }
}
