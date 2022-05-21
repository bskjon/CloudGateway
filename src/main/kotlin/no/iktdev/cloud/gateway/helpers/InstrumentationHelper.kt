package no.iktdev.cloud.gateway.helpers

import java.lang.instrument.Instrumentation

class InstrumentationHelper {
    @Volatile
    private var globalInstrumentation: Instrumentation? = null

    fun premain(agentArgs: String?, inst: Instrumentation?) {
        globalInstrumentation = inst
    }

    fun getObjectSize(`object`: Any?): Long {
        checkNotNull(globalInstrumentation) { "Agent not initialized." }
        return globalInstrumentation!!.getObjectSize(`object`)
    }
}