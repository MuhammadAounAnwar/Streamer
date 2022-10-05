package com.ono.streamer.ui.mainscreen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ono.streamer.databinding.FragmentDetailBinding
import com.ono.streamer.ui.ViewModelFactory
import com.ono.streamer.ui.player.VideoPlayerActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DetailFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var injector: StreamerViewModelInjector

    lateinit var binding: FragmentDetailBinding

    private val viewModel by lazy {
        val factory = ViewModelFactory(injector, this, null)
        ViewModelProvider(requireActivity(), factory)[StreamerViewModel::class.java]
    }

    private val clickListener by lazy {
        View.OnClickListener {
            VideoPlayerActivity.initiateActivity(requireActivity(), viewModel.selectedItem.value?.backdrop_path.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.clickListener = clickListener
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is HasAndroidInjector) {
            AndroidSupportInjection.inject(this)
        }

    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}