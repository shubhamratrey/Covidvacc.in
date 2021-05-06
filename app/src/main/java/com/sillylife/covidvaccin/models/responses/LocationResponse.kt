package com.sillylife.covidvaccin.models.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sillylife.covidvaccin.models.District
import com.sillylife.covidvaccin.models.State
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationResponse(
        @SerializedName("districts") var districts: ArrayList<District>? = null,
        @SerializedName("states") var states: ArrayList<State>? = null,
        @SerializedName("state") var state: State? = null,
        @SerializedName("district") var district: District? = null
) : Parcelable