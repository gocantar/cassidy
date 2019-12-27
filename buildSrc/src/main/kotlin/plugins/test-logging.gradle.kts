import org.gradle.api.internal.tasks.testing.DecoratingTestDescriptor
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

/**
 * This plugin configure the log for unit testing execution
 */

tasks.withType<Test> {
    val mainEvents = listOf(
        TestLogEvent.FAILED,
        TestLogEvent.SKIPPED,
        TestLogEvent.STANDARD_OUT
    )

    testLogging {
        events = mainEvents.toSet()
        exceptionFormat = TestExceptionFormat.SHORT
        showExceptions = false
        showCauses = false
        showStackTraces = false
    }

    failFast = rootProject.property("failFast").toString().toBoolean()
    ignoreFailures = rootProject.property("ignoreFailures").toString().toBoolean()

    afterSuite(KotlinClosure2<DecoratingTestDescriptor, TestResult, Unit>({ descriptor, result ->
        takeIf { descriptor.isRoot }?.run {
            // TODO Add suite report
        }
    }))
}