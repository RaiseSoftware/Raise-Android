package com.cameronvwilliams.raise.di

import com.cameronvwilliams.raise.RaiseApplication
import com.cameronvwilliams.raise.data.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RaiseModule::class,
        DataModule::class,
        ActivityBindingModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface RaiseComponent : AndroidInjector<RaiseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: RaiseApplication): Builder

        fun build(): RaiseComponent
    }
}
