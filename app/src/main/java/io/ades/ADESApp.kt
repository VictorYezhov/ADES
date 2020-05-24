package io.ades

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import io.ades.config.Modules
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ADESApp  : Application(){
    companion object {

        private const val TAG = "Application"

        @SuppressLint("StaticFieldLeak")
        private var  mActivity: Activity? = null

        fun getActivity(): Activity {
            return mActivity!!
        }

        private lateinit var INSTANCE: android.app.Application

        fun instance(): android.app.Application {
            return INSTANCE
        }
    }

    override fun onCreate(){
        super.onCreate()
        INSTANCE = this
        startKoin {
            // Android context
            androidContext(this@ADESApp)
            // modules
            modules(Modules.module)
        }

        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks{

            override fun onActivityPaused(activity: Activity?) {
                //   Log.v(TAG, "onActivityPaused")
                mActivity = null
            }

            override fun onActivityResumed(activity: Activity?) {
                //   Log.v(TAG, "onActivityResumed")
            }

            override fun onActivityStarted(activity: Activity?) {
                //   Log.v(TAG, "onActivityStarted")
            }

            override fun onActivityDestroyed(activity: Activity?) {
                //   Log.v(TAG, "onActivityDestroyed")
                mActivity = null
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
                //  Log.v(TAG, "onActivitySaveInstanceState")
            }

            override fun onActivityStopped(activity: Activity?) {
                // Log.v(TAG, "onActivityStopped")
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                mActivity = activity
                //  Log.v(TAG, "onActivityCreated")
            }
        })
    }
}
inline fun <reified T> inject() : Lazy<T> =  ADESApp.instance().getKoin().inject()

fun context(): Context {
    return ADESApp.instance()
}

fun activity(): Activity {
    return ADESApp.getActivity()
}