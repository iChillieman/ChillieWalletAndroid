package com.chillieman.chilliewallet.model

enum class ChillieOrderStepState {
    PENDING,
    IN_PROGRESS,
    WAITING_TXN,
    WAITING_FEE,
    COMPLETE,
    ERROR,
    CANCELLED,
    CANCELLED_TO_AVOID_FEE
}
