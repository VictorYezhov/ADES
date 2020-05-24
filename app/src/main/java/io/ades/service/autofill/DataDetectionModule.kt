package io.ades.service.autofill

import android.content.Intent
import io.ades.service.firebase_phone_verification.Module

interface DataDetectionModule : Module {

    interface DataDetectionListener{
        fun onEmailDetected(email : String)
        fun onPhoneNumberDetected(phone: String)
    }

    fun detectEmail(resultCode : Int)
    fun detectPhoneNumber(phoneRequestCode : Int)
    fun setDetectionListener(listener : DataDetectionListener)
    fun handlePhoneDetectionResult(data : Intent)
    fun handleDetectMailResult(data : Intent)
}