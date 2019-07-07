package com.flickr.gallery.android.utils

import android.net.ConnectivityManager
import com.flickr.gallery.android.application.FlickerApp

/**
 * Connection utils class to provide connection related utility.
 */

object ConnectionUtils {

    fun isConnectedToNetwork(): Boolean {
        val cm = FlickerApp.getAppComponent().getAppContext().getSystemService("connectivity") as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

}