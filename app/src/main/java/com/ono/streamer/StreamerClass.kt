package com.ono.streamer

import android.app.Application
import com.ono.streamer.di.DaggerComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class StreamerClass : Application(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    private val daggerComponent by lazy {
        DaggerComponent.builder().create(this).inject(this)
    }

    override fun onCreate() {
        daggerComponent
        super.onCreate()
    }

    override fun androidInjector(): AndroidInjector<Any> = injector
}