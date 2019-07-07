package com.flickr.gallery.android.di

import android.content.Context
import com.flickr.gallery.android.utils.RxBus
import dagger.Component

@Component(modules = [FlickrAppModule::class])
interface FlickrAppComponent{
    fun getAppContext() : Context
    fun getBus() : RxBus
}