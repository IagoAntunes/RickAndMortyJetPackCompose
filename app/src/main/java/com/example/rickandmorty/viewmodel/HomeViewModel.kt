package com.example.rickandmorty.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.service.ICharacterService
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var listCharacters = mutableStateListOf<Character>()
    var filterlistCharacters = mutableStateListOf<Character>()

    private val client = ICharacterService.create()
    suspend fun getAllCharacters(): List<Character> {
        return client.getAllCharacters().results
    }

    fun filterListCharacters(text: String) {
        filterlistCharacters.clear()
        if (text.isEmpty()) {
            filterlistCharacters.addAll(listCharacters.toMutableStateList())
        } else {
            filterlistCharacters.addAll(
                listCharacters.filter { it.name.contains(text, ignoreCase = true) }
                    .toMutableStateList())
            println(filterlistCharacters.size)

        }
    }

    fun getAllCharacters2() {
        viewModelScope.launch {
            var result = client.getAllCharacters().results
            listCharacters.addAll(result)
            filterlistCharacters.addAll(result)
        }

    }
}