package no.iktdev.cloud.gateway.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.gson.Gson
import no.iktdev.cloud.gateway.Log
import no.iktdev.cloud.gateway.helpers.FileReader
import org.jetbrains.annotations.NotNull
import java.io.File
import java.io.FileInputStream

// Resource: https://firebase.google.com/docs/admin/setup
// https://firebase.google.com/docs/cloud-messaging/auth-server#java
class Initializer {
    companion object {
        /**
         * app<Package Name, FirebaseApp>
         */
        val app: Map<String, FirebaseApp> =
            if (Initializer().hasMultipleCredentials())
                MultiTenantConfiguration().getMultiTenant()
            else
                SingleTenantConfiguration().getSingleTenant()
    }


    /**
     * Checks if there are multiple credentials
     */
    private fun hasMultipleCredentials(): Boolean {
        if (System.getenv("FIREBASE_CREDENTIALS") == null)
            return false
        val path = System.getenv("FIREBASE_CREDENTIALS")
        val appFile = File(path)
        return appFile.exists()
    }

    @NotNull
    fun getAppInstance(appPackage: String): FirebaseApp {
        if (app.isEmpty())
            throw MissingGoogleApplicationCredentialsException("No application was found...")
        return if (app.containsKey(appPackage)) app[appPackage]!! else app[app.keys.first()]!!
    }



    private class SingleTenantConfiguration() {
        fun getSingleTenant(): Map<String, FirebaseApp> {
            validateGoogleCredentials()
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build()
            Log("FirebaseApp Instance: Loaded single-instance firebase app for all calls")
            return mapOf(Pair("default", FirebaseApp.initializeApp(options)))
        }

        private fun validateGoogleCredentials() {
            if (System.getenv("GOOGLE_APPLICATION_CREDENTIALS") == null)
                throw MissingGoogleApplicationCredentialsException("Environment variable for Google Application Credentials has not been set!")
            val path = System.getenv("GOOGLE_APPLICATION_CREDENTIALS")
            val credFile = File(path)
            if (!credFile.exists())
                throw MissingGoogleApplicationCredentialsException("Environment variable contains invalid Path for Google Application Credentials")
        }
    }

    private class MultiTenantConfiguration() {
        private val credentialsMap = File(System.getenv("FIREBASE_CREDENTIALS"))
        @Suppress("UNCHECKED_CAST")
        fun getMultiTenant(): Map<String, FirebaseApp> {
            val jsonMap = FileReader().getText(credentialsMap) ?: throw MultiTenantGoogleApplicationCredentialsReadException("Failed to read credentials file")
            val returnableMap: MutableMap<String, FirebaseApp> = mutableMapOf()

            (Gson().fromJson(jsonMap, Map::class.java) as Map<String, String>).forEach {
                returnableMap[it.key] = buildFirebaseAppInstance(it.value)
            }
            val packages = returnableMap.keys.toList().joinToString(separator = ",")

            Log("FirebaseApp Instance: Loaded multiple firebase app instances ($packages)")
            return returnableMap
        }

        private fun buildFirebaseAppInstance(fullPath: String): FirebaseApp {
            val cred = GoogleCredentials.fromStream(FileInputStream(fullPath)).createScoped()
            val options: FirebaseOptions = FirebaseOptions.builder().setCredentials(cred).build()
            return FirebaseApp.initializeApp(options)
        }
    }

    class MissingGoogleApplicationCredentialsException(override val message: String): RuntimeException()
    class MultiTenantGoogleApplicationCredentialsReadException(override val message: String): RuntimeException()
}