package uz.ilkhomkhuja.rickandmortymvpcompose.details

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import uz.ilkhomkhuja.rickandmortymvpcompose.common.StateScreen
import uz.ilkhomkhuja.rickandmortymvpcompose.list.ID
import uz.ilkhomkhuja.rickandmortymvpcompose.models.Result
import uz.ilkhomkhuja.rickandmortymvpcompose.ui.theme.RickAndMortyMVPComposeTheme

class DetailsActivity : ComponentActivity(), DetailsContact.View {

    private var presenter: DetailsPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getIntExtra(ID, -1)
        presenter = DetailsPresenter(this, DetailsModel(this), this)
        presenter?.getDetails(id)

        setContent {
            RickAndMortyMVPComposeTheme {
                StateScreen(isLoading = presenter?.isLoading == true) {
                    if (presenter?.characters != null) {
                       DetailsCard(result = presenter?.characters!!)
                    }
                }
            }
        }
    }

    override fun showError(error: Throwable) {
        Log.d("DetailsActivity", error.message ?: "Error")
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        super.onDestroy()
    }
}

@Composable
fun DetailsCard(result: Result) {
    Column {
        Image(
            painter = rememberAsyncImagePainter(result.image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)

        )
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = result.name, style = MaterialTheme.typography.subtitle1, fontSize = 25.sp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Canvas(modifier = Modifier
                    .size(10.dp)
                    .padding(end = 4.dp), onDraw = {
                    drawCircle(color = result.statusIndicator)
                })
                if (result.status == "Alive") {
                    Text(
                        text = "LIVE",
                        style = MaterialTheme.typography.body2,
                        fontSize = 16.sp
                    )
                } else {
                    Text(
                        text = result.status.uppercase(),
                        style = MaterialTheme.typography.body2,
                        fontSize = 16.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Gender:",
                style = MaterialTheme.typography.subtitle2,
                fontSize = 16.sp
            )
            Text(
                text = result.gender,
                style = MaterialTheme.typography.caption,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Specie:",
                style = MaterialTheme.typography.subtitle2,
                fontSize = 16.sp
            )
            Text(
                text = result.species,
                style = MaterialTheme.typography.caption,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Last known location:",
                style = MaterialTheme.typography.subtitle2,
                fontSize = 16.sp
            )
            Text(
                text = result.location.name,
                style = MaterialTheme.typography.caption,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "First seen in:",
                style = MaterialTheme.typography.subtitle2,
                fontSize = 16.sp
            )
            Text(
                text = result.origin.name,
                style = MaterialTheme.typography.caption,
                fontSize = 14.sp
            )
        }
    }
}