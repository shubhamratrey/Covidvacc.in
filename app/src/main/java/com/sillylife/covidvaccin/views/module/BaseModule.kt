package com.sillylife.covidvaccin.views.module

import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sillylife.covidvaccin.MainApplication
import com.sillylife.covidvaccin.services.AppDisposable


open class BaseModule {
    val application = MainApplication.getInstance()
    val apiService = application.getAPIService()
    var appDisposable = AppDisposable()
    var firebaseDatabase = Firebase.database.reference
    var messagesListener: ValueEventListener? = null

    fun onDestroy() {
        if (appDisposable != null) {
            appDisposable.dispose()
        }
        if (messagesListener != null) {
            firebaseDatabase.removeEventListener(messagesListener!!)
        }
    }

    fun getDisposable(): AppDisposable {
        if (appDisposable == null) {
            appDisposable = AppDisposable()
        }
        return appDisposable
    }


}