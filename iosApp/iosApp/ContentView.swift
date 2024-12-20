//
//  ContentView.swift
//  iosApp
//
//  Created by Zouhir RAJDAOUI on 11/8/2024.
//

import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .edgesIgnoringSafeArea(.all) // added the code here
            .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}

