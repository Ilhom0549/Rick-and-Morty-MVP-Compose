package uz.ilkhomkhuja.rickandmortymvpcompose.list

import android.content.Context
import uz.ilkhomkhuja.rickandmortymvpcompose.models.Result

interface ListContact {

    interface View {
        fun showError(error: Throwable)
        fun startDetailsActivity(id: Int)
    }

    interface Model {
        interface OnFinishedListener {
            fun onFinished(list: List<Result>)
            fun onFailure(error: Throwable)
        }

        fun fetchList(onFinishedListener: OnFinishedListener, context: Context)
    }

    interface Presenter {
        fun getList()
        fun onDestroy()
    }
}