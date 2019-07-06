package com.flickr.gallery.android.api.http

import com.flickr.gallery.android.models.RootFeed
import retrofit2.Call
import retrofit2.http.GET

/**
 * API service interface to call APIs from consume server APIs.
 */

interface IFlikerAPIService{

    /**
     * Open API to get flickr public images.
     */
    @GET(BASE_URL+"/services/feeds/photos_public.gne")
    fun getFlickrPhotos(): Call<RootFeed>

}

