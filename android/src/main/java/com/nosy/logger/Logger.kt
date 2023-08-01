package com.nosy.logger

import io.grpc.ManagedChannelBuilder
import io.grpc.Metadata
import io.grpc.stub.MetadataUtils
import nosy_logger.LoggerGrpc
import nosy_logger.LoggerGrpc.LoggerBlockingStub
import nosy_logger.LoggerOuterClass.Log

internal class Logger(private val url: String, private val apiKey: String) {

  private val stub: LoggerBlockingStub by lazy {
    val headers = Metadata().apply {
      put(API_KEY_METADATA, apiKey)
    }

    ManagedChannelBuilder.forTarget(url)
      .usePlaintext() // TODO use SSL
      .build()
      .let(LoggerGrpc::newBlockingStub) // TODO make it non blocking
      .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
      .also {
        "Logger connected to blocking grpc WITHOUT SSL".log()
      }
  }

  internal fun log(date: String, message: String) {
    Log.newBuilder()
      .setDate(date)
      .setMessage(message)
      .build()
      .let(stub::log)
  }

  private companion object {
    val API_KEY_METADATA: Metadata.Key<String> = Metadata.Key.of("api-key", Metadata.ASCII_STRING_MARSHALLER)
  }
}
