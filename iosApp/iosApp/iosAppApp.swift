//
//  iosAppApp.swift
//  iosApp
//
//  Created by Zouhir RAJDAOUI on 11/8/2024.
//

import SwiftUI
import shared

@main
struct iosAppApp: App {
    init() {
        KoinHelperKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
