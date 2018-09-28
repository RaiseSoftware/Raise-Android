package com.cameronvwilliams.raise.util

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers.not


fun tapViewWithId(@IdRes id: Int) {
    onView(withId(id)).perform(click())
}

fun enterTextIntoViewWithId(text: String, @IdRes id: Int) {
    onView(withId(id)).perform(typeText(text))
}

fun verifyViewIsEnabled(@IdRes id: Int) {
    onView(withId(id)).check(matches(isEnabled()))
}

fun verifyViewIsDisabled(@IdRes id: Int) {
    onView(withId(id)).check(matches(not(isEnabled())))
}