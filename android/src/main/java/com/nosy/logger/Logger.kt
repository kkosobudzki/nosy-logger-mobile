package com.nosy.logger

import io.grpc.ManagedChannelBuilder
import io.grpc.Metadata
import io.grpc.stub.MetadataUtils
import nosy_logger.LoggerGrpc
import nosy_logger.LoggerGrpc.LoggerStub
import nosy_logger.LoggerOuterClass.Logs

internal class Logger(private val config: Config) {

  private val stub: LoggerStub by lazy {
    val headers = Metadata().apply {
      put(API_KEY_METADATA, config.apiKey)
    }

    ManagedChannelBuilder.forTarget(config.url)
      .usePlaintext() // TODO use SSL
      .build()
      .let(LoggerGrpc::newStub)
      .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers))
      .also {
        "Logger connected to blocking grpc WITHOUT SSL".log()
      }
  }

  internal fun log(logs: Logs, onCompleted: () -> Unit, onError: (Throwable?) -> Unit) {
    stub.log(logs, DelegatedStreamObserver(whenCompleted = onCompleted, whenError = onError))
  }

  private companion object {
    val API_KEY_METADATA: Metadata.Key<String> =
      Metadata.Key.of("api-key", Metadata.ASCII_STRING_MARSHALLER)
  }
}
