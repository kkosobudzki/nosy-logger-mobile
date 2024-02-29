package dev.nosytools.rnlogger

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import nosy_logger.LoggerOuterClass.Level
import nosy_logger.LoggerOuterClass.Log
import nosy_logger.LoggerOuterClass.Logs

private fun String.toLevel(): Level =
  when (this) {
    "debug" -> Level.LEVEL_DEBUG
    "info" -> Level.LEVEL_INFO
    "warn" -> Level.LEVEL_WARN
    "error" -> Level.LEVEL_ERROR
    else -> throw IllegalArgumentException("Unsupported log level")
  }

internal fun ReadableMap.toLog(): Log =
  Log.newBuilder()
    .setDate(getString("date"))
    .setMessage(getString("message"))
    .setLevel(getString("level")?.toLevel())
    .build()

internal fun ReadableArray.toLogsList(): List<Log> =
  mutableListOf<Log>()
    .also { list ->
      for (i in 0 until size()) {
        list.add(getMap(i).toLog())
      }
    }

internal fun List<Log>.toLogs(): Logs =
  Logs.newBuilder()
    .also { builder -> forEach(builder::addLogs) }
    .build()
