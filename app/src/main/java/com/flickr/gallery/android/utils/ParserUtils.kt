package com.flickr.gallery.android.utils

import com.flickr.gallery.android.models.FeedContent
import com.flickr.gallery.android.models.RootFeed
import org.jsoup.Jsoup

object ParserUtils {

    private val TAG : String = ParserUtils::javaClass.name

    /**
     * We get HTML content from Content tag in feed xml, we need to parse and build to show on UI.
     * We could also use direct HTML  content to render on Webview but to beatify Gallery it may not be suitable data
     */
    fun getParsedFeedContent(rootFeed: RootFeed): MutableList<FeedContent> {
        val processedFeedList: MutableList<FeedContent>? = mutableListOf()
        try {
            val feedList = rootFeed.entries
            FlickrLogger.infoLog(
                TAG,
                "getParsedFeedContent() :: Feed list size  -> ${feedList!!.size}"
            )
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
                        if (!imgURL.isNullOrEmpty()) {
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