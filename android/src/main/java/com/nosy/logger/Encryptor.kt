package com.nosy.logger

import java.nio.ByteBuffer
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

internal class Encryptor(private val sharedSecretKey: SecretKey) {

  fun encrypt(input: String, nonce: ByteArray = generateNonce()): String =
    Cipher.getInstance(CIPHER_ALGORITHM)
      .apply {
        init(Cipher.ENCRYPT_MODE, sharedSecretKey, IvParameterSpec(nonce))
      }
      .doFinal(input.toByteArray())
      .let { encrypted ->
        ByteBuffer.allocate(encrypted.size + nonce.size)
          .put(encrypted)
          .put(nonce)
          .array()
      }
      .encode()

  private fun generateNonce(): ByteArray =
      SecureRandom().generateSeed(NONCE_LENGTH)

  private companion object {
    const val CIPHER_ALGORITHM = "ChaCha20-Poly1305"
    const val NONCE_LENGTH = 12
  }
}
