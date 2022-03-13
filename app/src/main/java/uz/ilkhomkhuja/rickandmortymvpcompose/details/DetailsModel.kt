package uz.ilkhomkhuja.rickandmortymvpcompose.details

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*
import uz.ilkhomkhuja.rickandmortymvpcompose.retrofit.ApiClient
import uz.ilkhomkhuja.rickandmortymvpcompose.retrofit.ApiService
import uz.ilkhomkhuja.rickandmortymvpcompose.utils.InternetConnectionHelper
import kotlin.coroutines.CoroutineContext

class DetailsModel(private var owner: LifecycleOwner) : DetailsContact.Model, CoroutineScope {

    var job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun fetchDetails(
        onFinishedListener: DetailsContact.Model.OnFinishedListener,
        context: Context,
        id: Int
    ) {
        InternetConnectionHelper(context).observe(owner) { networkState ->
            if (networkState) {
                val api = ApiClient.getRetrofit().create(ApiService::class.java)
                launch {
                    try {
                        coroutineScope {
                            val character = api.getCharacter(id)
                            if (character.isSuccessful) {
                                character.body()?.let { onFinishedListener.onFinished(it) }
                            } else {
                                onFinishedListener.onFailure(
                                    Throwable(
                                        character.errorBody()?.string() ?: "Error"
                                    )
                                )
                            }
                        }
                    } catch (e: Exception) {
                        onFinishedListener.onFailure(e)
                    }
                }
            } else {
                onFinishedListener.onFailure(Throwable("Internet Disconnected !!"))
            }
        }
    }
}