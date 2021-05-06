package com.sillylife.covidvaccin.views.viewmodal

import com.sillylife.covidvaccin.models.responses.LocationResponse
import com.sillylife.covidvaccin.views.fragments.BaseFragment
import com.sillylife.covidvaccin.views.module.BaseModule
import com.sillylife.covidvaccin.views.module.HomeFragmentModule

class HomeFragmentViewModel(fragment: BaseFragment) : BaseViewModel(),
        HomeFragmentModule.APIModuleListener {

    val module = HomeFragmentModule(this)
    val listener = fragment as HomeFragmentModule.APIModuleListener
    override fun onLocationApiSuccess(response: LocationResponse?) {
        listener.onLocationApiSuccess(response)
    }
//    override fun onContactPhoneSyncSuccess(response: SyncedContactsResponse) {
//        listener.onContactPhoneSyncSuccess(response)
//    }

    override fun onApiFailure(statusCode: Int, message: String) {
        listener.onApiFailure(statusCode, message)
    }

//    override fun onRealTimeOrderUpdates(status: String, requestId: Int) {
//        listener.onRealTimeOrderUpdates(status, requestId)
//    }
//
//    override fun onRealTimeOrderUpdateFailure(message: String) {
//        listener.onRealTimeOrderUpdateFailure(message)
//    }
//
//    override fun onHomeApiSuccess(response: HomeDataResponse?) {
//        listener.onHomeApiSuccess(response)
//    }
//
//    override fun onContactPhoneSyncSuccess(contacts: ArrayList<Contact>) {
//        listener.onContactPhoneSyncSuccess(contacts)
//    }

//
//    override fun onRingBellApiSuccess(response: GenericResponse) {
//        listener.onRingBellApiSuccess(response)
//    }

    override fun setViewModel(): BaseModule {
        return module
    }

    fun getLocation(state: String?, district: String?) {
        module.getLocation(state, district)
    }

//    fun getHomeData(pageNo: Int) {
//        module.getHomeData(pageNo)
//    }
//
//
//    fun getRealTimeUpdates(executiveId: Int) {
//        module.getRealTimeUpdates(executiveId)
//    }

//    fun getPhoneContacts(phoneNumbers: ArrayList<String>) {
//        module.getPhoneContacts(phoneNumbers)
//    }

    fun ringBell(profileId: Int) {
//        module.ringBell(profileId)
    }
}