package com.example.groovy

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.EnumSet.allOf


@RunWith(AndroidJUnit4::class)
class PlaylistFeature {

//    val mActivityRule = ActivityScenarioRule(MainActivity::class.java)
//    @Rule get
//
//    @Test
//    fun displayScreenTitle() {
//        assertDisplayed("Playlists")
//    }
//
//    @Test
//    fun displayListOfPlaylist() {
//        assertRecyclerViewItemCount(R.id.playlists_list,10)
//
//        onView(allOf(withId(R.id.playlist_name), isDescendantOfA(childOf(R.id.playlist_list), 0)))
//            .check(matches(withText("Hard Rock Cafe")))
//            .check(matches(isDisplayed()))
//
//        onView(allOf(withId(R.id.playlist_image), isDescendantOfA(childOf(R.id.playlist_list), 0)))
//            .check(matches(withDrawable(R.mipmap.playlist)))
//            .check(matches(isDisplayed()))
//    }
//
//    fun childOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
//
//        return object :TypeSafeMatcher<View>() {
//            override fun describeTo(description: Description) {
//                description.appendText("position $childPosition of parent")
//                parentMatcher.describeTo(description)
//            }
//
//            override fun matchesSafely(view: View): Boolean {
//                if(view.parent !is ViewGroup) return false
//                val parent = view.parent as ViewGroup
//
//                return (parentMatcher.matches(parent)
//                        && parent.childCount >childPosition
//                        && parent.getChildAt(childPosition) == view)
//            }
//
//        }
//    }
}