package no.iktdev.cloud.gateway.classes

/**
 * @param appPackage Package of app calling
 * @param fcmSenderToken Device Sender Token
 * @param fmcTargets List of FCM device tokens
 * @param payload Json String encoded on sender device
 */
data class FcmSendData(val appPackage: String, val fcmSenderToken: String, val fmcTargets: List<String>, val payload: Map<String, String>)
