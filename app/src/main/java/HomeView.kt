import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notetakingapp.R

val CustomPrimary = Color(0xFF87CEEB)
val CustomOnPrimary = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "NOTES",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier=Modifier.padding(start =24.dp),
                    color=Color.Black) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    Row(modifier = Modifier.padding(start=50.dp),
                        ) {
                        IconButton(onClick = {
                            // Add your exit logic here
                        },
                            ) {//Iwas here
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null,
                                tint = Color.Black,

                            )
                        }
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {

            },
                containerColor = MaterialTheme.colorScheme.primary) {

                Text(text = "+", style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    )
            }
        }

    ) { paddingValues ->
        Box(modifier=Modifier.padding(paddingValues)){
            Image(
                painter = painterResource(id= R.drawable.notes_images),
                contentDescription = "Notes Image",
                modifier= Modifier
                    .padding(top = 150.dp, start = 80.dp)
                    .align(Alignment.Center)
                    .height(250.dp)
                    .width(250.dp),
            contentScale = ContentScale.Crop)
        }
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
