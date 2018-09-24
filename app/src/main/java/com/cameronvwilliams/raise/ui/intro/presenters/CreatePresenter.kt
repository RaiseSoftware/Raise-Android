package com.cameronvwilliams.raise.ui.intro.presenters

import android.graphics.Bitmap
import android.util.Base64
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.views.CreateFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import io.reactivex.Single
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.withLatestFrom
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.util.*

private const val WHITE = -0x1
private const val BLACK = -0x1000000

class CreatePresenter(private val navigator: Navigator, private val dm: DataManager): BasePresenter() {

    lateinit var view: CreateFragment

    private lateinit var createdPokerGame: PokerGame
    private var selectedUserName: String = ""

    override fun onViewCreated(v: BaseFragment) {
        super.onViewCreated(v)
        view = v as CreateFragment

        val backPresses = view.backPresses()
            .subscribe {
                onBackPressed()
            }

        val createFormDetails = Observables.combineLatest(
            view.deckTypeChanges(),
            view.nameChanges(),
            view.gameNameChanges(),
            view.passcodeChanges()
        ) { deckType: DeckType, userName: CharSequence, gameName: CharSequence, requirePasscode: Boolean ->
            CreateDetails(deckType, userName.toString(), gameName.toString(), requirePasscode)
        }.distinctUntilChanged().doOnNext {
            if (it.isValid()) {
                view.enableCreateButton()
            } else {
                view.disableCreateButton()
            }
        }

        val gameRequests = view.createGameRequests()
            .withLatestFrom(createFormDetails) { _, details ->
                details
            }
            .flatMapSingle { onCreateClicked(it) }
            .doOnEach {
                view.showLoadingView()
                view.disableCreateButton()
            }
            .subscribe({ result: Result ->
                when (result.type) {
                    CreatePresenter.ResultType.SUCCESS -> onCreateSuccess(result.data!!)
                    CreatePresenter.ResultType.FAILURE -> onCreateFailure()
                    CreatePresenter.ResultType.PASSCODE -> TODO()
                }
            }, { t ->
                throw OnErrorNotImplementedException(t)
            })

        val adClosed = view.adClosed()
            .subscribe {
                navigator.goToPendingView(createdPokerGame, selectedUserName, true)
            }

        viewSubscriptions.addAll(gameRequests, backPresses, adClosed)
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()

        return true
    }

    private fun onCreateClicked(details: CreateDetails): Single<Result>? {
        return if (details.requiresPassode()) {
            Single.just(Result(ResultType.PASSCODE, null))
        } else {
            val game = PokerGame(details.gameName, details.deckType, details.requirePasscode)
            val player = Player(details.userName)
            val qrBitmap = encodeAsBitmap("{ gameName: ${details.gameName} }", BarcodeFormat.QR_CODE, 300, 300)
            val byteArrayOutputStream = ByteArrayOutputStream()
            qrBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)

            game.qrcode = encoded

            dm.createGame(game, player)
                .map { pokerGame -> Result(ResultType.SUCCESS, pokerGame) }
                .onErrorReturn { t ->
                    Timber.e(t)
                    Result(ResultType.FAILURE, null)
                }
        }
    }

    private fun onCreateSuccess(pokerGame: PokerGame) {
        view.hideLoadingView()
        view.enableCreateButton()
        navigator.goToPendingView(pokerGame, "", true)
    }

    private fun onCreateFailure() {
        view.hideLoadingView()
        view.enableCreateButton()
        view.showDefaultErrorSnackBar()
    }

    // val x = encodeAsBitmap("{}", BarcodeFormat.QR_CODE, 500, 500)
    private fun encodeAsBitmap(contents: String, format: BarcodeFormat, desiredWidth: Int, desiredHeight: Int): Bitmap {
        val hints: Hashtable<EncodeHintType, Any> = Hashtable(2)
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
        hints[EncodeHintType.MARGIN] = 1

        val writer = MultiFormatWriter()
        val result = writer.encode(contents, format, desiredWidth, desiredHeight, hints)
        val width = result.width
        val height = result.height
        val pixels = IntArray(width * height)
        // All are 0, or black, by default
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (result.get(x, y)) BLACK else WHITE
            }
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    private data class CreateDetails(val deckType: DeckType?, val userName: String, val gameName: String, val requirePasscode: Boolean) {
        fun isValid(): Boolean {
            return deckType != DeckType.NONE && userName.isNotEmpty() && gameName.isNotEmpty()
        }

        fun requiresPassode(): Boolean {
            return requirePasscode
        }
    }

    private data class Result(val type: ResultType, val data: PokerGame?)

    private enum class ResultType {
        SUCCESS,
        FAILURE,
        PASSCODE
    }
}
