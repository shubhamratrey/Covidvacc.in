package com.sillylife.covidvaccin.views.module

import com.sillylife.covidvaccin.constants.NetworkConstants
import com.sillylife.covidvaccin.models.responses.LocationResponse
import com.sillylife.covidvaccin.services.CallbackWrapper
import com.sillylife.covidvaccin.utils.CommonUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class HomeFragmentModule(val listener: APIModuleListener) : BaseModule() {

    fun getLocation(state: String?, district: String?) {
        val hashMap = HashMap<String, String>()
        if (CommonUtil.textIsNotEmpty(state)) {
            hashMap[NetworkConstants.API_PATH_QUERY_STATE] = state!!
        }
        if (CommonUtil.textIsNotEmpty(district)) {
            hashMap[NetworkConstants.API_PATH_QUERY_DISTRICT] = district!!
        }
        appDisposable.add(apiService
                .getLocations(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object : CallbackWrapper<Response<LocationResponse>>() {
                    override fun onSuccess(t: Response<LocationResponse>) {
                        if (t.isSuccessful) {
                            listener.onLocationApiSuccess(t.body()!!)
                        } else {
                            listener.onApiFailure(t.code(), "empty body")
                        }
                    }

                    override fun onFailure(code: Int, message: String) {
                        listener.onApiFailure(code, message)
                    }
                }))
    }

//    fun getHomeData(pageNo: Int) {
//        val hashMap = HashMap<String, String>()
//        hashMap[NetworkConstants.API_PATH_QUERY_PAGE] = pageNo.toString()
//        appDisposable.add(apiService
//                .getHomeData(hashMap)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object : CallbackWrapper<Response<HomeDataResponse>>() {
//                    override fun onSuccess(t: Response<HomeDataResponse>) {
//                        if (t.isSuccessful) {
//                            listener.onHomeApiSuccess(t.body()!!)
//                        } else {
//                            listener.onApiFailure(t.code(), "empty body")
//                        }
//                    }
//
//                    override fun onFailure(code: Int, message: String) {
//                        listener.onApiFailure(code, message)
//                    }
//                }))
//    }
//
//    fun getRealTimeUpdates(executiveId: Int) {
//        messagesListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.hasChild("status") && dataSnapshot.hasChild("request_id")) {
//                    listener.onRealTimeOrderUpdates(dataSnapshot.child("status").value.toString(), dataSnapshot.child("request_id").value.toString().toInt())
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                listener.onRealTimeOrderUpdateFailure(error.message)
//            }
//        }
//        firebaseDatabase.child("delivery_order_request/executive_id/${executiveId}").addValueEventListener(messagesListener!!)
//    }

//    fun getPhoneContacts(phoneNumbers: ArrayList<String>) {
//        appDisposable.add(apiService
//                .syncContacts(phoneNumbers)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object : CallbackWrapper<Response<SyncedContactsResponse>>() {
//                    override fun onSuccess(t: Response<SyncedContactsResponse>) {
//                        if (t.isSuccessful) {
//                            listener.onContactPhoneSyncSuccess(t.body()!!)
//                        } else {
//                            listener.onApiFailure(t.code(), "empty body")
//                        }
//                    }
//
//                    override fun onFailure(code: Int, message: String) {
//                        listener.onApiFailure(code, message)
//                    }
//                }))
//    }

//    fun ringBell(profileId: Int) {
//        appDisposable.add(
//            apiService
//                .ringBell(profileId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object : CallbackWrapper<Response<GenericResponse>>() {
//                    override fun onSuccess(t: Response<GenericResponse>) {
//                        if (t.isSuccessful) {
////                            listener.onRingBellApiSuccess(t.body()!!)
//                        } else {
//                            listener.onApiFailure(t.code(), t.message())
//                        }
//                    }
//
//                    override fun onFailure(code: Int, message: String) {
//                        listener.onApiFailure(code, message)
//                    }
//                })
//        )
//    }

    interface APIModuleListener {
        fun onLocationApiSuccess(response: LocationResponse?)
        fun onApiFailure(statusCode: Int, message: String)
    }
}