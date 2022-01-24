package com.newtech.newtech_sfm.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment<P : BasePresenter<BaseView>> : BaseView, Fragment() {

    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = instantiatePresenter()
    }
    /**
     * Instantiates the presenter the Activity is based on.
     */
    protected abstract fun instantiatePresenter(): P

    override fun getContext(): Context {
        return context
    }
}