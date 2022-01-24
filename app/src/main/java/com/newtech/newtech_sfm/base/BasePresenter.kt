package com.newtech.newtech_sfm.base


abstract class BasePresenter<out V : BaseView>(protected val view: V) {

    /**
     * This method may be called when the presenter view is created
     */
    open fun onViewCreated(){}

    /**
     * This method may be called when the presenter view is destroyed
     */
    open fun onViewDestroyed(){}



}