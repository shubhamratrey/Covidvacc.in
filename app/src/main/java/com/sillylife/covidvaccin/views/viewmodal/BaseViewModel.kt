package com.sillylife.covidvaccin.views.viewmodal

import android.os.Handler
import androidx.lifecycle.ViewModel
import com.sillylife.covidvaccin.services.AppDisposable
import com.sillylife.covidvaccin.views.module.BaseModule

open abstract class BaseViewModel : ViewModel() {


    private var baseModule: BaseModule? = null

    abstract fun setViewModel(): BaseModule

    init {
        init()
    }

    private fun init() {
        Handler().postDelayed({
            baseModule = setViewModel()

        }, 200)
    }

    fun onDestroy() {
        baseModule?.onDestroy()
    }

    fun getAppDisposable(): AppDisposable {
        if (baseModule == null) {
            baseModule = setViewModel()
        }
        return baseModule?.getDisposable()!!
    }
}