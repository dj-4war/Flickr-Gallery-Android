package com.flickr.gallery.android.unit

import com.flickr.gallery.android.BaseTestClass
import com.flickr.gallery.android.utils.FlickrLogger
import com.flickr.gallery.android.utils.FlickrUtils
import org.jsoup.Jsoup
import org.junit.Assert
import org.junit.Test

/**
 * This test class is responsible to perform unit test related to feed parsing.
 * @author Shashi
 */
class ParsingTest : BaseTestClass(){

    private val TAG : String = ParsingTest::javaClass.name

    @Test
    fun canParseSampleContent(){
        val testFeedContent = "<p><a href=\"https://www.flickr.com/people/182381730@N03/\">rajeshpradhan3108</a> posted a photo:</p>\n" +
                "\t\n" +
                "<p><a href=\"https://www.flickr.com/photos/182381730@N03/48187172471/\" title=\"DSC05538\"><img src=\"https://live.staticflickr.com/65535/48187172471_22edeea8b4_m.jpg\" width=\"240\" height=\"160\" alt=\"DSC05538\" /></a></p>"
        val doc = Jsoup.parse(testFeedContent)
        val elements = doc.getElementsByTag("a")

        for(element in elements){
            val postedBy = element.text()
            val postTitle = element.attr("title")
            val imgElement = element.getElementsByAttribute("src")
            val imgURL = imgElement.attr("src")
            FlickrLogger.infoLog(TAG, "Posted by ->  ==>$postedBy")
            FlickrLogger.infoLog(TAG, "Image Title by ->  ==>$postTitle")
            FlickrLogger.infoLog(TAG, "Image URL -> ==>$imgURL")
        }
        Assert.assertTrue(elements[0].text().isNotEmpty())
    }

    @Test
    fun canParseFeedDate(){
        val feedDate  = FlickrUtils.parseFeedDate("2019-07-07T07:12:42Z")
        Assert.assertTrue(feedDate.isNotEmpty())
    }

    @Test
    fun canParseFeedTime(){
        val feedTime  = FlickrUtils.parseFeedTime("2019-07-07T07:12:42Z")
        Assert.assertTrue(feedTime.isNotEmpty())
    }
}