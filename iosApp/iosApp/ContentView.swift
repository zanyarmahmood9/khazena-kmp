import UIKit
import SwiftUI
import Shared

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Self.Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Self.Context) {}
}

struct ContentView: View {
    @State private var showsLaunchScreen = true

    var body: some View {
        ZStack {
            ComposeView()
                .ignoresSafeArea()
            if showsLaunchScreen {
                // Same as UILaunchScreen in Info.plist; fades out over Compose's identical first frame.
                ZStack {
                    Color("LaunchBackground")
                    Image("LaunchMark")
                }
                .ignoresSafeArea()
                .allowsHitTesting(false)
                .transition(.opacity)
            }
        }
        .task {
            try? await Task.sleep(for: .milliseconds(150))
            withAnimation(.easeOut(duration: 0.35)) { showsLaunchScreen = false }
        }
    }
}
