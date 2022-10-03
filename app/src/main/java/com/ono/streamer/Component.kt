package com.ono.streamer

import com.ono.streamerlibrary.ManagerMainModule
import com.ono.streamerlibrary.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(modules = [AndroidSupportInjectionModule::class, AppMainModule::class, ManagerMainModule::class])
interface Component : AndroidInjector<StreamerClass>{

    @Component.Builder
    abstract class Builder: AndroidInjector.Factory<StreamerClass>{


        override fun create(instance: StreamerClass?): AndroidInjector<StreamerClass> {
            binds(instance!!)
            return build()
        }

        @BindsInstance
        abstract fun binds(ringTokApp: StreamerClass): Builder
        abstract fun build():AndroidInjector<StreamerClass>

    }
}