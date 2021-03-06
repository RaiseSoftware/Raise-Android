package com.cameronvwilliams.raise.ui.intro

import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.MainActivity
import com.cameronvwilliams.raise.ui.pending.PendingActivity
import com.cameronvwilliams.raise.util.enterTextIntoViewWithId
import com.cameronvwilliams.raise.util.tapViewWithId
import com.cameronvwilliams.raise.util.verifyViewIsDisabled
import com.cameronvwilliams.raise.util.verifyViewIsEnabled
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntroActivityTest {

    @get:Rule
    val rule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun goToJoinAndSuccessfullyJoin() {
        tapViewWithId(R.id.joinButton)
        verifyViewIsDisabled(R.id.joinButton)

        enterTextIntoViewWithId("Neo", R.id.userNameEditText)
        verifyViewIsDisabled(R.id.joinButton)

        enterTextIntoViewWithId("123", R.id.gameIdEditText)
        verifyViewIsDisabled(R.id.joinButton)

        enterTextIntoViewWithId("456", R.id.gameIdEditText)
        verifyViewIsEnabled(R.id.joinButton)

        tapViewWithId(R.id.joinButton)
        intended(hasComponent(PendingActivity::class.java.name))
    }

    @Test
    fun goToJoinAndRequirePasscode() {
        tapViewWithId(R.id.joinButton)
        verifyViewIsDisabled(R.id.joinButton)

        enterTextIntoViewWithId("Neo", R.id.userNameEditText)
        verifyViewIsDisabled(R.id.joinButton)

        enterTextIntoViewWithId("654321", R.id.gameIdEditText)
        verifyViewIsEnabled(R.id.joinButton)

        tapViewWithId(R.id.joinButton)
    }
}
