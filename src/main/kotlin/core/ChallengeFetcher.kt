package com.anuragxone.bgutils.core

import com.anuragxone.bgutils.utils.*
import kotlinx.coroutines.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend fun create(
    bgConfig: BgConfig,
    interpreterHash: String? = null
): DescrambledChallenge? {
    val requestKey = bgConfig.requestKey

//    bgConfig.fetch ?: throw IllegalArgumentException("[Challenge]: Fetch function not provided")

    val payload = mutableListOf(requestKey)
    if (interpreterHash != null) {
        payload.add(interpreterHash)
    }

    val response: HttpResponse = bgConfig.fetch(
        buildURL("Create", bgConfig.useYouTubeAPI)
    ) {
        method = HttpMethod.Post
        headers {
            append(HttpHeaders.ContentType, "application/json+protobuf")
            append("x-goog-api-key", GOOG_API_KEY)
            append("x-user-agent", "grpc-web-javascript/0.1")
        }
        setBody(Json.encodeToString(payload))
    }

    if (!response.status.isSuccess()) {
        throw Exception("[Challenge]: Failed to fetch challenge: ${response.status.value}")
    }

    val rawData: List<Any> = Json.decodeFromString(response.bodyAsText())

    return parseChallengeData(rawData)
}

fun parseChallengeData(rawData: Map<String, Any?>): DescrambledChallenge? {
    var challengeData: List<Any?> = emptyList()

    if (rawData.size > 1 && rawData[1] is String) {
        val descrambled = descramble(rawData[1] as String)
        challengeData = try {
            JSONObject(descrambled ?: "[]").let { it.toList() }
        } catch (e: JSONException) {
            emptyList()
        }
    } else if (rawData.isNotEmpty() && rawData[0] is Map<*, *>) {
        challengeData = rawData[0] as List<Any?>
    }

    val (messageId, wrappedScript, wrappedUrl, interpreterHash, program, globalName, _, clientExperimentsStateBlob) = challengeData

    val privateDoNotAccessOrElseSafeScriptWrappedValue = (wrappedScript as? List<*>)
        ?.find { it is String } as? String
    val privateDoNotAccessOrElseTrustedResourceUrlWrappedValue = (wrappedUrl as? List<*>)
        ?.find { it is String } as? String

    return DescrambledChallenge(
        messageId,
        InterpreterJavascript(
            privateDoNotAccessOrElseSafeScriptWrappedValue,
            privateDoNotAccessOrElseTrustedResourceUrlWrappedValue
        ),
        interpreterHash,
        program,
        globalName,
        clientExperimentsStateBlob
    )
}

