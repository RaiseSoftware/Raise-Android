package com.cameronvwilliams.raise.ui

import io.reactivex.disposables.CompositeDisposable
import javax.annotation.OverridingMethodsMustInvokeSuper

abstract class BasePresenter {
    protected lateinit var viewSubscriptions: CompositeDisposable

    @OverridingMethodsMustInvokeSuper
    open fun onViewCreated(v: BaseFragment) {
        viewSubscriptions = CompositeDisposable()
    }

    @OverridingMethodsMustInvokeSuper
    open fun onViewDestroyed() {
        viewSubscriptions.clear()
    }

    open fun onBackPressed(): Boolean = false
}
