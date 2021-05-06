package com.sillylife.covidvaccin.events

import android.content.Intent
import com.sillylife.covidvaccin.constants.RxEventType

class RxEvent {
    data class NetworkConnectivity(var isConnected: Boolean)
    data class ActivityResult(var requestCode: Int, var resultCode: Int, var data: Intent?)
    class Action(var eventType: RxEventType, vararg val items: Any)
}