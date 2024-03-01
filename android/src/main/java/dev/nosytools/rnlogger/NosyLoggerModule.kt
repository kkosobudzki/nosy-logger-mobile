package dev.nosytools.rnlogger

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import dev.nosytools.rnlogger.Config.Companion.toConfig
import dev.nosytools.logger.Logger

class NosyLoggerModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private lateinit var logger: Logger

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun init(config: ReadableMap, promise: Promise) {
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
    logger.log(
      messages.toLogsList(),
      onCompleted = { promise.resolve(true) },
      onError = promise::reject
    )
  }

  companion object {
    const val NAME = "NosyLogger"
  }
}
