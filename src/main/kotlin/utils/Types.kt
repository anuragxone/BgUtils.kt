package com.anuragxone.bgutils.utils

import io.ktor.client.request.*
import io.ktor.client.statement.*

data class BgConfig(
    val fetch: suspend (String, HttpRequestBuilder.() -> Unit)-> HttpResponse,
    val globalObj: Map<String, Any>,
    val identifier: String,
    val requestKey: String,
    val useYouTubeAPI: Boolean? = null
)
