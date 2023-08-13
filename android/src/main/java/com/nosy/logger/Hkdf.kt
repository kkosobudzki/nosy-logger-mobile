package com.nosy.logger

import android.security.keystore.KeyProperties
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

internal class Hkdf(
  private val salt: ByteArray,
  private val algorithm: String = KeyProperties.KEY_ALGORITHM_HMAC_SHA256
) {

  internal fun extract(sharedSecret: SecretKey): SecretKey =
    Mac.getInstance(algorithm)
      .apply {
        init(SecretKeySpec(salt, algorithm))
      }
      .doFinal(sharedSecret.encoded)
      .let { encoded ->
        SecretKeySpec(encoded, algorithm)
      }
}
