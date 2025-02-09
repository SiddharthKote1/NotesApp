package com.example.notetakingapp.ui.screens

import Note
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.notetakingapp.R

val CustomPrimary = Color(0xFF87CEEB)
val CustomOnPrimary = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController,
    notes: List<Note>,
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "NOTES",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(start = 24.dp),
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    Row(modifier = Modifier.padding(start = 50.dp)) {
                        IconButton(onClick = {
                            Toast.makeText(context, "Work in process", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("NotingScreen")
            }, containerColor = MaterialTheme.colorScheme.primary) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (notes.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.notes_images),
                    contentDescription = "Notes Image",
                    modifier = Modifier
                        .padding(top = 150.dp, start = 80.dp)
                        .align(Alignment.Center)
                        .height(250.dp)
                        .width(250.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = "No notes available",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                        .padding(top = 200.dp)
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                items(notes) { note ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF0F0F0))
                            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                            .padding(16.dp)
                            .clickable {
                                //New Added
                                navController.navigate("NotingScreen/${note.id}")
                            }
                    ) {
                        // Content inside the box (e.g., title and actions)
                        Text(
                            text = note.title,  // Example title text
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun Design() {
        val customColorScheme = MaterialTheme.colorScheme.copy(
            primary = CustomPrimary,
            onPrimary = CustomOnPrimary
        )
        MaterialTheme(colorScheme = customColorScheme) {
            HomeView(
                navController = rememberNavController(),
                notes = listOf()
            )  // Empty list for preview
        }
    }
}


