package barissaglam.client.movieapp.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import barissaglam.client.movieapp.base.domain.BaseViewViewState
import barissaglam.client.movieapp.base.extension.getErrorMessage
import barissaglam.client.movieapp.base.extension.orFalse
import barissaglam.client.movieapp.base.extension.runContextNotNull
import barissaglam.client.movieapp.base.viewmodel.BaseViewModel
import barissaglam.client.movieapp.view.BaseView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @get:LayoutRes
    protected abstract val layoutResourceId: Int
    lateinit var binding: DB
    lateinit var viewModel: VM
    protected abstract val classTypeOfViewModel: Class<VM>
    private var hasRequestSend = false
    protected var baseView: BaseView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(classTypeOfViewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            viewModel.handleIntent(it)
        }
        setUpViewBaseModelStateObservers()
        setUpViewModelStateObservers()
        setupClickListeners()
        init()
        if (!hasRequestSend) {
            initStartRequest()
            hasRequestSend = true
        }
    }


    open fun initStartRequest() {}
    open fun init() {}
    open fun setUpViewModelStateObservers() {}
    open fun setupClickListeners() {}

    private fun setUpViewBaseModelStateObservers() {
        viewModel.baseViewViewStateLiveData.observe(viewLifecycleOwner, ::onStateChanged)
    }

    private fun onStateChanged(stateView: BaseViewViewState?) {
        when {
            stateView?.showLoading.orFalse() -> baseView?.showLoading()
            stateView?.showContent.orFalse() -> baseView?.showContent()
            stateView?.showEmpty.orFalse() -> baseView?.showEmpty()
            stateView?.showError.orFalse() -> {
                runContextNotNull { context ->
                    val errorMsg = stateView?.throwable?.getErrorMessage(context)
                    baseView?.showError()
                    baseView?.setErrorText(errorMsg)
                }
            }
        }
    }
}