package com.ono.streamer.ui.mainscreen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ono.streamer.MediaAdapter
import com.ono.streamer.databinding.FragmentMainBinding
import com.ono.streamer.ui.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MainFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var injector: StreamerViewModelInjector

    lateinit var binding: FragmentMainBinding

    private val viewModel by lazy {
        val factory = ViewModelFactory(injector, this, null)
        ViewModelProvider(requireActivity(), factory)[StreamerViewModel::class.java]
    }

    private val moviesAdapter by lazy {
        MediaAdapter(requireContext())
    }
    private val tvShowsAdapter by lazy {
        MediaAdapter(requireContext())
    }
    private val profilesAdapter by lazy {
        MediaAdapter(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.rvMovies.adapter = moviesAdapter
        binding.rvTvShows.adapter = tvShowsAdapter
        binding.rvProfiles.adapter = profilesAdapter
        binding.toolbar.setNavigationOnClickListener { requireActivity().finish() }
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