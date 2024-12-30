package com.anuragxone.bgutils.utils

import io.ktor.client.request.*
import io.ktor.client.statement.*

// val FetchFunction: suspend (String, HttpRequestBuilder.() -> Unit) -> HttpResponse

data class InterpreterJavascript(
    val privateDoNotAccessOrElseSafeScriptWrappedValue: String? = null,
    val privateDoNotAccessOrElseTrustedResourceUrlWrappedValue: String? = null
)

data class DescrambledChallenge(
    val messageId: String? = null,
    val interpreterJavascript: InterpreterJavascript,
    val interpreterHash: String,
    val program: String,
    val globalName: String,
    val clientExperimentsStateBlob: String? = null
)

data class BgConfig(
    val fetch: suspend (String, HttpRequestBuilder.() -> Unit ) -> HttpResponse,
    val globalObj: Map<String, Any>,
    val identifier: String,
    val requestKey: String,
    val useYouTubeAPI: Boolean? = null
)
