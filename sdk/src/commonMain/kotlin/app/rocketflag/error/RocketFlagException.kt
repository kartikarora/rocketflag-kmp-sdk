package app.rocketflag.error

/**
 * A custom exception class that wraps a [RocketFlagError] to be used with Kotlin's Result.failure().
 * This allows passing specific, domain-related error information while adhering to the Result API's
 * requirement for a Throwable.
 *
 * @param error The specific [RocketFlagError] that occurred.
 * @param errorMessage An optional detail message.
 * @param errorCause The original cause of the exception, if available.
 */
data class RocketFlagException(
    val error: RocketFlagError,
    val errorMessage: String? = null,
    val errorCause: Throwable? = null
) : Exception(errorMessage ?: error.toString(), errorCause)