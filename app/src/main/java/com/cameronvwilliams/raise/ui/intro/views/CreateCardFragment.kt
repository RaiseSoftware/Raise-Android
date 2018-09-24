package com.cameronvwilliams.raise.ui.intro.views

import android.os.Bundle
import android.support.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
import kotlinx.android.synthetic.main.intro_create_card_view.*
import javax.inject.Inject

class CreateCardFragment: BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_create_card_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val t = ChangeBounds()
        t.duration = 300L

        parentFragment?.enterTransition = t
        enterTransition = t

        parentFragment?.exitTransition = t
        exitTransition = t

        createCardView.setOnClickListener {
            navigator.goToCreateGame(
                selectDeckText,
                fibonacciRadio,
                tshirtRadio,
                joinForm,
                userNameEditText,
                formDivider,
                gameNameText,
                requirePasscodeCheckbox,
                createGameView
            )
        }

        userNameEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                navigator.goToCreateGame(
                    selectDeckText,
                    fibonacciRadio,
                    tshirtRadio,
                    joinForm,
                    userNameEditText,
                    formDivider,
                    gameNameText,
                    requirePasscodeCheckbox,
                    createGameView
                )
            }
        }

        fibonacciRadio.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                navigator.goToCreateGame(
                    selectDeckText,
                    fibonacciRadio,
                    tshirtRadio,
                    joinForm,
                    userNameEditText,
                    formDivider,
                    gameNameText,
                    requirePasscodeCheckbox,
                    createGameView
                )
            }
        }

        tshirtRadio.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                navigator.goToCreateGame(
                    selectDeckText,
                    fibonacciRadio,
                    tshirtRadio,
                    joinForm,
                    userNameEditText,
                    formDivider,
                    gameNameText,
                    requirePasscodeCheckbox,
                    createGameView
                )
            }
        }

        gameNameText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                navigator.goToCreateGame(
                    selectDeckText,
                    fibonacciRadio,
                    tshirtRadio,
                    joinForm,
                    userNameEditText,
                    formDivider,
                    gameNameText,
                    requirePasscodeCheckbox,
                    createGameView
                )
            }
        }

        requirePasscodeCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            navigator.goToCreateGame(
                selectDeckText,
                fibonacciRadio,
                tshirtRadio,
                joinForm,
                userNameEditText,
                formDivider,
                gameNameText,
                requirePasscodeCheckbox,
                createGameView
            )
        }
    }

    companion object {
        fun newInstance(): CreateCardFragment {
            return CreateCardFragment()
        }
    }
}