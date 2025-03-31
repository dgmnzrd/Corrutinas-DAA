package com.example.corrutinas.view.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corrutinas.models.ItemsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemsViewModel: ViewModel() {
    private val _list: MutableStateFlow<List<ItemsModel>> = MutableStateFlow(emptyList())
    val list = _list
    var itemList = mutableStateListOf(ItemsModel())
    var isLoading by mutableStateOf(false)

    fun fetchData() {
        viewModelScope.launch {
            try {
                isLoading = true
                llamarAPI()
            } catch (e: Exception) {
                println("Error: $(e.message)")
            } finally {
                isLoading = false
            }
        }
    }

    private suspend fun llamarAPI() {
        val result = withContext(Dispatchers.IO) {
            delay(5000)
            (1..50).map { id -> ItemsModel(id, "Item $id") }
        }
        itemList.addAll(result)
        list.value = result
    }
}