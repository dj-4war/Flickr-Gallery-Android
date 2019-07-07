package com.flickr.gallery.android.integration

import com.flickr.gallery.android.BaseTestClass
import com.flickr.gallery.android.api.http.FlickrHTTPClient
import com.flickr.gallery.android.api.http.IFlikerAPIService
import com.flickr.gallery.android.controllers.FlickrFeedController
import com.flickr.gallery.android.models.RootFeed
import com.flickr.gallery.android.utils.FlickrLogger
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner

/**
 * This is class holds Flickr integration tests and this class is responsible to test APIs and its behaviour
 * @author Shashi, Created at July 2nd 2019
 */
@RunWith(MockitoJUnitRunner::class)
class FlickrAPIIntegrationTest : BaseTestClass() {

    val TAG: String = FlickrAPIIntegrationTest::class.java.canonicalName

    init {
        FlickrHTTPClient.initialize()
    }

    @Test
    fun canHitPubPhotosEndPoint() {
        val response = hitPublicPhotosEndPoint()
        Assert.assertEquals(response.code(), 200)
    }

    @Test
    fun canGetResponseFromServer() {
        val response = hitPublicPhotosEndPoint()
        Assert.assertNotNull(response.body())
    }

    @Test
    fun canGetValidResponseForFeed() {
        val response = hitPublicPhotosEndPoint()
        val feed = response.body()
        FlickrLogger.infoLog(TAG,"canGetValidResponseForFeed() :: Feed title ${feed!!.title}")
        assertEquals(feed!!.title, "Uploads from everyone")
    }

    @Test
    fun canGetFeeds() {
        val response = hitPublicPhotosEndPoint()
        val publicFeeds = response.body()
        FlickrLogger.infoLog(TAG,"canGetFeeds() :: Feed list size ${publicFeeds!!.entries!!.size}")
        assertTrue(publicFeeds.entries!!.isNotEmpty())
    }

    @Test
    fun canGetContent() {
        val response = hitPublicPhotosEndPoint()
        val publicFeeds = response.body()
        FlickrLogger.infoLog(TAG,"canGetContent() :: Content  ${(publicFeeds!!.entries!![0]).content}")
        //Assert.assertTrue(publicFeeds.entries!!.isNotEmpty())
    }

    @Test
    fun canParseFeedContentFromServer(){
        val response = hitPublicPhotosEndPoint()
        val publicFeeds = response.body()
        val feedContentList = FlickrFeedController.getParsedFeedContent(publicFeeds!!)
        val pasedListSize = feedContentList.size
        Assert.assertTrue(pasedListSize > 0)
    }

    private fun hitPublicPhotosEndPoint(): retrofit2.Response<RootFeed> {
        val flickrAPIService: IFlikerAPIService =
            FlickrHTTPClient.getHttpClient()!!.create(IFlikerAPIService::class.java)  // Get API service
        val publicPhotos = flickrAPIService.getFlickrPhotos()
        return publicPhotos.execute()
    }
}
