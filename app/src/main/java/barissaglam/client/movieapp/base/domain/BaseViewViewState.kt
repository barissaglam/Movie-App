package barissaglam.client.movieapp.base.domain

data class BaseViewViewState(
    val showLoading: Boolean = false,
    val showContent: Boolean = false,
    val showError: Boolean = false,
    val showEmpty: Boolean = false,

    val throwable: Throwable? = null
)
