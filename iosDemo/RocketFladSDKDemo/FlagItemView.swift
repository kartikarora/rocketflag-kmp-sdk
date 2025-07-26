import SwiftUI
import RocketFlagSdk

struct FlagItemView: View {
    let item: FlagListItem

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            if let cohort = item.cohort {
                Text("Cohort: \(cohort)")
            }
            if let environment = item.environment {
                Text("Environment: \(environment)")
            }

            if item.loading {
                ProgressView()
                    .padding(.top, 8)
            } else {
                let status: String = {
                    if let flag = item.flag {
                        return flag.enabled ? "ENABLED" : "DISABLED"
                    } else {
                        return item.error ?? "Unknown error"
                    }
                }()
                Text(status)
            }
        }
        .padding(16)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(
            RoundedRectangle(cornerRadius: 12)
                .stroke(Color.secondary.opacity(0.5), lineWidth: 1)
        )
    }
}

#Preview {
    FlagItemView(item: FlagListItem(
        flagId: "PpH5FifOCCZdbhDgGdHO",
        name: "Flag Standard",
        cohort: nil,
        environment: nil,
        flag: Flag(name: "flag_standard", enabled: true, id: "PpH5FifOCCZdbhDgGdHO"),
        error: nil,
        loading: false
    ))
    .padding()
}
