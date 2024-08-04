package khan.z.dermagazeai.application

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class DermagazeAIApp: Application() {
    override fun onCreate(){
        super.onCreate()
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)

            Log.i("TestApp1", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("TestApp1", "Could not initialize Amplify", error)

        }
        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(this)
    }


}