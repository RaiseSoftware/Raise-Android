package com.cameronvwilliams.raise.ui.pending.views


import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_poker_info.*


class PokerInfoFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poker_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokerGame = arguments?.get("game") as PokerGame

        gameIdText.text = getString(R.string.text_game_id_value, pokerGame.gameId)

        if (!TextUtils.isEmpty(pokerGame.passcode)) {
            passcodeText.text =
                    getString(R.string.text_passcode_value, pokerGame.passcode.toUpperCase())
        } else {
            passcodeText.visibility = View.GONE
        }

        val decodedString = Base64.decode(pokerGame.qrcode.split(',')[1], Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        qrCodeImage.setImageBitmap(decodedByte)
    }

    companion object {
        fun newInstance(game: PokerGame): PokerInfoFragment {
            val fragment = PokerInfoFragment()
            val args = Bundle()
            args.putParcelable("game", game)
            fragment.arguments = args
            return fragment
        }
    }
}
