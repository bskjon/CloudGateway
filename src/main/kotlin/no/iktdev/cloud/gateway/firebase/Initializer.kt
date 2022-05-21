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
        val app: MutableMap<String, FirebaseApp> = mutableMapOf()
    }

    init {
        if (!hasMultipleCredentials()) {
            validateGoogleCredentials()
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build()
            app["default"] = FirebaseApp.initializeApp(options)
            Log("FirebaseApp Instance: Loaded single-instance firebase app for all calls")
        } else {
            val appFile = File(System.getenv("FIREBASE_CREDENTIALS"))
            val jsonMap = FileReader().getText(appFile) ?: throw MultiTenantGoogleApplicationCredentialsReadException("Failed to read credentials file")
            readAndSetFirebaseCredentials(jsonMap)
        }
        Log("FirebaseApp Instance: Completed Initialization")
    }

    private fun readAndSetFirebaseCredentials(jsonMap: String) {
        val transformedMap: Map<String, String> = Gson().fromJson(jsonMap, Map::class.java) as Map<String, String>
        transformedMap.forEach {
            val cred = GoogleCredentials.fromStream(FileInputStream(it.value))
                .createScoped()
            val options: FirebaseOptions = FirebaseOptions.builder().setCredentials(cred).build()
            app[it.key] = FirebaseApp.initializeApp(options)
            Log("FirebaseApp Instance: Loaded up instance for: ${it.key}")
        }
    }


    private fun validateGoogleCredentials() {
        if (System.getenv("GOOGLE_APPLICATION_CREDENTIALS") == null)
            throw MissingGoogleApplicationCredentialsException("Environment variable for Google Application Credentials has not been set!")
        val path = System.getenv("GOOGLE_APPLICATION_CREDENTIALS")
        val credFile = File(path)
        if (!credFile.exists())
            throw MissingGoogleApplicationCredentialsException("Environment variable contains invalid Path for Google Application Credentials")
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

    class MissingGoogleApplicationCredentialsException(override val message: String): RuntimeException()
    class MultiTenantGoogleApplicationCredentialsReadException(override val message: String): RuntimeException()
}