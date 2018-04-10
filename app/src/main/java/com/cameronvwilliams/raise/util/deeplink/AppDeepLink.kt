package com.cameronvwilliams.raise.util.deeplink

import com.airbnb.deeplinkdispatch.DeepLinkSpec


@DeepLinkSpec(prefix = ["raise://"])
annotation class AppDeepLink(vararg val value: String)