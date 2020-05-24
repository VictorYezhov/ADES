package io.ades.service.firebase_phone_verification

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import android.util.Log
import kotlin.concurrent.timer

class VerificationModuleImpl(private var bindedActivity: Activity?) : VerificationModule {


    companion object{
        private const val TAG = "VerificationModuleImpl"
        private const val TIME_OUT_IN_SEC = 120L
    }

    private var storedVerificationId : String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var verificationCallBacks : VerificationModule.PhoneVerificationCallbacks? = null

    init {
        if(bindedActivity == null){
            throw RuntimeException("Provided activity could not be null")
        }
    }

    override fun setVerificationCallBacks(verificationCallBacks: VerificationModule.PhoneVerificationCallbacks){
        this.verificationCallBacks = verificationCallBacks
    }



    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.i(TAG, "onVerificationCompleted:$credential")
            Log.i(TAG, "onVerificationCompleted code :$credential")

            checkCode(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.i(TAG, "onVerificationFailed", e)
            verificationCallBacks?.onVerificationFailed()

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request

            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                //TODO: Handle it additionally
            }
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.i(TAG, "onCodeSent:$verificationId")
            verificationCallBacks?.onCodeSent()
            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            resendToken = token

        }
    }



    private fun checkCode(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(bindedActivity!!) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    verificationCallBacks?.onVerified(credential.smsCode)
                    val user = task.result?.user
                    FirebaseAuth.getInstance().signOut() // TODO : delete, only for testing
                } else {
                    Log.i(TAG, "signInWithCredential:failure", task.exception)
                    verificationCallBacks?.onInvalidCodeInputted()
                }
            }
    }



    override fun verifyNumber(number: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            number, // Phone number to verify
            TIME_OUT_IN_SEC, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            bindedActivity!!, // Activity (for callback binding)
            callbacks) // OnVerificationStateChangedCallbacks
    }


    override fun checkCodeEntered(code: String) {
        if(storedVerificationId != null){
            val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
            checkCode(credential)
        }else{
            Log.e(TAG, "Hey, Smth went wrong. You requested resending before  onCodeSent was called")
        }
    }

    override fun resendCode(number: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            number, // Phone number to verify
            TIME_OUT_IN_SEC, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            bindedActivity!!, // Activity (for callback binding)
            callbacks,
            resendToken) // OnVerificationStateChangedCallbacks
    }

    override fun clear() {
        bindedActivity = null
        verificationCallBacks = null
    }


    fun Starttimer(){
        timer("timer", true, System.currentTimeMillis(),  period = 1000) {

        }
    }
}