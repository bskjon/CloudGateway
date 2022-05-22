package no.iktdev.cloud.gateway.firebase

import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.MulticastMessage
import no.iktdev.cloud.gateway.Log
import no.iktdev.cloud.gateway.classes.FcmSendData
import no.iktdev.cloud.gateway.helpers.InstrumentationHelper
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.lang.instrument.Instrumentation

open class CloudMessage {

    /**
     * @return Count of devices targeted
     */
    fun send(data: FcmSendData): Int {
        if (!hasRequiredData(data))
            throw MissingRequiredFcmDataException("Required data for sending to Firebase Cloud Messaging was missing..")
        if (isExceedingPayloadLimit(data.payload))
            throw PayloadExceedsMaximumSize("Provided payload exceeds maximum size")

        val firebaseApp = Initializer().getAppInstance(data.appPackage)

        return if (data.fcmTargets.size == 1) sendToSingle(
            app = firebaseApp,
            target = data.fcmTargets.first(),
            payload = data.payload)
        else
            sendToMultiple(app = firebaseApp,
                targets = data.fcmTargets,
                payload = data.payload
            )
    }

    private fun sendToSingle(app: FirebaseApp, target: String, payload: Map<String, String>): Int {
        val fcmMessage = Message.builder()
            .putAllData(payload)
            .setToken(target)
            .build()
        val response = FirebaseMessaging.getInstance(app).send(fcmMessage)
        Log(response)
        return 1
    }

    private fun sendToMultiple(app: FirebaseApp, targets: List<String>, payload: Map<String, String>): Int {
        val fcmMessage = MulticastMessage.builder()
            .putAllData(payload)
            .addAllTokens(targets)
            .build()
        val response = FirebaseMessaging.getInstance(app).sendMulticast(fcmMessage)
        return response.successCount
    }

    fun hasRequiredData(fcmSendData: FcmSendData): Boolean {
        if (fcmSendData.fcmTargets.isEmpty()) return false
        if (fcmSendData.payload.isEmpty()) return false
        return true
    }


    fun isExceedingPayloadLimit(payload: Map<String, String>): Boolean {
        val limit = 4096 // 4KB
        val estimated = estimateSizeOfMap(payload)
        return estimated > limit
    }

    protected fun estimateSizeOfMap(payload: Map<String, String>): Long {
        //val size = InstrumentationHelper().getObjectSize(payload)
        var byteCount: Long = 0
        payload.forEach {
            byteCount += it.key.toByteArray().size + it.value.toByteArray().size
        }
        return byteCount
    }

    class MissingRequiredFcmDataException(override val message: String): RuntimeException()
    class PayloadExceedsMaximumSize(override val message: String): RuntimeException()
}