package com.nosy.logger

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap

class NosyLoggerModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private lateinit var logger: Logger

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun init(config: ReadableMap) {
    logger = Logger(
      url = "127.0.0.1:8080", // TODO auto initialize from env in js
      apiKey = config.getString("apiKey").orEmpty()
    )
  }

  @ReactMethod
  fun log(messages: ReadableArray, promise: Promise) {
    if (this::logger.isInitialized) {
      logger.log(messages.toLogs())

      promise.resolve(true)
    } else {
      promise.reject(IllegalStateException("Not initialized - make sure to call init() before"))
    }
  }

  companion object {
    const val NAME = "NosyLogger"
  }
}
