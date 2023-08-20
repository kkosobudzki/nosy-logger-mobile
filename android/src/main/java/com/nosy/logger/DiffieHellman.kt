package com.nosy.logger

import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.KeyAgreement
import javax.crypto.SecretKey


internal class DiffieHellman(
  private val algorithm: String = "X25519",
  private val provider: String = "BC"
) {

  private val keyPair: KeyPair by lazy {
    KeyPairGenerator.getInstance(algorithm, provider)
      .generateKeyPair()
  }

  internal val publicKey: String by lazy {
    keyPair.public
      .encoded
      .encode()
  }

  internal fun sharedSecret(otherPublicKey: String): SecretKey =
    KeyAgreement.getInstance(algorithm, provider)
      .apply {
        init(keyPair.private)

        doPhase(otherPublicKey.toPublicKey(), true)
      }
      .generateSecret(algorithm)

  private fun String.toPublicKey(): PublicKey =
    decode()
      .let(::X509EncodedKeySpec)
      .let(KeyFactory.getInstance(algorithm, provider)::generatePublic)
}
