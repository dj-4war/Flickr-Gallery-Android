package com.flickr.gallery.android.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.flickr.gallery.android.R
import com.flickr.gallery.android.adapters.FeedGalleryAdapter
import com.flickr.gallery.android.fragments.FeedDetailsDialogFragment
import com.flickr.gallery.android.models.FeedContent
import com.flickr.gallery.android.utils.ConnectionUtils
import com.flickr.gallery.android.utils.FlickrLogger
import com.flickr.gallery.android.viewmodel.GalleryViewModel
import kotlinx.android.synthetic.main.activity_main.*


/**
 * This activity is responsible to reidar  feed gallery on UI .
 * @author Shashi
 */

class FlickrGalleryActivity : FlickrBaseActivity(), OnItemClickListener{

    private val TAG: String = FlickrGalleryActivity::class.java.name
    private lateinit var mGallaryViewModel : GalleryViewModel
    private lateinit var mParsedFeedList : MutableList<FeedContent>
    private lateinit var feedGalleryAdapter : FeedGalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setupUI()
        getPublicFeeds();
        setListenersToViews()
    }

    private fun init() {
        mGallaryViewModel = ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        mParsedFeedList = mutableListOf()
    }

    private fun setListenersToViews() {
        flickr_gallery_gridview.onItemClickListener = this
    }

    private fun setupUI() {
        supportActionBar!!.elevation = 0.0F
        feedGalleryAdapter = FeedGalleryAdapter(mParsedFeedList, this@FlickrGalleryActivity)
        flickr_gallery_gridview.adapter = feedGalleryAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_refresh, menu)//Menu Resource, Menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.main_action_refresh -> getPublicFeeds()

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun getPublicFeeds() {
        if(ConnectionUtils.isConnectedToNetwork()) {
            loading_pb.visibility = View.VISIBLE
            mGallaryViewModel.getFeedContent().observe(this, Observer<List<FeedContent>> {
                feedData ->
                if(feedData != null){
                    FlickrLogger.infoLog(TAG, "getPublicFeeds() :: Received public feeds and feeds size : ${feedData.size}")
                    mParsedFeedList.clear()
                    mParsedFeedList.addAll(feedData)
                    feedGalleryAdapter.notifyDataSetChanged()
                    loading_pb.visibility = View.GONE
                }
            })
        }else{
            showSnackBar(getString(R.string.no_network),true, false, content)
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedFeed = parent!!.adapter.getItem(position) as FeedContent
        showSnackBar("${selectedFeed.author}",true, false, content)
        val fragManager = supportFragmentManager
        val feedDetailsFragDetails = FeedDetailsDialogFragment().newFragInstance("Feed Details", selectedFeed)
        feedDetailsFragDetails.show(fragManager, "feed_details_dialog" )
    }
}
