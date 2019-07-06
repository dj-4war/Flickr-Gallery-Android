package com.flickr.gallery.android.api.http

/**
 * HTTP utils to be used across the application
 * @author Shashi
 */

object HTTPUtils {

    fun getDefaultHeaders(): Map<String, String> {
        val defaultHeadersMap = HashMap<String, String>()
        defaultHeadersMap["Content-Type"] = "application/json"
        return defaultHeadersMap
    }
}