package io.ades.data

data class DataReading<T>(
    val data : T,
    val time : Long
)