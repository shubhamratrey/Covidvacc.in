package com.sillylife.covidvaccin.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created on 24/09/18.
 */

@Parcelize
data class Slot(
        @SerializedName("center_id") var center_id: Int? = null,
        @SerializedName("center_name") var center_name: String? = null,
        @SerializedName("address") var address: String? = null,
        @SerializedName("state_name") var state_name: String? = null,
        @SerializedName("district_name") var district_name: String? = null,
        @SerializedName("pincode") var pincode: String? = null,
        @SerializedName("vaccine") var vaccine: String? = null,
        @SerializedName("min_age_limit") var min_age_limit: Int? = null,
        @SerializedName("fee_type") var fee_type: String? = null,
        @SerializedName("available_capacity") var available_capacity: String? = null,
) : Parcelable {

}
