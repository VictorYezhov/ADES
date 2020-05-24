package io.ades.data

data class Car(
    val id : Long,
    val carName : String,
    val averageSpeed : Int,
    val averageConsumption : Int,
    val timeTillMileage : Int,
    val averageMileage : Int
)