package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.models.Location
import com.example.rickandmorty.models.Origin
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                App()
            }
        }
    }
}


@Composable
fun App() {
    val viewModel = HomeViewModel()
    //OUTRA FORMA DE FAZER
    //    val result = produceState<List<Character>>(initialValue = emptyList(), producer = {
    //        value = viewModel.getAllCharacters()
    //    })
    viewModel.getAllCharacters2()

    val list = remember {
        mutableStateOf(viewModel.filterlistCharacters)
    }

    var text by remember {
        mutableStateOf("")
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(horizontal = 8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                enabled = list.value.isNotEmpty(),
                onValueChange = {
                    text = it
                    viewModel.filterListCharacters(text)
                },
                label = {
                    Text("Pesquise")
                },
                shape = RoundedCornerShape(16.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                }

            )

            Divider(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            if (list.value.isNotEmpty()) {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                ) {
                    items(list.value) {
                        CardCharacter(it)
                    }
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }


        }

    }
}

@Preview
@Composable
fun CardCharacter(
    character: Character = Character(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        origin = Origin(
            name = "Earth (C-137)", url = "https://rickandmortyapi.com/api/location/1"
        ),
        location = Location(
            name = "Citadel of Ricks", url = "https://rickandmortyapi.com/api/location/3"
        ),
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        episode = listOf(
            "https://rickandmortyapi.com/api/episode/1",
            "https://rickandmortyapi.com/api/episode/2",
            // ...
        ),
        url = "https://rickandmortyapi.com/api/character/1",
        created = "2017-11-04T18:48:46.250Z"
    )
) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(
            Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),

                )
            Column(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                Text(
                    text = character.name,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                    ),
                )
                Text(
                    text = character.status,
                    style = TextStyle(
                        fontSize = 14.sp,
                    ),
                )
                Text(
                    text = character.species,
                    style = TextStyle(
                        fontSize = 14.sp,
                    ),
                )
                Text(
                    text = character.location.name,
                    style = TextStyle(
                        fontSize = 14.sp,
                    ),
                )
            }
        }
    }
}