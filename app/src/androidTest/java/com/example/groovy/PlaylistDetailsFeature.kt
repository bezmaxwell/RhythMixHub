package com.example.groovy

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.CoreMatchers
import org.junit.Test

class PlaylistDetailsFeature: BaseUITest() {

    @Test
    fun displayPlaylistNameAndDetails(){
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.playlist_image),
                ViewMatchers.isDescendantOfA(childOf(ViewMatchers.withId(R.id.playlist_list), 0))
            )
        ).perform(ViewActions.click())

        assertDisplayed("Hard Rock Cafe")


    }
}