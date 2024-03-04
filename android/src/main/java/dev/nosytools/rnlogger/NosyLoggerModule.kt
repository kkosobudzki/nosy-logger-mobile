package dev.nosytools.rnlogger

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import dev.nosytools.logger.Logger

class NosyLoggerModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private val logger by lazy { Logger(reactContext) }

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun init(apiKey: String) {
    logger.init(apiKey)
  }

  @ReactMethod
  fun debug(message: String) {
    logger.debug(message)
  }

  @ReactMethod
  fun info(message: String) {
    logger.info(message)
  }

  @ReactMethod
  fun warning(message: String) {
    logger.warning(message)
  }

  @ReactMethod
  fun error(message: String) {
    logger.error(message)
  }

  companion object {
    const val NAME = "NosyLogger"
  }
}
