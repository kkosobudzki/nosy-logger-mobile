package dev.nosytools.logger

import android.security.keystore.KeyProperties
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

internal class Hkdf(
  private val algorithm: String = KeyProperties.KEY_ALGORITHM_HMAC_SHA256
) {

  internal fun extract(sharedSecret: SecretKey): SecretKey =
    Mac.getInstance(algorithm)
      .apply {
        init(sharedSecret)
      }
      .doFinal()
      .toSecretKey()

  private fun ByteArray.toSecretKey(): SecretKey =
    SecretKeySpec(this, algorithm)
}
