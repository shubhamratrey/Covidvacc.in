package com.sillylife.covidvaccin.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created on 24/09/18.
 */

@Parcelize
data class District(
        @SerializedName("district_id") var districtId: Int? = null,
        @SerializedName("district_name") var districtName: String? = null,
        @SerializedName("district_slug") var districtSlug: String? = null,
        @SerializedName("state_slug") var stateSlug: String? = null,
) : Parcelable {

}