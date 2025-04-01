package com.example.corrutinas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.corrutinas.ui.theme.CorrutinasTheme
import com.example.corrutinas.view.model.ItemsViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CorrutinasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ItemsView(viewModel = ItemsViewModel())
                }
            }
        }
    }
}

@Composable
fun ItemsView(viewModel: ItemsViewModel) {
    val itemsList = viewModel.itemList
    val list by viewModel.list.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }
    Column {
        if (viewModel.isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(list) { item ->
                    Text(text = item.name)
                }
            }
        }
    }
}

@Composable
fun ButtonColor() {
    var color by remember { mutableStateOf(false) }
    Button(
        onClick = { color = !color },
        colors = ButtonDefaults.buttonColors(
            containerColor =
            if (color) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.secondary,
        )
    ) {
        Text(text = "Cambiar Color")
    }
}