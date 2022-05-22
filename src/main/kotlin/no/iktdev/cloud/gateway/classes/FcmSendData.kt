package no.iktdev.cloud.gateway.classes

/**
 * @param appPackage Package of app calling
 * @param fcmSenderToken Device Sender Token
 * @param fcmTargets List of FCM device tokens
 * @param payload Json String encoded on sender device
 */
data class FcmSendData(val appPackage: String, val fcmSenderToken: String, val fcmTargets: List<String>, val payload: Map<String, String>)
