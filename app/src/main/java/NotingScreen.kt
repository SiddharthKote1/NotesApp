import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.notetakingapp.R
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.util.UUID

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NotingScreen(
    modifier: Modifier = Modifier, navController: NavController,
    viewModel: WishViewModel,
    note:Note? = null,
    firebaseAnalytics: FirebaseAnalytics
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, "NotingScreen")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

    }
    val storage = Firebase.storage
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var uploadProgress by remember { mutableStateOf(false) }


    // Camera Launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val uri = Uri.parse(MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Captured Image", null))
            imageUri = uri
            uploadImageToFirebase(uri, context) { uploadProgress = it }
        }
    }

    // Gallery Launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            imageUri = it
            uploadImageToFirebase(it, context) { uploadProgress = it }
        }
    }

    var input1 by remember { mutableStateOf(note?.title ?: "") }
    var input2 by remember { mutableStateOf(note?.content ?: "") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if(note == null) "ADD NOTES" else "EDIT NOTE") },
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
        },
        floatingActionButton = {
            Row(modifier = Modifier.padding(16.dp)) {
                FloatingActionButton(
                    onClick = { cameraLauncher.launch() },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Camera")
                }
                Spacer(modifier = Modifier.width(16.dp))
                FloatingActionButton(
                    onClick = { galleryLauncher.launch("image/*") },
                    containerColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(imageVector = Icons.Default.Create, contentDescription = "Gallary")
                }
            }
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

fun uploadImageToFirebase(uri: Uri, context: android.content.Context, onProgress: (Boolean) -> Unit) {
    val storageRef = FirebaseStorage.getInstance().reference.child("uploads/${UUID.randomUUID()}.jpg")

    onProgress(true)
    storageRef.putFile(uri)
        .addOnSuccessListener {
            onProgress(false)
            Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            onProgress(false)
            Toast.makeText(context, "Upload failed!", Toast.LENGTH_SHORT).show()
        }
}
