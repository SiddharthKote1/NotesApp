package com.example.notetakingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.notetakingapp.ui.theme.NoteTakingAppTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

