package com.flickr.gallery.android.utils

import java.lang.Exception

object FlickrUtils{
    private val TAG : String = FlickrLogger::javaClass.name

    fun parseFeedDate(feedDate : String) : String{
        try{
            return ""+feedDate.subSequence(11,feedDate.length-1)
        }catch (ex : Exception){
            FlickrLogger.errLog(TAG, "parseFeedDate() :: Exception in ",ex)
        }
        return ""
    }

    fun parseFeedTime(feedDate : String) : String {
        try{
            return ""+feedDate.subSequence(0,10)
        }catch (ex : Exception){
            FlickrLogger.errLog(TAG, "parseFeedTime() :: Exception in ",ex)
        }
        return ""
    }

}