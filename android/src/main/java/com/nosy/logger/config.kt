package com.nosy.logger

import com.facebook.react.bridge.ReadableMap

data class Config(val url: String, val apiKey: String) {
  companion object {
    fun ReadableMap.toConfig(): Config =
      Config(
        url = BuildConfig.COLLECTOR_URL,
        apiKey = getString("apiKey").orThrow("Missing api key")
      )
  }
}

private fun String?.orThrow(message: String): String =
  this ?: throw IllegalArgumentException(message)
