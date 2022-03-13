package uz.ilkhomkhuja.rickandmortymvpcompose.details

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import uz.ilkhomkhuja.rickandmortymvpcompose.models.Result

class DetailsPresenter(
    private var view: DetailsContact.View?,
    private val model: DetailsModel,
    private val context: Context
) : DetailsContact.Presenter,
    DetailsContact.Model.OnFinishedListener {

    var characters: Result? by mutableStateOf(null)
    var isLoading: Boolean by mutableStateOf(true)

    override fun onFinished(detail: Result) {
        characters = detail
        isLoading = false
    }

    override fun onFailure(error: Throwable) {
        view?.showError(error)
    }

    override fun getDetails(id: Int) {
        model.fetchDetails(this, context, id)
    }

    override fun onDestroy() {
        model.job.cancel()
        view = null
    }
}