package com.cameronvwilliams.raise.di

import com.cameronvwilliams.raise.RaiseApplicationDebug
import com.cameronvwilliams.raise.data.DataModuleDebug
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    RaiseModuleDebug::class,
    DataModuleDebug::class,
    ActivityBindingModuleDebug::class,
    AndroidSupportInjectionModule::class
])
interface RaiseComponentDebug: RaiseComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: RaiseApplicationDebug): Builder

        fun build(): RaiseComponentDebug
    }
}
