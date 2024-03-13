package com.example.rickandmorty.service

import com.example.rickandmorty.models.RickAndMortyResponse
import com.example.rickandmorty.utils.HttpRoutes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class CharacterServiceImpl(
    private val client: HttpClient
) : ICharacterService {
    override suspend fun getAllCharacters(): RickAndMortyResponse {

        val result = client.get {
            url(HttpRoutes.GET_CHARACTERS)
        }

        return result.body()

    }
}

