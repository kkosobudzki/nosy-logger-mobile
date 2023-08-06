package com.nosy.logger

import android.util.Base64

internal fun ByteArray.encode(): String =
  String(Base64.encode(this, Base64.DEFAULT))

internal fun String.decode(): ByteArray =
  Base64.decode(this, Base64.DEFAULT)
