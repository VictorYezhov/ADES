package io.ades.service.firebase_phone_verification





interface Module {
    fun clear()
}


interface VerificationModule : Module {

    interface PhoneVerificationCallbacks{
        fun onCodeSent() // This callback will be triggered when we have confirmation about sending phone
        fun onVerified(code: String?) // This callback will be triggered when phone number will be successfully verified
        fun onVerificationFailed() // This callback will be triggered when error during verification occurred. More details
        fun onInvalidCodeInputted() // This callback will be triggered when user enter wrong code
    }

    fun setVerificationCallBacks(verificationCallBacks: PhoneVerificationCallbacks)
    fun verifyNumber(number : String)
    fun resendCode(number : String)
    fun checkCodeEntered(code : String)

}