package com.nosy.logger

import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PublicKey
import java.security.SecureRandom
import java.security.spec.X509EncodedKeySpec
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

internal fun String.mapToPublicKey(): PublicKey =
  decode()
    .let(::X509EncodedKeySpec)
    .let { spec ->
      "encoded: $spec".log()

      KeyFactory.getInstance("X25519", "BC").generatePublic(spec)
    }

internal fun generateSecretKey(): SecretKey =
  KeyGenerator.getInstance("ChaCha20")
    .apply {
      init(256, SecureRandom.getInstanceStrong())
    }
    .generateKey()

internal fun generateKeyPair(): KeyPair =
  KeyPairGenerator.getInstance("X25519", "BC")
    .generateKeyPair()

internal fun PublicKey.mapToString(): String =
  encoded.encode()
