package com.flickr.gallery.android

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.Matcher


object EsspressoUtils {

    private val TAG : String = EsspressoUtils::javaClass.name

    /**
     * Perform action of waiting for a specific time.
     */
    fun waitFor(millis: Long): ViewAction {
        return object : ViewAction {
            override fun perform(uiController: UiController?, view: View?) {
                Log.d(TAG, "Waiting for activity ...")
            }

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isRoot()
            }

            override fun getDescription(): String {
                return "Wait for $millis milliseconds."
            }
        }
    }


    fun getText(matcher: Matcher<View>): String {
        val stringHolder = arrayOf<String>(null.toString())
        onView(matcher).perform(object : ViewAction {
            override fun perform(uiController: UiController?, view: View?) {
                val tv = view as TextView //Save, because of check in getConstraints()
                stringHolder[0] = tv.text.toString()
            }

            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "getting text from a TextView"
            }

        })
        return stringHolder[0]
    }



}