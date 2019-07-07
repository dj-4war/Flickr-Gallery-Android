package com.flickr.gallery.android.di

import android.content.Context
import com.flickr.gallery.android.application.FlickerApp
import com.flickr.gallery.android.utils.RxBus
import dagger.Module
import dagger.Provides

/**
 * Dagger module to hold app resources required to use across the app.
 * @author Shashi
 */

@Module
class FlickrAppModule constructor(app: FlickerApp)  {

    private var mFlikrApp : FlickerApp? = null

    init {
        mFlikrApp = app
    }

    @Provides
    fun providesBus() : RxBus{
        return RxBus
    }

    @Provides
    fun providesApp() : FlickerApp {
        return mFlikrApp!!
    }

    @Provides
    fun providesAppContext() : Context{
        return mFlikrApp!!.applicationContext
    }

}