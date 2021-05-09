package com.sillylife.covidvaccin.views.viewmodal

import com.sillylife.covidvaccin.models.responses.GenericResponse
import com.sillylife.covidvaccin.models.responses.SlotsResponse
import com.sillylife.covidvaccin.views.fragments.BaseFragment
import com.sillylife.covidvaccin.views.module.BaseModule
import com.sillylife.covidvaccin.views.module.SlotsModule

class SlotsViewModel(fragment: BaseFragment) : BaseViewModel(),
        SlotsModule.APIModuleListener {

    val module = SlotsModule(this)
    val listener = fragment as SlotsModule.APIModuleListener

    override fun onSlotsApiSuccess(response: SlotsResponse?) {
        listener.onSlotsApiSuccess(response)
    }

    override fun onAddReminderApiSuccess(response: GenericResponse?) {
        listener.onAddReminderApiSuccess(response)
    }

    override fun onApiFailure(statusCode: Int, message: String) {
        listener.onApiFailure(statusCode, message)
    }

    override fun setViewModel(): BaseModule {
        return module
    }

    fun getSlots(district: String?, filters: List<String>, date: String?) {
        module.getSlots(district, filters, date)
    }

    fun getSessions(districtId: Int, date: String) {
        module.getSessions(districtId, date)
    }

    fun addReminder(centerId: Int?, districtId: Int?, date: String?, type: String?) {
        module.addReminder(centerId = centerId, districtId = districtId, date = date, type = type)
    }

}