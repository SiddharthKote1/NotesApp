package com.example.notetakingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.notetakingapp.ui.theme.NoteTakingAppTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition{true}
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            splashScreen.setKeepOnScreenCondition{false}
        }
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        FirebaseCrashlytics.getInstance().log("MainActivity Crashlytics")

        firebaseAnalytics = Firebase.analytics
        setContent {
            NoteTakingAppTheme {
                // Remember the NavController to pass it to NavGraph
                val navController = rememberNavController()
                NavGraph(navController = navController, firebaseAnalytics = firebaseAnalytics)
            }
        }
    }
}

