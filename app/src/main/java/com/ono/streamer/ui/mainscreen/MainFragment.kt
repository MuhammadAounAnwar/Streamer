package com.ono.streamer.ui.mainscreen

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ono.streamer.MediaAdapter
import com.ono.streamer.R
import com.ono.streamer.databinding.FragmentMainBinding
import com.ono.streamer.ui.ViewModelFactory
import com.ono.streamerlibrary.LoaderDialog
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
    private var searchView: SearchView? = null
    var searchMenuItem: MenuItem? = null

    private val loader by lazy {
        LoaderDialog(requireContext())
    }

    private val viewModel by lazy {
        val factory = ViewModelFactory(injector, this, null)
        ViewModelProvider(requireActivity(), factory)[StreamerViewModel::class.java]
    }

    private val moviesAdapter by lazy {
        MediaAdapter(requireContext(), onItemClicked = {
            navigateToDetailFragment(it)
        })
    }
    private val tvShowsAdapter by lazy {
        MediaAdapter(requireContext(), onItemClicked = {
            navigateToDetailFragment(it)
        })
    }
    private val profilesAdapter by lazy {
        MediaAdapter(requireContext(), onItemClicked = {
            navigateToDetailFragment(it)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        loader.createProgressDialog()
        binding.rvMovies.adapter = moviesAdapter
        binding.rvTvShows.adapter = tvShowsAdapter
        binding.rvProfiles.adapter = profilesAdapter
        binding.toolbar.setNavigationOnClickListener { requireActivity().finish() }

        initObservers()
        setupToolbar()

        return binding.root
    }

    private fun initObservers() {
        viewModel._loader.observe(requireActivity()) {
            if (it) {
                loader.showLoadingDialog()
            } else {
                loader.hideLoadingDialog()
            }
        }
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu)
        searchView = menu.findItem(R.id.searchIcon).actionView as SearchView
//        searchView.isSubmitButtonEnabled = true;
        searchView!!.isIconified = true
        searchView!!.queryHint = "Search"
        searchView!!.clearFocus()
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.getSearchedResult(newText) }
                return true
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is HasAndroidInjector) {
            AndroidSupportInjection.inject(this)
        }
    }

    override fun onResume() {
        super.onResume()
        searchMenuItem?.let {
            if (it.isActionViewExpanded)
                it.collapseActionView()
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector


    private fun navigateToDetailFragment(result: com.ono.streamerlibrary.models.Result) {
        viewModel.defineSelectedItem(result)
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
    }
}