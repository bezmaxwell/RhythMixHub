package com.example.groovy

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotExist
import org.hamcrest.CoreMatchers
import org.junit.Test

class PlaylistDetailsFeature: BaseUITest() {

    @Test
    fun displayPlaylistNameAndDetails(){
        navigateToPlaylistDetails(0)

        assertDisplayed("Hard Rock Cafe")
    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylistDetails() {
        IdlingRegistry.getInstance().unregister(idlingResource)

        Thread.sleep(3000)
        navigateToPlaylistDetails(0)

        assertDisplayed(R.id.details_loader)
    }

    @Test
    fun hidesLoader() {
        navigateToPlaylistDetails(0)

        assertNotDisplayed(R.id.details_loader)
    }
    @Test
    fun displayErrorMessageWhenNetworkFails() {

        navigateToPlaylistDetails(1)

        assertDisplayed(R.string.generic_error)
    }

    @Test
    fun hidesErrorMessage() {
        navigateToPlaylistDetails(2)

        Thread.sleep(3000)

        assertNotExist(R.string.generic_error)
    }

    private fun navigateToPlaylistDetails(row:Int) {
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.playlist_image),
                ViewMatchers.isDescendantOfA(
                    childOf(
                        ViewMatchers.withId(R.id.playlist_list),
                        row
                    )
                )
            )
        ).perform(ViewActions.click())

        assertDisplayed("Hard Rock Cafe")
    }
}