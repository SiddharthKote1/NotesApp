import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val CustomPrimary = Color(0xFF6200EE)
val CustomOnPrimary = Color(0xFFFFEB3B)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "NOTES",style = MaterialTheme.typography.titleLarge,modifier=Modifier.padding(start =24.dp)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Text(text = "HomeView",modifier = modifier.padding(paddingValues))
    }
}

@Composable
fun Design() {
    val customColorScheme = MaterialTheme.colorScheme.copy(
        primary = CustomPrimary,
        onPrimary = CustomOnPrimary
    )
    MaterialTheme(colorScheme = customColorScheme) { HomeView() }
}
@Composable
@Preview(showBackground = true)
fun HomeViewPreview() {
    Design()
}
