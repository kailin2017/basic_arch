package com.kailin.basic_arch.ui.home

import androidx.activity.OnBackPressedCallback
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.kailin.basic_arch.BR
import com.kailin.basic_arch.R
import com.kailin.basic_arch.app.DataBindingConfig
import com.kailin.basic_arch.app.DataBindingFragment
import com.kailin.basic_arch.databinding.FragmentHomeBinding
import com.kailin.basic_arch.widget.navigation
import com.kailin.basic_arch.widget.setActionBar

class HomeFragment : DataBindingFragment(), HomeEventProxy {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateDataBindingConfig(): DataBindingConfig =
        DataBindingConfig(R.layout.fragment_home)
            .setDataStateViewModel(BR.viewModel, viewModel)
            .addParam(BR.eventProxy, this)

    override fun onViewInit(binding: ViewDataBinding) =
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAndRemoveTask()
                }
            })

    override fun onClickGithub() =
        navigation(HomeFragmentDirections.actionHomeFragmentToGithubFragment())

    override fun onClickNews() =
        navigation(HomeFragmentDirections.actionHomeFragmentToNewsFragment())

    override fun onClickLogin() =
        navigation(HomeFragmentDirections.actionHomeFragmentToLoginFragment())

    override fun onClickExoPlayer() =
        navigation(HomeFragmentDirections.actionHomeFragmentToExoPlayerFragment())
}