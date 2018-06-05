package com.cameronvwilliams.raise.util.deeplink

import com.airbnb.deeplinkdispatch.DeepLinkSpec
import com.cameronvwilliams.raise.BuildConfig


@DeepLinkSpec(prefix = [BuildConfig.BASE_URL])
annotation class WebDeepLink(vararg val value: String)