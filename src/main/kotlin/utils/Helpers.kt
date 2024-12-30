package com.anuragxone.bgutils.utils

fun buildURL(endpointName: String, useYouTubeAPI: Boolean? = null): String {
    val baseUrl = if (useYouTubeAPI == true) YT_BASE_URL else GOOG_BASE_URL
    val apiPath = if (useYouTubeAPI == true) "api/jnn/v1" else "\$rpc/google.internal.waa.v1.Waa"
    return "$baseUrl/$apiPath/$endpointName"
}
