package com.flickr.gallery.android.controllers

import com.flickr.gallery.android.FlickerApp
import com.flickr.gallery.android.api.DataResponse
import com.flickr.gallery.android.api.PUBLIC_FEEDS
import com.flickr.gallery.android.api.http.FlickrHTTPClient
import com.flickr.gallery.android.api.http.IFlikerAPIService
import com.flickr.gallery.android.models.FeedContent
import com.flickr.gallery.android.models.RootFeed
import com.flickr.gallery.android.utils.FlickrLogger
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * This controller is responsible to handle all Photos related operations with in the applicaiton.
 * @author Shashi , created on 2nd July 2019
 */

object FlickrPhotosController {

    private val TAG = FlickrPhotosController::class.java.canonicalName

    /**
     * API to get public photos from server
     */
    fun getPhotosFromServer(): DataResponse<RootFeed> {
        val serverResponse = DataResponse<RootFeed>()
        try {
            val flickrAPIService: IFlikerAPIService =
                FlickrHTTPClient.getHttpClient()!!.create(IFlikerAPIService::class.java)  // Get API service
            val phublicPhotosResponse = flickrAPIService.getFlickrPhotos()
            phublicPhotosResponse.enqueue(
                object : Callback<RootFeed> {
                    override fun onFailure(call: Call<RootFeed>, t: Throwable) {
                        FlickrLogger.errLog(TAG, "onFailure() :: Failed to get response ", null);
                    }

                    override fun onResponse(call: Call<RootFeed>, response: Response<RootFeed>) {
                        FlickrLogger.infoLog(
                            TAG,
                            "onResponse() ::  Response received from server for photos ${response.message()}"
                        )
                        serverResponse.entity = response.body()
                        serverResponse.status = response.code()
                        serverResponse.isSuccess = response.isSuccessful
                        serverResponse.requestType = PUBLIC_FEEDS
                        FlickerApp.getAppComponent().getBus().publish(serverResponse)
                    }
                }
            )
        } catch (ex: Exception) {
            FlickrLogger.errLog(TAG, "getPhotosFromServer() :: exception while getting photos from server ", ex);
        }
        return serverResponse
    }

    /**
     * We get HTML content from Content tag in feed xml, we need to parse and build to show on UI.
     * We could also use direct HTML  content to render on Webview but to beatify Gallery it may not be suitable data
     */
    fun getParsedFeedContent(rootFeed: RootFeed): MutableList<FeedContent> {
        var processedFeedList: MutableList<FeedContent>? =  mutableListOf()
        try {
            val feedList = rootFeed.entries
            FlickrLogger.infoLog(TAG, "getParsedFeedContent() :: Feed list size  -> ${feedList!!.size}")
            if (feedList != null) {
                for (entry in feedList) {
                    val feedContent = FeedContent()
                    feedContent.feedTitle = entry.feedTitle
                    feedContent.author = entry.author!!.authorName
                    feedContent.feedAuthorURI = entry.author!!.authorURI
                    feedContent.feedDate = entry.feedPublishDate


                    val htmlContent = entry.content
                    feedContent.rawContent = htmlContent
                    val doc = Jsoup.parse(htmlContent)
                    val elements = doc.getElementsByTag("a") //get <a tags

                    for (element in elements) {
                        val postedBy = element.text()
                        val postTitle = element.attr("title")
                        val imgElement = element.getElementsByAttribute("src")
                        val imgURL = imgElement.attr("src")
                        if (!postedBy.isNullOrEmpty()) {
                            feedContent.postedBy = postedBy
                            FlickrLogger.infoLog(TAG, "Posted by ->  ==>$postedBy")
                        }
                        if (!postTitle.isNullOrEmpty()) {
                            feedContent.postedImageTitle = postTitle
                            FlickrLogger.infoLog(TAG, "Image Title by ->  ==>$postTitle")
                        }
                        if(!imgURL.isNullOrEmpty()){
                            feedContent.photoURI = imgURL
                            FlickrLogger.infoLog(TAG, "Image URL -> ==>$imgURL")
                        }
                    }
                    processedFeedList!!.add(feedContent)
                }
            } else {
                FlickrLogger.infoLog(TAG, "getParsedFeedContent() :: Feed list is empty")
            }
        } catch (ex: Exception) {
            FlickrLogger.errLog(TAG, "getParsedFeedContent() :: Exception while parsing ", ex)
        }
        return processedFeedList!!
    }
}