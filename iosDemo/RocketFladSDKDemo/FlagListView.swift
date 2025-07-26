import SwiftUI

struct FlagListView: View {
    let flags: [FlagListItem]
    
    private var groupedFlags: [(id: String, name: String, items: [FlagListItem])] {
        var groups: [(id: String, name: String, items: [FlagListItem])] = []
        var seenIds: [String] = []
        
        for flag in flags {
            if !seenIds.contains(flag.flagId) {
                seenIds.append(flag.flagId)
                let items = flags.filter { $0.flagId == flag.flagId }
                groups.append((id: flag.flagId, name: flag.name, items: items))
            }
        }
        return groups
    }

    var body: some View {
        List {
            ForEach(groupedFlags, id: \.id) { group in
                Section(header: Text(group.name)
                    .font(.headline)
                    .foregroundColor(.primary)
                    .padding(.vertical, 8)
                ) {
                    ForEach(group.items) { item in
                        FlagItemView(item: item)
                            .listRowInsets(EdgeInsets(top: 4, leading: 16, bottom: 4, trailing: 16))
                            .listRowSeparator(.hidden)
                    }
                }
            }
        }
        .listStyle(.plain)
    }
}
