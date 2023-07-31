package com.nosylogger

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise

class NosyLoggerModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun log(message: String, promise: Promise) {
    // TODO log message with grpc here

    promise.resolve();
  }

  companion object {
    const val NAME = "NosyLogger"
  }
}
