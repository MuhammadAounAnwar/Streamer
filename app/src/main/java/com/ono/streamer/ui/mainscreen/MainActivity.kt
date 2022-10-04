package com.ono.streamer.ui.mainscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ono.streamer.R
import com.ono.streamer.databinding.ActivityMainBinding
import com.ono.streamer.ui.ViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var injector: StreamerViewModelInjector

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val viewModel by lazy {
        val factory = ViewModelFactory(injector, this, null)
        ViewModelProvider(this, factory)[StreamerViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (application is HasAndroidInjector) {
            AndroidInjection.inject(this)
        }
        super.onCreate(savedInstanceState)

        binding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.initViewModel()
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}