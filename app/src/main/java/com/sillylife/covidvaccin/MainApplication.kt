package com.sillylife.covidvaccin

import android.app.Application
import com.google.firebase.FirebaseApp
import com.sillylife.covidvaccin.services.APIService
import com.sillylife.covidvaccin.services.AppDisposable
import com.sillylife.covidvaccin.services.IAPIService


class MainApplication : Application() {

    @Volatile
    private var mIAPIService: IAPIService? = null


    @Volatile
    private var mIAPIServiceWithBaseUrl: IAPIService? = null

    @Volatile
    private var mIAPIServiceCache: IAPIService? = null


    var appDisposable: AppDisposable = AppDisposable()

    companion object {
        @Volatile
        private var application: MainApplication? = null

        @Synchronized
        fun getInstance(): MainApplication {
            return application!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        application = this@MainApplication
    }

    @Synchronized
    fun getAPIService(): IAPIService {
        if (mIAPIService == null) {
            mIAPIService = APIService.build()
        }
        return mIAPIService!!
    }

    @Synchronized
    fun getAPIService(baseUrl: String): IAPIService {
        if (mIAPIServiceWithBaseUrl == null) {
            mIAPIServiceWithBaseUrl = APIService.build(baseUrl)
        }
        return mIAPIServiceWithBaseUrl!!
    }

    @Synchronized
    fun getAPIService(cacheEnabled: Boolean): IAPIService {
        if (mIAPIService == null) {
            mIAPIService = APIService.build()
        }
        if (mIAPIServiceCache == null) {
            val cacheDuration = 3600.toLong()
            mIAPIServiceCache = APIService.build(this, cacheDuration)

        }
        return if (cacheEnabled) mIAPIServiceCache!! else mIAPIService!!
    }


}