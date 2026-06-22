package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.data.BandaDatabase
import com.example.data.BandaRepository
import com.example.ui.BandaAppContent
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.BandaViewModel
import com.example.viewmodel.BandaViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Initialize Room Local Database
        val database = BandaDatabase.getDatabase(applicationContext)

        // 2. Initialize Repository Pattern
        val repository = BandaRepository(database)

        // 3. Create ViewModel Factory
        val factory = BandaViewModelFactory(application, repository)

        // 4. Retrieve ViewModel Instance
        val viewModel = ViewModelProvider(this, factory)[BandaViewModel::class.java]

        // 5. Mount Compose UI Hierarchy
        setContent {
            MyApplicationTheme {
                BandaAppContent(viewModel = viewModel)
            }
        }
    }
}
