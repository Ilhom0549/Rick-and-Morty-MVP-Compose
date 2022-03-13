package uz.ilkhomkhuja.rickandmortymvpcompose.list

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*
import uz.ilkhomkhuja.rickandmortymvpcompose.retrofit.ApiClient
import uz.ilkhomkhuja.rickandmortymvpcompose.retrofit.ApiService
import uz.ilkhomkhuja.rickandmortymvpcompose.utils.InternetConnectionHelper
import kotlin.coroutines.CoroutineContext

class ListModel(private var owner: LifecycleOwner) : ListContact.Model, CoroutineScope {

    var job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun fetchList(
        onFinishedListener: ListContact.Model.OnFinishedListener,
        context: Context
    ) {
        InternetConnectionHelper(context).observe(owner) { networkState ->
            if (networkState) {
                val api = ApiClient.getRetrofit().create(ApiService::class.java)
                launch {
                    try {
                        coroutineScope {
                            val charactersList = api.getCharactersList()
                            if (charactersList.isSuccessful) {
                                onFinishedListener.onFinished(
                                    charactersList.body()?.results ?: emptyList()
                                )
                            } else {
                                onFinishedListener.onFinished(emptyList())
                                onFinishedListener.onFailure(
                                    Throwable(
                                        charactersList.errorBody()?.string()
                                    )
                                )
                            }
                        }
                    } catch (e: Exception) {
                        onFinishedListener.onFailure(e)
                    }
                }
            } else {
                onFinishedListener.onFinished(emptyList())
                onFinishedListener.onFailure(Throwable("Internet Disconnected !!"))
            }
        }
    }
}