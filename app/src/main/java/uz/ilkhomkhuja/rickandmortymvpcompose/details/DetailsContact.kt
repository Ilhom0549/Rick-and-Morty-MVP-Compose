package uz.ilkhomkhuja.rickandmortymvpcompose.details

import android.content.Context
import uz.ilkhomkhuja.rickandmortymvpcompose.models.Result

interface DetailsContact {

    interface View {
        fun showError(error: Throwable)
    }

    interface Model {
        interface OnFinishedListener {
            fun onFinished(detail: Result)
            fun onFailure(error: Throwable)
        }

        fun fetchDetails(onFinishedListener: OnFinishedListener, context: Context, id: Int)
    }

    interface Presenter {
        fun getDetails(id: Int)
        fun onDestroy()
    }
}