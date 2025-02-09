import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NotingScreen(
    modifier: Modifier = Modifier, navController: NavController,
    viewModel: WishViewModel,
    //New Added
    note:Note? = null
) {
    var input1 by remember { mutableStateOf(note?.title ?: "") }
    var input2 by remember { mutableStateOf(note?.content ?: "") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                //NEW ADDED
                title = { Text(text = if(note == null) "ADD NOTES" else "EDIT NOTES") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (input1.isNotBlank() && input2.isNotBlank()) {
                            val updatedNote = Note(
                                id=note?.id?:0,
                                title = input1,
                                content = input2
                            )
                            if(note==null){
                                viewModel.addNote(updatedNote)
                            }
                            else{
                                viewModel.updateNote(updatedNote)
                            }
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Title and Description cannot be empty.", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Save Note")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            // Title TextField
            TextField(
                value = input1,
                onValueChange = { input1 = it },
                label = { Text("Title") },
                modifier = Modifier.padding(0.dp).fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.titleLarge,
                singleLine = true
            )

            // Description TextField
            TextField(
                value = input2,
                onValueChange = { input2 = it },
                label = { Text("Description") },
                modifier = Modifier.padding(0.dp).fillMaxWidth().fillMaxHeight(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

