package com.example.library;

import com.example.library.config.BurialPointConfig;
import com.example.library.log.Logcat;

/**
 * @author YangJ
 */
public class BurialPointManager {

    private BurialPointConfig mConfig;

    private static final class SingletonHolder {
        private static final BurialPointManager INSTANCE = new BurialPointManager();
    }

    public static BurialPointManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private BurialPointManager() {
    }

    public void initialize(BurialPointConfig config) {
        this.mConfig = config;
    }

    public Logcat getLogcat() {
        return mConfig.getLogcat();
    }

}
