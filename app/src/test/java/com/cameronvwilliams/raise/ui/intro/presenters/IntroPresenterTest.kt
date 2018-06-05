package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.ui.Navigator
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class IntroPresenterTest {

    @Mock private lateinit var navigator: Navigator
    @Mock private lateinit var introView: IntroContract.IntroViewActions

    private lateinit var introPresenter: IntroContract.IntroUserActions

    @Before fun setup() {
        MockitoAnnotations.initMocks(this)

        introPresenter = IntroPresenter(navigator)
        introPresenter.onViewCreated(introView)
    }

    @After fun tearDown() {
        introPresenter.onViewDestroyed()
    }

    @Test fun goToCreateGameScreen() {
        introPresenter.onCreateButtonClicked()

        verify(navigator).goToCreateGame()
    }

    @Test fun goToJoinGameScreen() {
        introPresenter.onJoinButtonClicked()

        verify(navigator).goToJoinGame()
    }
}
