package com.newtech.newtech_sfm.injection

import com.google.android.datatransport.runtime.dagger.BindsInstance
import com.google.android.datatransport.runtime.dagger.Component
import com.newtech.newtech_sfm.base.BaseView
import com.newtech.newtech_sfm.injection.module.ContextModule
import com.newtech.newtech_sfm.merchandising.article.ArticleFragmentPresenter
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(ContextModule::class)])
interface PresenterInjector {

    /**
     * Injects required dependencies into the specified PostPresenter.
     * @param articleFragmentPresenter ArticleFragmentPresenter in which to inject the dependencies
     */

    fun inject(articleFragmentPresenter: ArticleFragmentPresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        //fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }

}