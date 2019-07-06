package com.flickr.gallery.android.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "author", strict = false)
class FeedAuthor{
    @field:Element(name = "name", required = false)
    var authorName: String? = null

    @field:Element(name = "uri", required = false)
    var authorURI: String? = null
}