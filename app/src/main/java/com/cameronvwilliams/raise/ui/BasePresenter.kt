package com.cameronvwilliams.raise.ui

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter {
    protected lateinit var viewSubscriptions: CompositeDisposable

    open fun onViewCreated(v: BaseFragment) {
        viewSubscriptions = CompositeDisposable()
    }

    open fun onViewDestroyed() {
        viewSubscriptions.clear()
    }

    open fun onBackPressed(): Boolean = false
}
