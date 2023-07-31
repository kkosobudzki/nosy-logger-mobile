package com.nosy.logger

import android.util.Log

internal fun String.log() = Log.d("NosyLogger", this)
