package io.ades.service.autofill

import android.content.Intent
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import java.lang.RuntimeException


class DataDetectionModuleImpl(private var attachedFragment: Fragment?)  : DataDetectionModule {

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    companion object{
        private const val TAG = "DataDetectionModuleImpl"
    }

    private val mGoogleSignInClient : GoogleSignInClient
    private var dataDetectionListener : DataDetectionModule.DataDetectionListener? = null

    init {
        if( attachedFragment != null) {
            mGoogleSignInClient = GoogleSignIn.getClient(attachedFragment?.context!!, gso);
        }else{
            throw RuntimeException("Activity could not be null")
        }
    }

    override fun detectEmail(resultCode : Int)  {
        val signInIntent = mGoogleSignInClient.signInIntent
        attachedFragment!!.startActivityForResult(signInIntent, resultCode)
    }

    override fun detectPhoneNumber(phoneRequestCode : Int) {
        attachedFragment?.let {
            val hintRequest = HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build()
            val apiClient = GoogleApiClient.Builder(it.context!!)
                .addApi(Auth.CREDENTIALS_API)
                .build()
            val intent = Auth.CredentialsApi.getHintPickerIntent(apiClient, hintRequest)
            it.startIntentSenderForResult(intent.intentSender,
                phoneRequestCode, null, 0, 0, 0, null)
        }
    }

    override fun handlePhoneDetectionResult(data : Intent){
        val credential = data.getParcelableExtra<Parcelable>(Credential.EXTRA_KEY) as Credential
        val number = credential.id
        dataDetectionListener?.onPhoneNumberDetected(number)
    }

    override fun handleDetectMailResult(data : Intent) {
        try {

            val completedTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = completedTask.getResult(ApiException::class.java)
            Log.i(TAG, "Google sign in email: " + account?.email)
            account?.email?.let {
                mGoogleSignInClient.signOut() // This is actually hack to get user email, so we need to log-out user
                dataDetectionListener?.onEmailDetected(it)
            }
        } catch (e: ApiException) {

        }
    }

    override fun setDetectionListener(listener: DataDetectionModule.DataDetectionListener) {
        this.dataDetectionListener = listener
    }

    override fun clear() {
        dataDetectionListener = null
        attachedFragment = null
    }
}