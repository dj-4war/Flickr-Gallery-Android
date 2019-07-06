package com.flickr.gallery.android.activities

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.flickr.gallery.android.FlickerApp
import com.flickr.gallery.android.adapters.FeedGalleryAdapter
import com.flickr.gallery.android.api.DataResponse
import com.flickr.gallery.android.api.PUBLIC_FEEDS
import com.flickr.gallery.android.controllers.FlickrPhotosController
import com.flickr.gallery.android.models.RootFeed
import com.flickr.gallery.android.utils.ConnectionUtils
import com.flickr.gallery.android.utils.FlickrLogger
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.AdapterView.OnItemClickListener
import com.flickr.gallery.android.R
import com.flickr.gallery.android.fragments.FeedDetailsDialogFragment
import com.flickr.gallery.android.models.FeedContent
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


/**
 * This activity is responsible to deal with feed gallery.
 * @author Shashi
 */

class FlickrGalleryActivity : FlickrBaseActivity(), OnItemClickListener{

    val TAG: String = FlickrGalleryActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        getPublicFeeds();
        processFeedResult()
        setListenersToViews()
    }

    private fun setListenersToViews() {
        flickr_gallery_gridview.onItemClickListener = this
    }

    private fun setupUI() {
        supportActionBar!!.elevation = 0.0F
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_refresh, menu)//Menu Resource, Menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.main_action_refresh -> getPublicFeeds()
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun processFeedResult() {
        FlickerApp.getAppComponent()
            .getBus()
            .listen(DataResponse::class.java)
            .subscribe { dataResp ->
                try {
                    FlickrLogger.infoLog(TAG, "processFeedResult() :: Received data from feed response")
                    loading_pb.visibility = View.GONE
                    if (dataResp != null) {
                        when (dataResp.requestType) {
                            PUBLIC_FEEDS ->
                                if (dataResp.isSuccess!!) {
                                    if ((dataResp.entity != null)) {
                                        val rootFeed  = dataResp.entity as RootFeed
                                        FlickrLogger.infoLog(TAG, "processFeedResult() :: Feeds is received from and size ${rootFeed.entries!!.size}")
                                        buildFeeds(rootFeed)
                                    } else {
                                        showSnackBar(getString(R.string.invalid_data_txt), true, false, content)
                                    }
                                } else {
                                    showSnackBar(getString(R.string.server_error)+dataResp.status, true, false, content)
                                }
                        }
                    }
                } catch (ex: Exception) {
                    FlickrLogger.errLog(TAG, "processFeedResult() :: Error while getting  feeds ", ex)
                    showSnackBar(getString(R.string.server_error)+dataResp.status, true, false, content)
                }
            }
    }


    @VisibleForTesting
    private fun buildFeeds(rootFeed: RootFeed) {
        try {
            loading_pb.visibility = View.VISIBLE
            supportActionBar!!.setSubtitle(rootFeed.title)
            doAsync {
                val parsedFeedList = FlickrPhotosController.getParsedFeedContent(rootFeed)
                uiThread {
                    val  feedGalleryAdapter = FeedGalleryAdapter(parsedFeedList, this@FlickrGalleryActivity)
                    flickr_gallery_gridview.adapter = feedGalleryAdapter
                    loading_pb.visibility = View.GONE
                }
            }
        }catch (ex : Exception){
            FlickrLogger.errLog(TAG, "buildFeeds() :: Error while processing  feeds ", ex)
        }
    }

    private fun getPublicFeeds() {
        if(ConnectionUtils.isConnectedToNetwork()) {
            loading_pb.visibility = View.VISIBLE
            FlickrPhotosController.getPhotosFromServer()
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
