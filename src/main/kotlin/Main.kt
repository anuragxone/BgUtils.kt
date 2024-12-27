package com.anuragxone.bgutils

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

suspend fun main() {
    println("Hello World!")
    val client = HttpClient(OkHttp)
    val response = client.request("https://ktor.io")
    println(response.bodyAsText())
    client.close()
}