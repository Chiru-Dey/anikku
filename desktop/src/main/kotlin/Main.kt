import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension

/**
 * Main entry point for Anikku Desktop application.
 * 
 * This function initializes the Compose Desktop window and launches the application.
 */
fun main() = application {
    val windowState = rememberWindowState(
        width = 1280.dp,
        height = 800.dp
    )

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "Anikku - Anime Player & Library Manager"
    ) {
        // Set minimum window size
        window.minimumSize = Dimension(800, 600)

        // Launch the main application composable
        DesktopApp()
    }
}
