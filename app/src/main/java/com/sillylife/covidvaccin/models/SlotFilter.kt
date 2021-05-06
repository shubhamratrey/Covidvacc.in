package com.sillylife.covidvaccin.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created on 24/09/18.
 */

@Parcelize
data class SlotFilter(
        @SerializedName("type") var type: String? = null,
        @SerializedName("text") var text: String? = null,
        @SerializedName("value") var value: String? = null,
        @SerializedName("is_selected") var is_selected: Boolean? = null,
) : Parcelable {

}
