package com.cameronvwilliams.raise.data

import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import com.cameronvwilliams.raise.data.local.RaisePreferences
import com.cameronvwilliams.raise.data.model.*
import com.cameronvwilliams.raise.data.remote.AuthService
import com.cameronvwilliams.raise.data.remote.SocketAPI
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import durdinapps.rxfirebase2.RxFirestore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor(
    private val db: FirebaseFirestore,
    private val authService: AuthService,
    private val socketClient: SocketAPI,
    private val raisePreferences: RaisePreferences
) {

    fun signIn() = authService.signInAnonymously()

    fun getUserId() = authService.getUserId()

    fun setOfflineDeckType(deckType: DeckType) {
        raisePreferences.setDeckType(deckType)
    }

    fun getOfflineDeckType() = raisePreferences.getDeckType()

    fun offlineDeckType() = raisePreferences.getDeckTypeObservable()

    fun createGame(game: PokerGame): Single<PokerGame> {
        return RxFirestore.addDocument(db.collection("game"), game)
            .flatMapMaybe {
                RxFirestore.getDocument(it)
            }
            .flatMapSingle {
                Single.just(it.toObject(PokerGame::class.java)?.withId(it.id))
            }
    }

    fun findGame(name: String, passcode: String? = ""): Single<PokerGame> {
        val query = passcode?.let {
            db.collection("game")
                .whereEqualTo("gameName", name)
                .whereEqualTo("passcode", passcode)
        } ?: db.collection("game")
            .whereEqualTo("gameName", name)
            .whereEqualTo("passcode", passcode)

        return RxFirestore.getCollection(query)
            .flatMapSingle {
                Single.just(it.documents[0])
            }
            .flatMapMaybe {
                RxFirestore.getDocument(it.reference)
            }
            .flatMapSingle {
                Single.just(it.toObject(PokerGame::class.java)?.withId(it.id))
            }
    }

    fun joinGame(uid: String, player: Player): Completable {
        val docRef = db.collection("game").document(uid)
        val updateMap = HashMap<String, Any>()
        updateMap["uid"] = player.uid!!
        updateMap["name"] = player.name!!
        updateMap["roles"] = player.roles

        return RxFirestore.updateDocument(docRef, "players",  FieldValue.arrayUnion(updateMap))
    }

    fun leaveGame() {
        socketClient.disconnect()
    }

    fun startGame() {

        socketClient.sendStartGameMessage()
    }

    fun endGame() {
        socketClient.sendEndGameMessage()
    }

    fun submitCard(card: Card) {
        socketClient.sendSubmitCardMessage(card)
    }

    fun getPlayersInGame(): Flowable<Pair<List<Player>, DiffUtil.DiffResult>> {
        return socketClient.onPlayersInGameChange()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getActivePlayersCards(): Flowable<Pair<List<ActiveCard>, DiffUtil.DiffResult>> {
        return socketClient.onActiveCardSetChange()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGameStart(): Completable {
        return socketClient.onGameStart()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGameEnd(): Completable {
        return socketClient.onGameEnd()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUserStoriesForGame(): Flowable<Story> {
        return socketClient.onNextUserStory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFibonacciCards(): ArrayList<Card> {
        return arrayListOf(
            Card(DeckType.FIBONACCI, CardValue.ZERO),
            Card(DeckType.FIBONACCI, CardValue.ONE_HALF),
            Card(DeckType.FIBONACCI, CardValue.ONE),
            Card(DeckType.FIBONACCI, CardValue.TWO),
            Card(DeckType.FIBONACCI, CardValue.THREE),
            Card(DeckType.FIBONACCI, CardValue.FIVE),
            Card(DeckType.FIBONACCI, CardValue.EIGHT),
            Card(DeckType.FIBONACCI, CardValue.THIRTEEN),
            Card(DeckType.FIBONACCI, CardValue.TWENTY),
            Card(DeckType.FIBONACCI, CardValue.FORTY),
            Card(DeckType.FIBONACCI, CardValue.ONE_HUNDRED),
            Card(DeckType.FIBONACCI, CardValue.INFINITY),
            Card(DeckType.FIBONACCI, CardValue.QUESTION_MARK),
            Card(DeckType.FIBONACCI, CardValue.COFFEE)
        )
    }

    fun getTShirtCards(): ArrayList<Card> {
        return arrayListOf(
            Card(DeckType.T_SHIRT, CardValue.X_SMALL),
            Card(DeckType.T_SHIRT, CardValue.SMALL),
            Card(DeckType.T_SHIRT, CardValue.MEDIUM),
            Card(DeckType.T_SHIRT, CardValue.LARGE),
            Card(DeckType.T_SHIRT, CardValue.X_LARGE),
            Card(DeckType.T_SHIRT, CardValue.QUESTION_MARK),
            Card(DeckType.T_SHIRT, CardValue.COFFEE)
        )
    }
}