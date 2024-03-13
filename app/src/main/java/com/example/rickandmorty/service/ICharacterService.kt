package com.example.rickandmorty.service

import com.example.rickandmorty.models.RickAndMortyResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json

interface ICharacterService {
    suspend fun getAllCharacters(): RickAndMortyResponse


    companion object {
        fun create(): ICharacterService {
            return CharacterServiceImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(ContentNegotiation) {
                        json()
                    }


                }
            )
        }
    }

}