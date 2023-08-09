package com.nosy.logger

import java.security.KeyFactory
import java.security.PublicKey
import java.security.SecureRandom
import java.security.spec.X509EncodedKeySpec
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

internal fun String.toPublicKey(): PublicKey =
  decode()
    .let(::X509EncodedKeySpec)
    .let { spec ->
      KeyFactory.getInstance("ChaCha20").generatePublic(spec)
    }

internal fun generateSecretKey(): SecretKey =
  KeyGenerator.getInstance("ChaCha20")
    .apply {
      init(256, SecureRandom.getInstanceStrong())
    }
    .generateKey()
