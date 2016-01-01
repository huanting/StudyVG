package com.energysh.drawshow;

import android.app.Application;
import com.energysh.drawshow.io.IOHelper;

/**
 * 做一些全局的初始化
 * @author hankcs
 */
public class MainApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        IOHelper.init(this);
    }
}
