import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .edgesIgnoringSafeArea(.all) //for full screen ui without system status bar
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



