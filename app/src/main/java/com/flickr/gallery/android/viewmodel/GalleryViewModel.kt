package com.flickr.gallery.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flickr.gallery.android.models.FeedContent
import com.flickr.gallery.android.repositories.GalleryRepository

/**
 * A Model view class which provides data to View.
 */

class GalleryViewModel : ViewModel(){

    private var mFeedContent : MutableLiveData<List<FeedContent>> = MutableLiveData()

    fun getFeedContent() : LiveData<List<FeedContent>>{
        mFeedContent = GalleryRepository.getFeedContentFromRemote()
        return mFeedContent
    }
}