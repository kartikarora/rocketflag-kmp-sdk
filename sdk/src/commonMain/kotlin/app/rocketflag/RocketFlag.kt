@file:JvmName("RocketFlag")
@file:OptIn(kotlin.experimental.ExperimentalObjCName::class)

package app.rocketflag

import app.rocketflag.Constants.DEFAULT_BASE_URL
import app.rocketflag.Constants.DEFAULT_VERSION
import app.rocketflag.error.RocketFlagException
import app.rocketflag.model.Flag
import app.rocketflag.network.RocketFlagApiService
import kotlin.jvm.JvmName

class RocketFlag {

    private val apiService: RocketFlagApiService by lazy {
        RocketFlagApiService(DEFAULT_BASE_URL, DEFAULT_VERSION)
    }

    /**
     * Retrieves the status and details of a specific feature flag.
     *
     * @param flagId The unique identifier of the flag to retrieve.
     * @param cohort An optional cohort identifier to evaluate the flag against.
     * @param environment An optional environment identifier to evaluate the flag against.
     * @return A [Flag] object.
     * @throws [RocketFlagException] if the request fails.
     */
    suspend fun get(flagId: String, cohort: String? = null, environment: String? = null): Flag {
        return apiService.getFlag(flagId, cohort, environment).getOrThrow()
    }
}