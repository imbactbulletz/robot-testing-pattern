package com.imbactbulletz.robotpattern.util

import android.app.Application
import androidx.annotation.StringRes
import androidx.test.core.app.ApplicationProvider

fun resolveString(@StringRes resId: Int) =
    ApplicationProvider.getApplicationContext<Application>().getString(resId)