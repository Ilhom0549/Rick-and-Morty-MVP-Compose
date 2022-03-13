package uz.ilkhomkhuja.rickandmortymvpcompose.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import uz.ilkhomkhuja.rickandmortymvpcompose.common.StateScreen
import uz.ilkhomkhuja.rickandmortymvpcompose.details.DetailsActivity
import uz.ilkhomkhuja.rickandmortymvpcompose.models.Result
import uz.ilkhomkhuja.rickandmortymvpcompose.ui.theme.RickAndMortyMVPComposeTheme

const val ID = "id"

class MainActivity : ComponentActivity(), ListContact.View {

    private var presenter: ListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ListPresenter(this, ListModel(this), this)
        presenter?.getList()

        setContent {
            RickAndMortyMVPComposeTheme {
                StateScreen(isLoading = presenter?.isLoading == true) {
                    CharacterList(
                        list = presenter?.characters!!,
                        startDetails = ::startDetailsActivity
                    )
                }
            }
        }
    }

    override fun showError(error: Throwable) {
        Log.d("Ricky and Morty MVP", error.localizedMessage ?: "Error")
    }

    override fun startDetailsActivity(id: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(ID,id)
        startActivity(intent)
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        super.onDestroy()
    }
}

@Composable
fun CharacterItem(character: Result, startDetails: (Int) -> Unit) {
    Row {
        Column(modifier = Modifier.clickable { startDetails(character.id) }) {
            Image(
                painter = rememberAsyncImagePainter(character.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp, 120.dp)

            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterList(list: List<Result>, startDetails: (Int) -> Unit) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 100.dp), modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
    ) {
        items(list) { item ->
            CharacterItem(character = item, startDetails = startDetails)
        }
    }
}