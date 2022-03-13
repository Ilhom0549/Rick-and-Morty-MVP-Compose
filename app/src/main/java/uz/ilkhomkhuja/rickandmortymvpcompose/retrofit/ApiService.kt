package uz.ilkhomkhuja.rickandmortymvpcompose.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import uz.ilkhomkhuja.rickandmortymvpcompose.models.ListCharacters
import uz.ilkhomkhuja.rickandmortymvpcompose.models.Result

interface ApiService {

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<Result>

    @GET("character")
    suspend fun getCharactersList(): Response<ListCharacters>
}