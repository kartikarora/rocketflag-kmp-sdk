package app.rocketflag.error

import io.ktor.client.statement.HttpResponse
import kotlinx.io.IOException

/**
 * A sealed interface representing all possible errors that can occur when interacting with the RocketFlag API.
 * This provides a clear, exhaustive, and type-safe way to handle errors in consuming applications.
 */
sealed interface RocketFlagError {
    /**
     * Represents a network-related error (e.g., no internet connection, timeout).
     */
    data class NetworkError(val originalException: IOException) : RocketFlagError

    /**
     * Represents an HTTP error response from the server (e.g., 400 Bad Request, 401 Unauthorized, 404 Not Found, 500 Internal Server Error).
     * @param code The HTTP status code.
     * @param message A descriptive error message, potentially from the API response body.
     * @param originalResponse The raw HttpResponse, useful for debugging if needed.
     */
    data class HttpError(
        val code: Int,
        val message: String? = null,
        val originalResponse: HttpResponse? = null
    ) : RocketFlagError

    /**
     * Represents an error during data serialization or deserialization (e.g., malformed JSON response).
     */
    data class SerializationError(val originalException: Throwable) : RocketFlagError

    /**
     * Represents an unknown or unexpected error.
     */
    data class UnknownError(val originalException: Throwable) : RocketFlagError
    /**
     * Represents a state where the SDK has not been properly configured before use.
     */
    object NotConfigured : RocketFlagError
}