import Foundation
import RocketFlagSdk

struct FlagListItem: Identifiable, Equatable {
    let id: UUID = UUID()
    let flagId: String
    let name: String
    let cohort: String?
    let environment: String?
    let flag: Flag?
    let error: String?
    let loading: Bool

    static func == (lhs: FlagListItem, rhs: FlagListItem) -> Bool {
        return lhs.flagId == rhs.flagId &&
            lhs.name == rhs.name &&
            lhs.cohort == rhs.cohort &&
            lhs.environment == rhs.environment &&
            lhs.flag == rhs.flag &&
            lhs.error == rhs.error &&
            lhs.loading == rhs.loading
    }
}
