package com.flickr.gallery.android.activities



import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.flickr.gallery.android.R
import com.flickr.gallery.android.utils.FlickrLogger

/**
 * All the activities should extend FlickrBaseActivity to make use or inherit the of common properties of the Activities
 * @author Shashi Created on 29/06/2019
 *
 */

open class FlickrBaseActivity : AppCompatActivity() {

    private val TAG : String = FlickrBaseActivity::javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun getProgressBar(): ProgressBar {
        val progressBar = ProgressBar(this)
        return progressBar
    }

    protected fun showSnackBar(msg: String, shouldDismiss: Boolean, shouldShowOnTop: Boolean, rootView : View ) {
        try {
            if (!isFinishing) {
                val bar = Snackbar.make(rootView!!, msg, if (shouldDismiss) Snackbar.LENGTH_LONG else Snackbar.LENGTH_INDEFINITE)
                val sbView = bar.view
                sbView.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                val textView = sbView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
                textView.setTextColor(Color.WHITE)
                textView.setTypeface(null, Typeface.BOLD)
                val snackAction = sbView.findViewById<View>(android.support.design.R.id.snackbar_action) as TextView
                snackAction.setTextColor(resources.getColor(R.color.colorAccent))
                bar.setAction(getString(R.string.close_txt)) { bar.dismiss() }
                if (shouldShowOnTop) {
                    val params = sbView.layoutParams as FrameLayout.LayoutParams
                    params.gravity = Gravity.TOP
                    sbView.layoutParams = params
                }
                bar.show()
            }
        } catch (ex: Exception) {
            FlickrLogger.errLog(TAG, "showSnackBar() :: Exception while showing snackbar ",ex)
        }
    }

}
