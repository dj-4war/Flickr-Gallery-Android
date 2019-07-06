package com.flickr.gallery.android

import com.flickr.gallery.android.utils.FlickrLogger

abstract class BaseTestClass {
    init {
        FlickrLogger.TEST_MODE  = true;
    }
}