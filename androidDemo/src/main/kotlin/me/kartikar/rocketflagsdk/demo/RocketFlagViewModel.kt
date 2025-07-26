package me.kartikar.rocketflagsdk.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rocketflag.RocketFlag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RocketFlagViewModel : ViewModel() {
    private val _flags = MutableStateFlow<List<FlagListItem>>(emptyList())
    val flags: StateFlow<List<FlagListItem>> = _flags

    private data class FlagRequest(val name: String, val id: String, val cohort: String?, val environment: String?)

    private val flagRequests = listOf(
        FlagRequest("Flag Standard", "sIO6dwFmrWkNfWDVSim6", null, null),
        FlagRequest("Flag Cohort", "xSyv5Fk0xBC9hNO3uBCu", "cohort_1", null),
        FlagRequest("Flag Cohort", "xSyv5Fk0xBC9hNO3uBCu", "cohort_2", null),
        FlagRequest("Flag Cohort", "xSyv5Fk0xBC9hNO3uBCu", "cohort_3", null),
        FlagRequest("Flag Env 1", "Z5GHBxH6ADLjFcyPTFul", null, "env_1"),
        FlagRequest("Flag Env 2", "o4f5HcYhY5Op9ROPaC4U", null, "env_2"),
        FlagRequest("Flag Env 1 2", "NPgzghbiQiD63ELBdblf", null, "env_1"),
        FlagRequest("Flag Env 1 2", "NPgzghbiQiD63ELBdblf", null, "env_2"),
        FlagRequest("Flag Cohort Env 1", "1pFoSiRaY945TUnXG0Dk", "cohort_1", "env_1"),
        FlagRequest("Flag Cohort Env 2", "1pFoSiRaY945TUnXG0Dk", "cohort_2", "env_1"),
        FlagRequest("Flag Cohort Env 1", "1pFoSiRaY945TUnXG0Dk", "cohort_3", "env_1"),
        FlagRequest("Flag Cohort Env 1", "1pFoSiRaY945TUnXG0Dk", "cohort_1", "env_2"),
        FlagRequest("Flag Cohort Env 1", "1pFoSiRaY945TUnXG0Dk", "cohort_2", "env_2"),
        FlagRequest("Flag Cohort Env 2", "1pFoSiRaY945TUnXG0Dk", "cohort_3", "env_2")
    )

    init {
        viewModelScope.launch {
            _flags.value = flagRequests.map { request ->
                FlagListItem(request.id, request.name, request.cohort, request.environment, null, null, true)
            }

            _flags.value = flagRequests.map { request ->
                try {
                    val flag = RocketFlag().get(request.id, request.cohort, request.environment)
                    FlagListItem(request.id, request.name, request.cohort, request.environment, flag, null, false)
                } catch (e: Exception) {
                    FlagListItem(request.id, request.name, request.cohort, request.environment, null, e.message, false)
                }
            }
        }
    }
}