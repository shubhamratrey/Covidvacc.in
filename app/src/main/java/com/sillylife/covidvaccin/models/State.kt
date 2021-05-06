package com.sillylife.covidvaccin.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created on 24/09/18.
 */

@Parcelize
data class State(
        @SerializedName("state_id") var stateId: Int? = null,
        @SerializedName("state_name") var stateName: String? = null,
        @SerializedName("state_slug") var stateSlug: String? = null,
) : Parcelable {

}