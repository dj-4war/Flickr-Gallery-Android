package com.flickr.gallery.android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.flickr.gallery.android.R
import com.flickr.gallery.android.models.FeedContent
import com.flickr.gallery.android.utils.FlickrLogger
import com.flickr.gallery.android.utils.FlickrUtils
import com.flickr.gallery.android.utils.GlideUtils

/**
 * A view data minder adapter to build gallery view
 * @author Shashi
 */

class FeedGalleryAdapter(val feedPhotoList: List<FeedContent>, context : Context) : BaseAdapter() {

    private var mInflater: LayoutInflater? = null
    private val TAG = FeedGalleryAdapter::class.java.name
    private var mContext : Context = context

    init {
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mContext =  context
    }

    override fun getCount(): Int {
        return feedPhotoList.size
    }

    override fun getItem(position: Int): FeedContent {
        return feedPhotoList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, view : View?, parent: ViewGroup): View? {
        var convertView = view
        val feedContent = getItem(position)
        try {
            val holder: FeedHolder
            if (convertView == null) {
                convertView = mInflater!!.inflate(R.layout.gallery_item_view, null)
                holder = FeedHolder()
                // IF we want to use WebView to load feed content uncomment this
                //holder.feedWebView = convertView!!.findViewById<View>(R.id.feed_web_view) as WebView
                //holder.feedWebView!!.loadData(packageInfo.content, "text/html", "UTF-8");
                holder.feedThumbnail = convertView!!.findViewById<View>(R.id.feed_thumnail) as ImageView
                holder.feedAuthor = convertView!!.findViewById<View>(R.id.feed_author_tv) as TextView
                holder.feedTitle = convertView!!.findViewById<View>(R.id.feed_title_tv) as TextView
                holder.feedDate = convertView!!.findViewById<View>(R.id.feed_date_tv) as TextView
                convertView.tag = holder
            } else {
                holder = convertView.tag as FeedHolder
            }

            GlideUtils.loadFeedThumbnail(R.mipmap.ic_launcher, holder.feedThumbnail!!, mContext, feedContent.photoURI!!)
            holder.feedAuthor!!.text = feedContent.author
            holder.feedTitle!!.text = feedContent.postedImageTitle
            val time = FlickrUtils.parseFeedDate(feedContent.feedDate!!)
            val date = FlickrUtils.parseFeedTime(feedContent.feedDate!!)
            holder.feedDate!!.text = "$date, $time"
        } catch (ex: Exception) {
            FlickrLogger.errLog(TAG, "getView() :: exception : ",ex)
        }

        return convertView
    }

    internal class FeedHolder {
        var feedWebView : WebView? = null  // TODO : Depends up on requirement and design there is an option to load item in WebView as well
        var feedThumbnail: ImageView? = null
        var feedTitle : TextView? = null
        var feedAuthor : TextView? = null
        var feedDate : TextView? = null
    }
}