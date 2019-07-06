package com.flickr.gallery.android.utils

import android.util.Log
import java.lang.Exception

/**
 * Universal logger for Fliker application, we can extend this for Unit tests as well if we get to know
 * its if we are running in test mode.
 * @author Shashi Create on 1/07/2019
 */

object FlickrLogger {

    var TEST_MODE = false;

    fun infoLog(tag: String, msg: String) {
        if (!TEST_MODE) {
            Log.d(tag, msg)
        } else {
            System.out.println(msg)
        }
    }

    fun errLog(tag: String, msg: String, ex: Exception?) {  // TODO : We can make ex as an optional parameter as every error may not be having exception to print.
        if (!TEST_MODE) {
            Log.e(tag, "$msg, Exception : $ex")
        } else {
            System.err.println(msg)
        }
    }

}