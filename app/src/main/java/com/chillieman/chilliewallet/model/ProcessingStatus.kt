package com.chillieman.chilliewallet.model

enum class ProcessingStatus {
    NOT_STARTED,
    PROCESSING,
    SUCCESS,
    ERROR;

    companion object{
        fun toString(status: ProcessingStatus) =
            when(status) {
                SUCCESS -> "SUCCESS"
                ERROR -> "ERROR"
                NOT_STARTED -> "NOT STARTED"
                PROCESSING -> "PROCESSING"
            }
    }
}