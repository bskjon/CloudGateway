package no.iktdev.cloud.gateway.controller

import no.iktdev.cloud.gateway.Log
import no.iktdev.cloud.gateway.classes.FcmSendData
import no.iktdev.cloud.gateway.firebase.CloudMessage
import no.iktdev.cloud.gateway.log
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/fcm"])
class CloudPushNotification {

    @PostMapping(path = ["/devices/send"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun sendDevicesFcm(@RequestBody data: FcmSendData) {
        if (data.fcmTargets.isNotEmpty()) {
            val result = CloudMessage().send(data)
            if (result == data.fcmTargets.size) {
                Log("All $result devices were successfully targeted by Firebase Cloud Messaging")
            } else {
                Log("$result devices failed to get targeted by Firebase Cloud Messaging")
            }
        }
    }
}