package com.sillylife.covidvaccin.views.module

import com.sillylife.covidvaccin.constants.NetworkConstants
import com.sillylife.covidvaccin.models.responses.SlotsResponse
import com.sillylife.covidvaccin.services.CallbackWrapper
import com.sillylife.covidvaccin.utils.CommonUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class SlotsModule(val listener: APIModuleListener) : BaseModule() {

    fun getSlots(district: String?, filters: List<String>, date: String?) {
        val hashMap = HashMap<String, String>()
        if (CommonUtil.textIsNotEmpty(district)) {
            hashMap[NetworkConstants.API_PATH_QUERY_DISTRICT_SLUG] = district!!
        }
        if (CommonUtil.textIsNotEmpty(date)) {
            hashMap[NetworkConstants.API_PATH_QUERY_DATE] = date!!
        }

        appDisposable.add(apiService
                .getSlots(hashMap, filters)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object : CallbackWrapper<Response<SlotsResponse>>() {
                    override fun onSuccess(t: Response<SlotsResponse>) {
                        if (t.isSuccessful) {
                            listener.onSlotsApiSuccess(t.body()!!)
                        } else {
                            listener.onApiFailure(t.code(), "empty body")
                        }
                    }

                    override fun onFailure(code: Int, message: String) {
                        listener.onApiFailure(code, message)
                    }
                }))
    }

    fun getSessions(districtId: Int, date: String) {
        val hashMap = HashMap<String, String>()
        hashMap[NetworkConstants.API_PATH_QUERY_DISTRICT_ID] = districtId.toString()
        hashMap[NetworkConstants.API_PATH_QUERY_DATE] = date
        appDisposable.add(application.getAPIService(baseUrl = "https://cdn-api.co-vin.in/api/")
                .getSessions(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object : CallbackWrapper<Response<SlotsResponse>>() {
                    override fun onSuccess(t: Response<SlotsResponse>) {
                        if (t.isSuccessful) {
                            listener.onSlotsApiSuccess(t.body()!!)
                        } else {
                            listener.onApiFailure(t.code(), "empty body")
                        }
                    }

                    override fun onFailure(code: Int, message: String) {
                        listener.onApiFailure(code, message)
                    }
                }))
    }

    interface APIModuleListener {
        fun onSlotsApiSuccess(response: SlotsResponse?)
        fun onApiFailure(statusCode: Int, message: String)
    }
}