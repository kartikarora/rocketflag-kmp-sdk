package app.rocketflag.network

import app.rocketflag.error.RocketFlagError
import app.rocketflag.error.RocketFlagException
import app.rocketflag.model.Flag
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.ContentConvertException
import io.ktor.serialization.JsonConvertException
import io.ktor.serialization.kotlinx.json.json
import kotlinx.io.IOException
import kotlinx.serialization.json.Json

class RocketFlagApiService(
    private val baseUrl: String,
    private val apiVersion: String
) {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

    /**
     * Fetches a single flag by its key from the RocketFlag service.
     *
     * @param flagId The key (ID) of the flag to retrieve.
     * @param cohort Optional cohort to evaluate the flag against.
     * @param environment Optional environment to check the flag value for environment
     * @return A Result containing [Flag] on success, or [RocketFlagException] on failure.
     */
    suspend fun getFlag(flagId: String, cohort: String? = null, environment: String? = null): Result<Flag> {
        return runCatching {
            val response = httpClient.get("$baseUrl/$apiVersion/flags/$flagId") {
                environment?.let { parameter("env", it) }
                cohort?.let { parameter("cohort", it) }
            }

            when (response.status.value) {
                in 200..299 -> response.body<Flag>()
                in 400..499 -> {
                    val errorBody = try {
                        response.body<String>()
                    } catch (e: Exception) {
                        null
                    }
                    throw RocketFlagException(
                        RocketFlagError.HttpError(response.status.value, errorBody, response),
                        "HTTP Client Error: ${response.status.value}"
                    )
                }

                in 500..599 -> {
                    val errorBody = try {
                        response.body<String>()
                    } catch (e: Exception) {
                        null
                    }
                    throw RocketFlagException(
                        RocketFlagError.HttpError(response.status.value, errorBody, response),
                        "HTTP Server Error: ${response.status.value}"
                    )
                }

                else -> {
                    val errorBody = try {
                        response.body<String>()
                    } catch (e: Exception) {
                        null
                    }
                    throw RocketFlagException(
                        RocketFlagError.UnknownError(
                            IllegalStateException("Unexpected HTTP status code: ${response.status.value}")
                        ),
                        "Unexpected HTTP Status: ${response.status.value} - $errorBody"
                    )
                }
            }
        }.fold(
            onSuccess = { data -> Result.success(data) },
            onFailure = { throwable ->
                when (throwable) {
                    is RocketFlagException -> Result.failure(exception = throwable)
                    is IOException -> Result.failure(
                        exception = RocketFlagException(
                            error = RocketFlagError.NetworkError(originalException = throwable),
                            errorMessage = "Network Error",
                            errorCause = throwable
                        )
                    )
                    is ClientRequestException -> {
                        val errorBody = try {
                            throwable.response.body<String>()
                        } catch (e: Exception) {
                            null
                        }
                        Result.failure(
                            RocketFlagException(
                                error = RocketFlagError.HttpError(
                                    code = throwable.response.status.value,
                                    message = errorBody,
                                    originalResponse = throwable.response
                                ),
                                errorMessage = "HTTP Client Error: ${throwable.response.status.value}",
                                errorCause = throwable
                            )
                        )
                    }
                    is ServerResponseException -> {
                        val errorBody = try {
                            throwable.response.body<String>()
                        } catch (e: Exception) {
                            null
                        }
                        Result.failure(
                            exception = RocketFlagException(
                                error = RocketFlagError.HttpError(
                                    code = throwable.response.status.value,
                                    message = errorBody,
                                    originalResponse = throwable.response
                                ),
                                errorMessage = "HTTP Server Error: ${throwable.response.status.value}",
                                errorCause = throwable
                            )
                        )
                    }
                    is ContentConvertException, is JsonConvertException ->
                        Result.failure(
                            exception = RocketFlagException(
                                error = RocketFlagError.SerializationError(originalException = throwable),
                                errorMessage = "Serialization Error",
                                errorCause = throwable
                            )
                        )

                    else -> Result.failure(
                        RocketFlagException(
                            error = RocketFlagError.UnknownError(originalException = throwable),
                            errorMessage = "An unknown error occurred",
                            errorCause = throwable
                        )
                    )
                }
            }
        )
    }
}