package com.example.dragonsairlines

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.dragonsairlines.ui.FlightsActivity
import com.example.dragonsairlines.util.RecyclerViewMatcher

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class FlightsActivityTest: BaseUiTest(){


    @Test
    fun showFlights() {
        launchActivity<FlightsActivity>()
        onView(withId(R.id.flights_view)).check(matches(isDisplayed()))

    }

    @Test
    fun filterFlightsByValidPrice() {
        launchActivity<FlightsActivity>()
            onView(withId(R.id.et_from_price))
                .perform(typeText("8278"), ViewActions.closeSoftKeyboard())
            onView(withId(R.id.et_to_price))
                .perform(typeText("9000"), ViewActions.closeSoftKeyboard())
            onView(withId(R.id.btn_search_flights)).perform(click())
        onView( RecyclerViewMatcher(R.id.flights_view)
            .atPositionOnView(0, R.id.income))
            .check(matches(withText("White Harbor -> Volantis")))

    }
}