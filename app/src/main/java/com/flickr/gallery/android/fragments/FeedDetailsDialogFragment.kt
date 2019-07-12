package com.flickr.gallery.android.fragments

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flickr.gallery.android.R
import com.flickr.gallery.android.models.FeedContent
import com.flickr.gallery.android.utils.GlideUtils
import kotlinx.android.synthetic.main.feed_details.*
import android.widget.TextView
import java.util.regex.Pattern

/**
 * This dialog is responsible to show feed details when user clicks on any feed from Gallery
 */
class FeedDetailsDialogFragment internal constructor() : androidx.fragment.app.DialogFragment() {


    fun newFragInstance(fragTitle : String, feedDetails : FeedContent) : FeedDetailsDialogFragment{
        val fragInstance = FeedDetailsDialogFragment()
        val bundleArgs = Bundle()
        bundleArgs.putString("fragTitle", fragTitle )
        bundleArgs.putSerializable("feedDetails", feedDetails)
        fragInstance.arguments = bundleArgs
        return fragInstance
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feed_details, container)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments!!.getString("fragTitle", "Feed Details")
        dialog!!.setTitle(title)
        val feedDetails = arguments!!.getSerializable("feedDetails") as FeedContent
        feed_details_author_tv.text = feedDetails.author
        //feed_details_feedtitle_tv.text =  feedDetails.feedTitle
        feed_details_author_url_tv.text = feedDetails.feedAuthorURI
        //addLink(feed_details_author_url_tv, "Profile", feedDetails.feedAuthorURI!! )
        feed_details_img_desc_tv.text = feedDetails.postedImageTitle
        val time = feedDetails.feedDate!!.subSequence(11,feedDetails.feedDate!!.length-1)
        val date = (feedDetails.feedDate!!.subSequence(0,10))
        feed_details_posted_by_tv.text = "Posted by "+feedDetails.postedBy +" on "+"$date, $time"
        feed_details_close_iv.setOnClickListener(View.OnClickListener { dialog!!.dismiss() })
        GlideUtils.loadFeedThumbnail(R.mipmap.ic_launcher, feed_details_posted_img_iv, context!!, feedDetails.photoURI!!)
    }

    public override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog!!.getWindow().setLayout(width, height)
        }
    }

    private fun addLink(
        textView: TextView, patternToMatch: String,
        link: String
    ) {
        val filter = Linkify.TransformFilter { match, url -> link }
        Linkify.addLinks(
            textView, Pattern.compile(patternToMatch), null, null,
            filter
        )
    }

}