package com.flickr.gallery.android.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "entry", strict = false)
class Entry {
    @field:Element(name = "title", required = false)
    var feedTitle: String? = null

    @field:Element(name = "content", required = false)
    var content: String? = null

    @field:Element(name="author", required = false)
    var author : FeedAuthor? = null

    @field:Element(name="published", required = false)
    var feedPublishDate : String? = null

}
