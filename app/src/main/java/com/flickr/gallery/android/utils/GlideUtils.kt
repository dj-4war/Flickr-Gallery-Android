package com.flickr.gallery.android.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.flickr.gallery.android.R


object GlideUtils {
    /**
     * Load the image into specified image view.
     */
    fun loadFeedThumbnail(defaultImage: Int, loadInto: ImageView, context: Context, path: String) {
        Glide.with(context)
            .load(path)
            .centerCrop()
            .placeholder(defaultImage)
            .into(loadInto);

    }
}