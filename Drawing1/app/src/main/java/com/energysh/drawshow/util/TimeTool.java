package com.energysh.drawshow.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hankcs
 */
public class TimeTool
{
    private static final SimpleDateFormat ymdt = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
    private static final SimpleDateFormat mdt = new SimpleDateFormat("MM-dd HH_mm_ss");

    /**
     * 获取当前时间
     * @return
     */
    public static String current()
    {
        return ymdt.format(new Date());
    }

    public static String currentMonthDateTime()
    {
        return mdt.format(new Date());
    }

    /**
     * 获取当前时间，从1970年算起
     * @return
     */
    public static long currentTimeMillis()
    {
        return new Date().getTime();
    }
}
