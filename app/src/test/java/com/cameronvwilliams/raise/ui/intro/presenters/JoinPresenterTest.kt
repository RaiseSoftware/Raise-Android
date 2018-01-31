package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.IntroContract
import com.google.gson.Gson
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class JoinPresenterTest {

    @Mock private lateinit var navigator: Navigator
    @Mock private lateinit var dm: DataManager
    @Mock private lateinit var gson: Gson
    @Mock private lateinit var actions: IntroContract.JoinViewActions

    private lateinit var joinPresenter: IntroContract.JoinUserActions


    @Before fun setup() {
        MockitoAnnotations.initMocks(this)

        joinPresenter = JoinPresenter(navigator, dm, gson)
        joinPresenter.onViewCreated(actions)
    }

    @After fun tearDown() {
        joinPresenter.onViewDestroyed()
    }

    @Test fun onNameTextChanged() {
    }

    @Test fun onBackPressed() {
        assertTrue(joinPresenter.onBackPressed())
        Mockito.verify(navigator).goToIntro()
    }
}
