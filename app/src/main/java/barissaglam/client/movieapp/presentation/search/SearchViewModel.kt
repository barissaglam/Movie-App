package barissaglam.client.movieapp.presentation.search

import androidx.databinding.ObservableField
import barissaglam.client.movieapp.base.viewmodel.BaseViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor() : BaseViewModel() {
    var query = ObservableField("")

}