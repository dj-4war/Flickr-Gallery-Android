package com.flickr.gallery.android.application

import android.app.Application
import com.flickr.gallery.android.di.DaggerFlickrAppComponent
import com.flickr.gallery.android.di.FlickrAppComponent
import com.flickr.gallery.android.di.FlickrAppModule
import com.flickr.gallery.android.utils.FlickrLogger


/**
 * This class is starting point of the application, all the core initialisations which are required to available entire application
 * life cycle should be initialized in this class.
 * @author Shashi created on 29/06/2019
 */

class FlickerApp : Application() {


    private val TAG: String = FlickerApp::class.java.name

    override fun onCreate() {
        super.onCreate()
        FlickrLogger.infoLog(TAG, "onCreate() :: App Class")
        appComponent = DaggerFlickrAppComponent
            .builder()
            .flickrAppModule(FlickrAppModule(this))
            .build()
    }

    companion object {
        private var appComponent: FlickrAppComponent? = null
        fun getAppComponent(): FlickrAppComponent {
            return appComponent!!
        }
    }
}