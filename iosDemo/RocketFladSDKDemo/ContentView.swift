//
//  ContentView.swift
//  RocketFladSDKDemo
//
//  Created by Kartik Arora on 15/1/2026.
//

import SwiftUI

struct ContentView: View {
    @StateObject private var viewModel = RocketFlagViewModel()

    var body: some View {
        NavigationView {
            FlagListView(flags: viewModel.flags)
                .navigationTitle("RocketFlag SDK Demo")
        }
    }
}

#Preview {
    ContentView()
}
