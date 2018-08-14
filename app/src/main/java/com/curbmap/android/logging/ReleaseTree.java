package com.curbmap.android.logging;

import android.os.Build;
import android.util.Log;

import timber.log.Timber;

/**
 * Copyright 2018 curbmap-android-camera
 * <p>
 * ${COPYRIGHT}
 */


public class ReleaseTree extends Timber.Tree {
    // TODO: 8/6/18
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {

        if (priority == Log.ERROR || priority == Log.WARN)
            //YourCrashLibrary.log(priority, tag, message)
        ;
    }
}
