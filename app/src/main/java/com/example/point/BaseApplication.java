package com.example.point;

import android.app.Application;

import com.example.library.BurialPointManager;
import com.example.library.config.BurialPointConfig;
import com.example.library.log.DefaultLogcat;
import com.example.library.log.Logcat;

/**
 * @author YangJ
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        // 初始化日志记录器
//        Logcat logcat = new SimpleLogcat(); // 一个简单的日志记录器，仅仅在控制台输出日志信息
        Logcat logcat = new DefaultLogcat(this); // 一个默认的日志记录器，会将日志信息写入本地文件
        BurialPointConfig config = new BurialPointConfig.Builder(this).setLogcat(logcat).build();
        BurialPointManager.getInstance().initialize(config);
    }
}
