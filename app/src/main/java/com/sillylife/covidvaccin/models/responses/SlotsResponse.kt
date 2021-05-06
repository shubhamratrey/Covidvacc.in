package com.sillylife.covidvaccin.models.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sillylife.covidvaccin.models.District
import com.sillylife.covidvaccin.models.Slot
import com.sillylife.covidvaccin.models.SlotFilter
import com.sillylife.covidvaccin.models.State
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SlotsResponse(
        @SerializedName("slots") var slots: ArrayList<Slot>? = null,
        @SerializedName("sessions") var sessions: ArrayList<Slot>? = null,
        @SerializedName("filters") var filters: ArrayList<SlotFilter>? = null,
        @SerializedName("state") var state: State? = null,
        @SerializedName("district") var district: District? = null,
        @SerializedName("n_slots") var nSlots: Int? = null,
) : Parcelable