package com.cameronvwilliams.raise.util

import android.support.annotation.IdRes
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isEnabled
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