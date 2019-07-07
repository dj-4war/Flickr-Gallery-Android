package com.flickr.gallery.android.controllers

import com.flickr.gallery.android.api.DataResponse
import com.flickr.gallery.android.models.FeedContent
import com.flickr.gallery.android.models.RootFeed

/**
 * A feed interface can  provide  as when we there are new implementation in FeedController
 * @author Shashi
 */

interface IFeedProvider{
    fun getPhotosFromServer() : DataResponse<RootFeed>
    fun getParsedFeedContent(rootFeed: RootFeed) : MutableList<FeedContent>
}