package uz.ilkhomkhuja.rickandmortymvpcompose.list

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import uz.ilkhomkhuja.rickandmortymvpcompose.models.Result

class ListPresenter(
    private var view: ListContact.View?,
    private val model: ListModel,
    private val context: Context
) : ListContact.Presenter,
    ListContact.Model.OnFinishedListener {

    var characters: List<Result> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(true)

    override fun onFinished(list: List<Result>) {
        characters = list
        isLoading = false
    }

    override fun onFailure(error: Throwable) {
        view?.showError(error)
        isLoading = false
    }

    override fun getList() {
        model.fetchList(this, context)
    }

    override fun onDestroy() {
        model.job.cancel()
        view = null
    }
}