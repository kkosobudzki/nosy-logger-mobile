package com.nosy.logger

import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.KeyAgreement
import javax.crypto.SecretKey

private const val KEY_ALGORITHM = "X25519"
private const val KEY_PROVIDER = "BC"

private fun String.toPublicKey(): PublicKey =
  decode()
    .let(::X509EncodedKeySpec)
    .let { spec ->
      "encoded: $spec".log()

      KeyFactory.getInstance(KEY_ALGORITHM, KEY_PROVIDER).generatePublic(spec)
    }

internal fun generateKeyPair(): KeyPair =
  KeyPairGenerator.getInstance(KEY_ALGORITHM, KEY_PROVIDER)
    .generateKeyPair()

internal fun PublicKey.mapToString(): String =
  encoded.encode()

internal fun PrivateKey.sharedSecret(otherPublicKey: String): SecretKey =
  KeyAgreement.getInstance(KEY_ALGORITHM, KEY_PROVIDER)
    .apply {
      init(this@sharedSecret)

      doPhase(otherPublicKey.toPublicKey(), true)
    }
    .generateSecret(KEY_ALGORITHM)
