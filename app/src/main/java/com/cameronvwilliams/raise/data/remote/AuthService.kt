package com.cameronvwilliams.raise.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import durdinapps.rxfirebase2.RxFirebaseAuth
import io.reactivex.Maybe
import javax.inject.Inject

class AuthService @Inject constructor(
    private val auth: FirebaseAuth
) {

    private var user: FirebaseUser? = null

    fun signInAnonymously(): Maybe<Boolean> {
        return RxFirebaseAuth.signInAnonymously(auth)
            .map { authResult ->
                user = authResult.user
                authResult.user != null
            }
    }

    fun getUserId(): String? {
        return user?.uid
    }
}