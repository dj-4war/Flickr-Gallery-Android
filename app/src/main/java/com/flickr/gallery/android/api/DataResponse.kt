package com.flickr.gallery.android.api

/**
 * Generic response model used in across the application.
 * @author Shashi
 */

class DataResponse<Any> {
    var entity : Any? = null
    var status : Int? = null
    var isSuccess : Boolean? = null
    var requestType : Int? = null
}