package com.cameronvwilliams.raise.data.remote

import com.cameronvwilliams.raise.data.model.event.SocketEvent
import com.cameronvwilliams.raise.data.model.event.StartGameEvent
import com.cameronvwilliams.raise.data.remote.SocketClient.Companion.START_GAME_EVENT
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.BehaviorSubject
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import okhttp3.OkHttpClient

class RxSocket(private val gson: Gson, okHttpClient: OkHttpClient, private val url: String) {

    private val connected: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    private lateinit var socket: Socket

    init {
        IO.setDefaultOkHttpCallFactory(okHttpClient)
        IO.setDefaultOkHttpWebSocketFactory(okHttpClient)
    }

    fun connect(token: String) {
        val opts = IO.Options()
        opts.query = "token=$token"
        opts.secure = true
        opts.transports = arrayOf(WebSocket.NAME)
        opts.path = "/socket/socket.io"

        socket = IO.socket(url, opts)
        this.socket.on(Socket.EVENT_CONNECT, { this.connected.onNext(true)})
        this.socket.on(Socket.EVENT_DISCONNECT, { this.connected.onNext(false)})
        socket.connect()
    }

    fun listen(event: String): Observable<Any> {
        return Observable.create({ observableEmitter ->
            observableEmitter.setDisposable(Disposables.fromRunnable {
                this.socket.off(event)
            })
            this.socket.once(event, { args ->
                val payload = args[0]
                observableEmitter.onNext(payload)
            })
        })
    }

//    fun <T> listen(event: String, payloadClass: Class<T>): Observable<T> {
//        return Observable.create({ observableEmitter ->
//            observableEmitter.setDisposable(Disposables.fromRunnable {
//                this.socket.off(event)
//            })
//            this.socket.once(event, { args ->
//                val jsonString = args[0] as String
//                val obj = gson.fromJson(jsonString, payloadClass)
//                observableEmitter.onNext(obj)
//            })
//        })
//    }

    fun <T> listen(event: String, payloadClass: Class<T>): BehaviorSubject<T> {
        val subject: BehaviorSubject<T> = BehaviorSubject.create()

        socket.once(event, { args ->
            val jsonString = args[0] as String
            val obj = gson.fromJson(jsonString, payloadClass)
            subject.onNext(obj)
        })

        subject.doOnDispose {
            this.socket.off(event)
        }

        return subject
    }

    fun emit(event: String) {
        this.socket.emit(event)
    }

    fun emit(event: String, payload: Any) {
        this.socket.emit(event, payload)
    }

    fun disconnect() {
        socket.off()
        socket.disconnect()
    }
}