package com.cameronvwilliams.raise.data.store


class Store<A, S>(private val reducer: Reducer<A, S>, private var state: S, vararg middleware: Middleware<A, S>) {

    private val subscribers = arrayListOf<Runnable>()

    private val dispatcher = object : Middleware<A, S> {
        override fun dispatch(store: Store<A, S>, action: A, next: NextDispatcher<A>?) {
            synchronized(this) {
                state = store.reducer.reduce(action, state)
            }
            for (i in subscribers.indices) {
                store.subscribers[i].run()
            }
        }
    }

    private val next = arrayListOf<NextDispatcher<A>>()

    init {
        this.next.add(object : NextDispatcher<A> {
            override fun dispatch(action: A) {
                dispatcher.dispatch(this@Store, action, null)
            }
        })

        for (i in middleware.indices.reversed()) {
            val mw = middleware[i]
            val n = next[0]
            next[0] = object : NextDispatcher<A> {
                override fun dispatch(action: A) {
                    mw.dispatch(this@Store, action, n)
                }
            }
        }
    }

    fun dispatch(action: A): S? {
        this.next[0].dispatch(action)
        return this.state
    }

    fun subscribe(r: Runnable): Runnable {
        this.subscribers.add(r)
        return Runnable { subscribers.remove(r) }
    }

    interface Reducer<A, S> {
        fun reduce(action: A, currentState: S?): S
    }

    interface Middleware<A, S> {
        fun dispatch(store: Store<A, S>, action: A, next: NextDispatcher<A>?)
    }

    interface NextDispatcher<A> {
        fun dispatch(action: A)
    }
}