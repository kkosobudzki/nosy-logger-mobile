package com.nosy.logger

import java.nio.ByteBuffer
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyAgreement
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

internal class Encryptor(
  private val myPrivateKey: PrivateKey,
  private val serverPublicKey: PublicKey
) {

  private val sharedKey: SecretKey by lazy {
    KeyAgreement.getInstance(KEY_ALGORITHM) // TODO not sure about this key algorithm - maybe there should be elliptic curve?
      .apply {
        init(myPrivateKey)

        doPhase(serverPublicKey, true)
      }
      .generateSecret(KEY_ALGORITHM)
  }

  fun encrypt(input: String, nonce: ByteArray = generateNonce()): String =
    Cipher.getInstance(CIPHER_ALGORITHM)
      .apply {
        init(Cipher.ENCRYPT_MODE, sharedKey, IvParameterSpec(nonce))
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
    const val KEY_ALGORITHM = "ChaCha20"
    const val NONCE_LENGTH = 16
  }
}
