package com.flickr.gallery.android.ui

import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.flickr.gallery.android.EsspressoUtils
import com.flickr.gallery.android.R
import com.flickr.gallery.android.activities.FlickrGalleryActivity
import org.hamcrest.Matchers.anything
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Test class is to validate can render gallery successfully or not with the public gallery images.
 * @author Shashi
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class FlickrFeedGalleryTest {

    private val TAG : String  = FlickrFeedGalleryTest::class.java.name

    /**
     * Use [ActivityScenarioRule] to create and launch the activity under test, and close it
     * after test completes. This is a replacement for [androidx.test.rule.ActivityTestRule].
     */
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(FlickrGalleryActivity::class.java)

    @Test
    fun canLoadGalleryView(){
        Thread.sleep(5000) // Assuming that 5000 least time to get server response from Flicker public API
        onView(withId(R.id.flickr_gallery_gridview)).check(matches(isDisplayed()))
    }

    @Test
    fun canGetItemsFromAdapter(){
        Thread.sleep(5000)
        onData(anything()).
               inAdapterView(withId(R.id.flickr_gallery_gridview))
                .onChildView(withId(R.id.feed_author_tv))
    }

    @Test
    fun canClickGalleryFirstItem(){
        Thread.sleep(5000) // Assuming that 5000 least time to get server response from Flicker public API
        onData(anything()).inAdapterView(withId(R.id.flickr_gallery_gridview)).atPosition(0).onChildView(withId(
            R.id.gallery_thumbnail_card_view
        )).perform(click()) // Once the Grid items are loaded lets click first item
    }

    @Test
    fun canScrollGalleryView(){
        Thread.sleep(5000) // Assuming that 5000 least time to get server response from Flicker public API
        onView(withId(R.id.flickr_gallery_gridview)).perform(swipeUp())
    }

    @Test
    fun canLoadFeedDetailScreen(){
        Thread.sleep(5000) // Assuming that 5000 least time to get server response from Flicker public API
        onData(anything()).inAdapterView(withId(R.id.flickr_gallery_gridview)).atPosition(0).onChildView(withId(
            R.id.gallery_thumbnail_card_view
        )).perform(click())
        onView(withId(R.id.feed_details_author_tv)).check(matches(isDisplayed()))  // Check if the Fragment views are loaded correctly
    }

    @Test
    fun canCloseFeedDetailsScreen(){
        Thread.sleep(5000) // Assuming that 5000 least time to get server response from Flicker public API
        onData(anything()).inAdapterView(withId(R.id.flickr_gallery_gridview)).atPosition(0).onChildView(withId(
            R.id.gallery_thumbnail_card_view
        )).perform(click()) // Click on first item
        onView(withId(R.id.feed_details_author_tv)).check(matches(isDisplayed()))  // Check if the Fragment views are loaded correctly
        onView(withId(R.id.feed_details_close_iv)).perform(click())  // Click on CLose
        onData(anything()).inAdapterView(withId(R.id.flickr_gallery_gridview)).atPosition(1).onChildView(withId(
            R.id.gallery_thumbnail_card_view
        )).perform(click()) // See if it can click on second item
    }

    @Test
    fun canLoadProfileOnLinkClick(){
        Thread.sleep(5000) // Assuming that 5000 least time to get server response from Flicker public API
        onData(anything()).inAdapterView(withId(R.id.flickr_gallery_gridview)).atPosition(0).onChildView(withId(
            R.id.gallery_thumbnail_card_view
        )).perform(click()) // Click on first item
        onView(withId(R.id.feed_details_author_tv)).check(matches(isDisplayed()))  // Check if the Fragment views are loaded correctly
        val URL  =
            EsspressoUtils.getText(withId(R.id.feed_details_author_url_tv))
        init();
        val expectedIntent = hasAction(Intent.ACTION_VIEW);
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null));
        onView(withId(R.id.feed_details_author_url_tv)).perform(click());
        intended(expectedIntent);
        release()
    }

    @Test
    fun canRefreshFeeds(){
        Thread.sleep(5000) // Assuming that 5000 least time to get server response from Flicker public API
        onView(withId(R.id.flickr_gallery_gridview)).check(matches(isDisplayed()))
        onView(withId(R.id.main_action_refresh)).perform(click())
        Thread.sleep(5000) // Wait for feeds to load
        onView(withId(R.id.flickr_gallery_gridview)).check(matches(isDisplayed())) // Make sure gallery is displayed
        onData(anything()).inAdapterView(withId(R.id.flickr_gallery_gridview)).atPosition(0).onChildView(withId(
            R.id.gallery_thumbnail_card_view
        )).perform(click()) //To ensure it is loaded corectly click on first item
    }
}