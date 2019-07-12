package com.flickr.gallery.android.repositories

import androidx.lifecycle.MutableLiveData
import com.flickr.gallery.android.api.DataResponse
import com.flickr.gallery.android.api.PUBLIC_FEEDS
import com.flickr.gallery.android.api.http.FlickrHTTPClient
import com.flickr.gallery.android.api.http.IFlckrAPIService
import com.flickr.gallery.android.models.FeedContent
import com.flickr.gallery.android.models.RootFeed
import com.flickr.gallery.android.utils.FlickrLogger
import com.flickr.gallery.android.utils.ParserUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Singleton class
 */
object GalleryRepository {

    private val TAG: String = GalleryRepository::javaClass.name

    fun getFeedContentFromRemote(): MutableLiveData<List<FeedContent>> {
        val mutableFeedContent = MutableLiveData<List<FeedContent>>()
        try {
            val flickrAPIService: IFlckrAPIService =
                FlickrHTTPClient.getHttpClient()!!.create(IFlckrAPIService::class.java)  // Get API service
            val publicPhotosResponse = flickrAPIService.getFlickrPhotos()
            publicPhotosResponse.enqueue(
                object : Callback<RootFeed> {
                    override fun onFailure(call: Call<RootFeed>, t: Throwable) {
                        FlickrLogger.errLog(TAG, "onFailure() :: Failed to get response ", null);
                    }

                    override fun onResponse(call: Call<RootFeed>, response: Response<RootFeed>) {
                        doAsync {
                            val parsedFeedContent = processSuccessResponse(response)
                            if (parsedFeedContent != null) {
                                uiThread {
                                    mutableFeedContent.value = parsedFeedContent
                                }
                            }
                        }
                    }
                }
            )
        } catch (ex: Exception) {
            FlickrLogger.errLog(TAG, "getPhotosFromServer() :: exception while getting photos from server ", ex);
        }
        return mutableFeedContent
    }

    private fun processSuccessResponse(response: Response<RootFeed>): MutableList<FeedContent>? {
        var processedFeedList: MutableList<FeedContent>? = null
        val serverResponse = DataResponse<RootFeed>()
        FlickrLogger.infoLog(
            TAG,
            "processSuccessResponse() ::  Response received from server for photos ${response.message()}"
        )
        serverResponse.entity = response.body()
        serverResponse.status = response.code()
        serverResponse.isSuccess = response.isSuccessful
        serverResponse.requestType = PUBLIC_FEEDS
        if (serverResponse.entity != null) {
            val feedContent = response.body() as RootFeed
            processedFeedList = ParserUtils.getParsedFeedContent(feedContent)
        }
        return processedFeedList
    }
}