package no.iktdev.cloud.gateway.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
class Heartbeat {
    private fun heartbeat(): Heartbeat
    {
        return Heartbeat(true, System.currentTimeMillis() / 1000L)
    }

    @GetMapping("/heartbeat")
    fun heartbeatPath(): Heartbeat
    {
        return heartbeat()
    }


    @GetMapping("/swagger")
    fun swaggerRedirect(response: HttpServletResponse) {
        response.setHeader("Location", "/swagger-ui.html")
        response.status = 302
    }

    data class Heartbeat(val status: Boolean, val time: Long)
}