package com.nosy.logger

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import nosy_logger.LoggerOuterClass.Log
import nosy_logger.LoggerOuterClass.Logs

internal fun ReadableMap.toLog(): Log =
  Log.newBuilder()
    .setDate(getString("date"))
    .setMessage(getString("message"))
    .build()

internal fun ReadableArray.toLogs(): Logs =
  Logs.newBuilder()
    .apply {
      for (i in 0..size()) {
        addLogs(getMap(i).toLog())
      }
    }
    .build()
