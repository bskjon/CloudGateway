package no.iktdev.cloud.gateway.firebase

import com.google.gson.Gson
import junit.framework.TestCase.*
import no.iktdev.cloud.gateway.classes.FcmSendData
import org.junit.jupiter.api.Test

class CloudMessageTest {


    @Test
    fun hasRequiredDataSuccessTest() {
        val invalidData = FcmSendData("","", listOf("ADeviceHere-WithNoFunctional-ID"), mapOf(Pair("data", "This is some data")))
        val res = CloudMessage().hasRequiredData(invalidData)
        assertTrue(res)
    }

    @Test
    fun hasRequiredDataFailureTest() {
        val emptyData = FcmSendData("","", listOf(), mapOf())
        val res = CloudMessage().hasRequiredData(emptyData)
        assertFalse(res)
    }

    @Test
    fun doesNotExceedPayloadLimit() {
        val payload: MutableMap<String, String> = mutableMapOf()
        payload["data"] = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sed vestibulum augue. Phasellus semper faucibus gravida. Phasellus porta volutpat facilisis. Sed orci nulla, mattis in efficitur sed, mollis at massa. Praesent ultricies scelerisque malesuada. Nulla facilisi. Duis non orci lacus. Sed vel nisl augue. Proin feugiat ex justo, gravida luctus quam gravida sed. Curabitur sed risus eros. Nam et mi feugiat, viverra odio sed, vestibulum massa. Duis ac efficitur eros."
        val exeeds = CloudMessage().isExceedingPayloadLimit(payload)
        assertFalse(exeeds)
    }

    @Test
    fun doesExceedPayloadLimit() {
        val resource = this.javaClass.classLoader.getResource("LorumIpsum.txt")?.readText() ?: ""
        val payload: MutableMap<String, String> = mutableMapOf()
        payload["data"] = resource
        val exeeds = CloudMessage().isExceedingPayloadLimit(payload)
        assertTrue(exeeds)
    }

    @Test
    fun testetst() {
        val map: Map<String, String> = mapOf(Pair("Key", "TVal"), Pair("Key", "TVal2"))
        val json = Gson().toJson(map)
        System.out.println(json)

    }


}