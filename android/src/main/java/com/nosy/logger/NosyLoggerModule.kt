package com.nosy.logger

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise

class NosyLoggerModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private val logger: Logger by lazy {
    Logger("[::1]:8080") // TODO move to config
  }

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun log(date: String, message: String, promise: Promise) {
    logger.log(date, message)

    promise.resolve(true)
  }

  companion object {
    const val NAME = "NosyLogger"
  }
}
