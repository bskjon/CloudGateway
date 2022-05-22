package no.iktdev.cloud.gateway

import kotlinx.coroutines.*
import no.iktdev.cloud.gateway.firebase.Initializer
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import sun.reflect.Reflection
import java.io.File
import javax.annotation.PreDestroy


@SpringBootApplication
class Application

private var context: ApplicationContext? = null
fun getContext(): ApplicationContext? {
	return context
}


fun main(args: Array<String>) {
	/*val application = SpringApplication(ProcessorApplication::class.java)
	val props = Properties()
	props.setProperty("spring.main.banner-mode", "log");
	props.setProperty("logging.file", BaseConfiguration.logLocation.absolutePath);
	props.setProperty("logging.pattern.console", "");
	application.setDefaultProperties(props);

	context = application.run(*args)*/

	context = runApplication<Application>(*args)
	Initializer()
}


@PreDestroy
fun onDestroying() {
}

inline fun <reified T> log(message: String) {
	LoggerFactory.getLogger(T::class.java.simpleName).info(message)
}

fun Log(message: String) {
	val caller: String = Thread.currentThread().stackTrace[2].className ?: Thread.currentThread().stackTrace[2].methodName
	LoggerFactory.getLogger(caller).info(message)
}

