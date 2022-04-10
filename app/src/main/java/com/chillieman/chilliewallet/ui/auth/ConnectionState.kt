package com.chillieman.chilliewallet.ui.auth

import androidx.annotation.StringRes
import com.chillieman.chilliewallet.R

enum class ConnectionState(@StringRes val stringRes: Int) {
    CONNECTING(R.string.connecting),
    CONNECTED(R.string.connected),
    DISCONNECTED(R.string.disconnected),
    ERROR(R.string.error_check_network)
}