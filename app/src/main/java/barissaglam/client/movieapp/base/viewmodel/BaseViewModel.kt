package barissaglam.client.movieapp.base.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import barissaglam.client.movieapp.base.domain.BaseViewViewState
import barissaglam.client.movieapp.util.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel(
    val baseViewViewStateLiveData: SingleLiveEvent<BaseViewViewState> = SingleLiveEvent()
) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    fun getCompositeDisposable() = compositeDisposable
    fun Disposable.track() = compositeDisposable.add(this)
    open fun handleIntent(extras: Bundle) {}

    inline fun <T> Observable<T>.sendRequest(
        crossinline successHandler: (T) -> Unit
    ) {
        updateUIState(showLoading = true)
        subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->
                    successHandler(data)
                    updateUIState(showContent = true)
                },
                { throwable ->
                    updateUIState(showError = true, throwable = throwable)
                }).track()
    }

    inline fun <T> Observable<T>.sendRequestWithCheckEmpty(
        crossinline successHandler: (T) -> Unit,
        crossinline emptyHandler: (T) -> Boolean
    ) {
        updateUIState(showLoading = true)
        subscribe(
            { data ->
                successHandler(data)
                if (emptyHandler(data)) updateUIState(showEmpty = true)
                else updateUIState(showContent = true)
            },
            { throwable ->
                updateUIState(showError = true, throwable = throwable)
            }).track()
    }

    fun updateUIState(
        showLoading: Boolean = false,
        showContent: Boolean = false,
        showError: Boolean = false,
        showEmpty: Boolean = false,
        throwable: Throwable? = null
    ) {
        baseViewViewStateLiveData.value = BaseViewViewState(showLoading, showContent, showError, showEmpty, throwable)
    }

    override fun onCleared() = compositeDisposable.dispose()

}