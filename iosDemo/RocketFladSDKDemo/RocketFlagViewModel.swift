import Foundation
import RocketFlagSdk
import Combine

@MainActor
class RocketFlagViewModel: ObservableObject {
    @Published var flags: [FlagListItem] = []

    private struct FlagRequest {
        let name: String
        let id: String
        let cohort: String?
        let environment: String?
    }

    private let flagRequests = [
        FlagRequest(name: "Flag Standard", id: "sIO6dwFmrWkNfWDVSim6", cohort: nil, environment: nil),
        FlagRequest(name: "Flag Cohort", id: "xSyv5Fk0xBC9hNO3uBCu", cohort: "cohort_1", environment: nil),
        FlagRequest(name: "Flag Cohort", id: "xSyv5Fk0xBC9hNO3uBCu", cohort: "cohort_2", environment: nil),
        FlagRequest(name: "Flag Cohort", id: "xSyv5Fk0xBC9hNO3uBCu", cohort: "cohort_3", environment: nil),
        FlagRequest(name: "Flag Env 1", id: "Z5GHBxH6ADLjFcyPTFul", cohort: nil, environment: "env_1"),
        FlagRequest(name: "Flag Env 2", id: "o4f5HcYhY5Op9ROPaC4U", cohort: nil, environment: "env_2"),
        FlagRequest(name: "Flag Env 1 2", id: "NPgzghbiQiD63ELBdblf", cohort: nil, environment: "env_1"),
        FlagRequest(name: "Flag Env 1 2", id: "NPgzghbiQiD63ELBdblf", cohort: nil, environment: "env_2"),
        FlagRequest(name: "Flag Cohort Env 1", id: "1pFoSiRaY945TUnXG0Dk", cohort: "cohort_1", environment: "env_1"),
        FlagRequest(name: "Flag Cohort Env 2", id: "1pFoSiRaY945TUnXG0Dk", cohort: "cohort_2", environment: "env_1"),
        FlagRequest(name: "Flag Cohort Env 1", id: "1pFoSiRaY945TUnXG0Dk", cohort: "cohort_3", environment: "env_1"),
        FlagRequest(name: "Flag Cohort Env 1", id: "1pFoSiRaY945TUnXG0Dk", cohort: "cohort_1", environment: "env_2"),
        FlagRequest(name: "Flag Cohort Env 1", id: "1pFoSiRaY945TUnXG0Dk", cohort: "cohort_2", environment: "env_2"),
        FlagRequest(name: "Flag Cohort Env 2", id: "1pFoSiRaY945TUnXG0Dk", cohort: "cohort_3", environment: "env_2")
    ]

    init() {
        loadFlags()
    }

    func loadFlags() {
        self.flags = flagRequests.map { request in
            FlagListItem(flagId: request.id, name: request.name, cohort: request.cohort, environment: request.environment, flag: nil, error: nil, loading: true)
        }

        Task {
            var updatedFlags: [FlagListItem] = []
            for request in flagRequests {
                do {
                    print("[DEBUG_LOG] Fetching flag: \(request.name), id: \(request.id)")
                    let flag = try await RocketFlag().get(flagId: request.id, cohort: request.cohort, environment: request.environment)
                    print("[DEBUG_LOG] Result for \(request.name): \(String(describing: flag))")

                    let item = FlagListItem(flagId: request.id, name: request.name, cohort: request.cohort, environment: request.environment, flag: flag, error: nil, loading: false)
                    updatedFlags.append(item)
                } catch {
                    print("[DEBUG_LOG] Error fetching \(request.name): \(error)")
                    var errorMessage = error.localizedDescription

                    if let rocketFlagError = error as? RocketFlagException {
                        if let httpError = rocketFlagError.error as? RocketFlagErrorHttpError {
                            errorMessage = "HTTP \(httpError.code): \(httpError.message ?? rocketFlagError.errorMessage ?? "No message")"
                        } else if let networkError = rocketFlagError.error as? RocketFlagErrorNetworkError {
                            errorMessage = "Network Error: \(networkError.originalException.message ?? rocketFlagError.errorMessage ?? "Unknown network error")"
                        } else if let serializationError = rocketFlagError.error as? RocketFlagErrorSerializationError {
                            errorMessage = "Serialization Error: \(serializationError.originalException.message ?? rocketFlagError.errorMessage ?? "Unknown serialization error")"
                        } else if let unknownError = rocketFlagError.error as? RocketFlagErrorUnknownError {
                            errorMessage = "Unknown Error: \(unknownError.originalException.message ?? rocketFlagError.errorMessage ?? "Unknown error")"
                        }
                    }

                    let item = FlagListItem(flagId: request.id, name: request.name, cohort: request.cohort, environment: request.environment, flag: nil, error: errorMessage, loading: false)
                    updatedFlags.append(item)
                }
            }
            self.flags = updatedFlags
        }
    }
}
