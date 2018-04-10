package com.cameronvwilliams.raise.di

import com.cameronvwilliams.raise.RaiseApplicationMockDebug
import com.cameronvwilliams.raise.data.DataModuleMockDebug
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    RaiseModuleMockDebug::class,
    DataModuleMockDebug::class,
    ActivityBindingModule::class,
    AndroidSupportInjectionModule::class
])
interface RaiseComponentMockDebug: RaiseComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: RaiseApplicationMockDebug): Builder

        fun build(): RaiseComponentMockDebug
    }
}
