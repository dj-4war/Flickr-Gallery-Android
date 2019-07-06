package com.flickr.gallery.android.api.http

import retrofit2.Retrofit


interface IHTTPClient{
    fun bindService(service: Any) : Any
    fun getHttpClient() : Retrofit?
}