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
import kotlinx.android.synthetic.main.pending_poker_info_fragment.*
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import timber.log.Timber
import android.content.ContentValues
import android.provider.MediaStore
import java.io.*


class PokerInfoFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pending_poker_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokerGame = arguments?.get("game") as PokerGame

        gameIdText.text = getString(R.string.text_game_id_value, pokerGame.gameId)

        if (!TextUtils.isEmpty(pokerGame.passcode)) {
            passcodeText.text =
                    getString(R.string.text_passcode_value, pokerGame.passcode!!.toUpperCase())
        } else {
            passcodeText.visibility = View.GONE
        }

        val decodedString = Base64.decode(pokerGame.qrcode!!.split(',')[1], Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        qrCodeImage.setImageBitmap(decodedByte)

        qrCodeImage.setOnLongClickListener {
            val share = Intent(Intent.ACTION_SEND)
            share.type = "image/jpeg"

            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "title")
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            val uri = activity!!.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            val outstream: OutputStream
            try {
                outstream = activity!!.contentResolver.openOutputStream(uri)
                decodedByte.compress(Bitmap.CompressFormat.JPEG, 100, outstream)
                outstream.close()
            } catch (e: Exception) {
                Timber.e(e)
            }


            share.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(share, "Share Image"))
            true
        }
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
