package com.nosy.logger

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.nosy.logger.Config.Companion.toConfig
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security

class NosyLoggerModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private lateinit var logger: Logger

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun init(config: ReadableMap, promise: Promise) {
    if (this::logger.isInitialized) {
      return promise.reject(IllegalStateException("Already initialized"))
    }

    Logger(config.toConfig())
      .run {
        init(
          onCompleted = {
            logger = this

            promise.resolve(true)
          },
          onError = promise::reject
        )
      }
  }

  @ReactMethod
  fun log(messages: ReadableArray, promise: Promise) {
    if (this::logger.isInitialized) {
      logger.log(
        messages.toLogsList(),
        onCompleted = { promise.resolve(true) },
        onError = promise::reject
      )
    } else {
      promise.reject(IllegalStateException("Not initialized - make sure to call init() before"))
    }
  }

  companion object {
    const val NAME = "NosyLogger"

    init {
      Security.removeProvider("BC");
      Security.addProvider(BouncyCastleProvider())
    }
  }
}
