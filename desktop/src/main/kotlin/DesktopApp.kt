import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Main application composable for Anikku Desktop.
 * 
 * This is a placeholder UI for Session 1 to verify the desktop setup is working.
 * In future sessions, this will be replaced with the full navigation and screen structure.
 */
@Composable
fun DesktopApp() {
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // App title
                Text(
                    text = "Anikku Desktop",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Windows Port - Session 1 Foundation",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Status card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 64.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Setup Status",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        StatusItem("✅", "Compose Desktop initialized")
                        StatusItem("✅", "Material3 theme applied")
                        StatusItem("✅", "Desktop module created")
                        StatusItem("✅", "Version catalog configured")
                        StatusItem("⏳", "Platform abstractions (next)")
                        StatusItem("⏳", "Core module migration (next)")
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Platform info
                Text(
                    text = buildString {
                        append("Platform: ${System.getProperty("os.name")}\n")
                        append("Architecture: ${System.getProperty("os.arch")}\n")
                        append("Java: ${System.getProperty("java.version")}\n")
                        append("User: ${System.getProperty("user.home")}")
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun StatusItem(icon: String, text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icon,
            modifier = Modifier.width(32.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
