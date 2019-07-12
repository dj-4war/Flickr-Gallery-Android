package com.flickr.gallery.android.models

import androidx.lifecycle.ViewModel
import java.io.Serializable

/**
 * Filtered feed content will be used to to bind with UI elements.
 * @author Shashi
 */

class FeedContent :  Serializable{

    var feedTitle : String? = null
    var author : String? = null
    var feedAuthorURI : String? = null
    var feedDate : String? = null
    var postedBy : String? = null
    var postedImageTitle : String? = null
    var photoURI : String? = null
    var rawContent : String? = null



}
