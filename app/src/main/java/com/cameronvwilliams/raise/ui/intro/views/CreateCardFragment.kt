package com.cameronvwilliams.raise.ui.intro.views

import android.os.Bundle
import android.support.transition.ChangeBounds
import android.support.transition.ChangeTransform
import android.support.transition.TransitionSet
import android.support.v4.app.SharedElementCallback
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
import com.jakewharton.rxbinding2.widget.checked
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
        t.duration = 10000L
        t.interpolator = AccelerateDecelerateInterpolator()

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
                requirePasscodeCheckbox
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
                    requirePasscodeCheckbox
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
                    requirePasscodeCheckbox
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
                    requirePasscodeCheckbox
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
                    requirePasscodeCheckbox
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
                requirePasscodeCheckbox
            )
        }
    }

    companion object {
        fun newInstance(): CreateCardFragment {
            return CreateCardFragment()
        }
    }
}