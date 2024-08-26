package khan.z.dermagazeai.application

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class DermagazeAIApp: Application() {
    override fun onCreate(){
        super.onCreate()
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.configure(applicationContext)
            Log.i("DermagazeAIApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("DermagazeAIApp", "Could not initialize Amplify", error)

        }
        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(this)
    }

}