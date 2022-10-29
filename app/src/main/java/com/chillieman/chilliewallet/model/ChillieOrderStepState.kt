package com.chillieman.chilliewallet.model

enum class ChillieOrderStepState {
    PENDING,
    IN_PROGRESS,
    WAITING_TXN,
    WAITING_FEE,
    COMPLETE,
    ERROR,
    CANCELLED
}
