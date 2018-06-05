package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.remote.RetrofitException
import com.cameronvwilliams.raise.ui.Navigator
import io.reactivex.Observable
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CreatePresenterTest {

    @Mock private lateinit var navigator: Navigator
    @Mock private lateinit var dm: DataManager
    @Mock private lateinit var actions: IntroContract.CreateViewActions

    private lateinit var createPresenter: IntroContract.CreateUserActions
    private val pokerGame = PokerGame("", DeckType.FIBONACCI, false)
    private val responsePokerGame = PokerGame("", DeckType.FIBONACCI, false)
    private val errorPokerGame = PokerGame("", DeckType.T_SHIRT, true)
    private val exception = RetrofitException("", "", null, RetrofitException.Kind.HTTP, null, null)

    @Before fun setup() {
        MockitoAnnotations.initMocks(this)

        createPresenter = CreatePresenter(navigator, dm)
        createPresenter.onViewCreated(actions)
    }

    @After fun tearDown() {
        createPresenter.onViewDestroyed()
    }

    @Test fun onCreateButtonClickedSuccess() {
        `when`(dm.createPokerGame(pokerGame)).thenReturn(Observable.just(responsePokerGame))
        `when`(actions.RADIO_FIBONACCI).thenReturn(0)

        createPresenter.onCreateButtonClicked("", "",0,false)

        val inOrder = Mockito.inOrder(actions, dm, navigator)
        inOrder.verify(dm).createPokerGame(pokerGame)
        inOrder.verify(actions).showLoadingView()
        inOrder.verify(actions).hideLoadingView()
        inOrder.verify(navigator).goToPendingView(responsePokerGame, "")
    }

    @Test fun onCreateButtonClickedError() {
        `when`(dm.createPokerGame(errorPokerGame)).thenReturn(Observable.error(exception))
        `when`(actions.RADIO_T_SHIRT).thenReturn(1)

        createPresenter.onCreateButtonClicked("", "",1,true)

        val inOrder = Mockito.inOrder(actions, dm)
        inOrder.verify(dm).createPokerGame(errorPokerGame)
        inOrder.verify(actions).showLoadingView()
        inOrder.verify(actions).hideLoadingView()
        inOrder.verify(actions).showDefaultErrorSnackBar()
    }

    @Test(expected = IllegalArgumentException::class) fun onCreateButtonIncorrectDeckType() {
        createPresenter.onCreateButtonClicked("", "",5,true)
    }

    @Test fun gameNameTextChangeEmptyNameAndNoGameType() {
        createPresenter.onGameNameTextChanged("", false)
        verify(actions).disableCreateButton()
    }

    @Test fun gameNameTextChangeNoGameType() {
        createPresenter.onGameNameTextChanged("Sprint Master Flex 3000", false)
        verify(actions).disableCreateButton()
    }

    @Test fun gameNameTextChangeEmptyName() {
        createPresenter.onGameNameTextChanged("", true)
        verify(actions).disableCreateButton()
    }

    @Test fun gameNameTextChange() {
        createPresenter.onGameNameTextChanged("Sprint War Z", true)
        verify(actions).enableCreateButton()
    }

    @Test fun deckTypeRadioButtonChange() {
        createPresenter.onDeckTypeRadioButtonChecked("Sprint Wars: A New Hope", true)
        verify(actions).enableCreateButton()
    }

    @Test fun deckTypeRadioButtonChangeEmptyName() {
        createPresenter.onDeckTypeRadioButtonChecked("", true)
        verify(actions).disableCreateButton()
    }

    @Test fun onBackPressed() {
        assertTrue(createPresenter.onBackPressed())
        verify(navigator).goBack()
    }
}