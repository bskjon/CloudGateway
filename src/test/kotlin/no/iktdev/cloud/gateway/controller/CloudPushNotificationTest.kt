package no.iktdev.cloud.gateway.controller

import com.google.gson.Gson
import no.iktdev.cloud.gateway.classes.FcmSendData
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


//@RunWith(SpringRunner::class)
@ExtendWith(SpringExtension::class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(controllers = [CloudPushNotification::class])
class CloudPushNotificationTest(
    @Autowired var mockMvc: MockMvc,
    /*@Autowired var controller: CloudPushNotification,
    @Autowired var template: TestRestTemplate*/
) {

    @Test
    fun checkThatDeserializationIsCorrect() {
        val data = FcmSendData("", fcmSenderToken = "nana", fcmTargets = listOf(), payload = mapOf(Pair("data", "nana batmaaaaan")))
        val stringData = Gson().toJson(data)

        mockMvc.perform(post("/fcm/devices/send")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(stringData)
        ).andExpect(status().isOk)
    }

}