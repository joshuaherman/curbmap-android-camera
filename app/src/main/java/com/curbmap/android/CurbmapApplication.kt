package com.curbmap.android

import android.app.Application

import com.curbmap.android.logging.NoLoggingTree

import timber.log.Timber

/**
 * Copyright 2018 curbmap-android-camera
 *
 *
 * ${COPYRIGHT}
 */


class CurbmapApplication : Application() {

    // TODO: 8/6/18 Add logging
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(NoLoggingTree())
        }
    }


}
