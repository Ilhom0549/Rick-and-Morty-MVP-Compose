package uz.ilkhomkhuja.rickandmortymvpcompose.models

import androidx.compose.ui.graphics.Color

data class Result(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) {
    val statusIndicator: Color
        get() {
            return if (status == "Alive") Color.Green else Color.Red
        }
}