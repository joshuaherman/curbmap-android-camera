package com.curbmap.android.logging;

import timber.log.Timber;

/**
 * Copyright 2018 curbmap-android-camera
 * <p>
 * ${COPYRIGHT}
 */


@SuppressWarnings("unused")
public class NoLoggingTree extends Timber.Tree {
//NOTE: https://medium.com/@caueferreira/timber-enhancing-your-logging-experience-330e8af97341

    @Override
    protected void log(final int priority, final String tag, final String message, final Throwable throwable) { }

}
