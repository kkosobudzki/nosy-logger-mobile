package com.nosy.logger

import io.grpc.ManagedChannelBuilder
import nosy_logger.LoggerGrpc
import nosy_logger.LoggerGrpc.LoggerBlockingStub
import nosy_logger.LoggerOuterClass.Log

internal class Logger(private val url: String, private val apiKey: String) {

  private val stub: LoggerBlockingStub by lazy {
    ManagedChannelBuilder.forTarget(url)
      .usePlaintext() // TODO use SSL
      .build()
      .let(LoggerGrpc::newBlockingStub)
      .also {
        "Logger connected to blocking grpc WITHOUT SSL".log()
      }
  }

  internal fun log(date: String, message: String) {
    "Logger log message: $message".log()

    Log.newBuilder()
      .setDate(date)
      .setMessage(message)
      .build()
      .let(stub::log)
  }
}
