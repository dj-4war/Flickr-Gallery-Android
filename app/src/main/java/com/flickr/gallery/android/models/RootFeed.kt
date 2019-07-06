package com.flickr.gallery.android.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name= "feed", strict = false)
class RootFeed {
    @field:Element(name = "title")
    var title: String? = null

   @field:ElementList(name = "entry", required = false, inline = true)
    var entries: List<Entry>? = null


}