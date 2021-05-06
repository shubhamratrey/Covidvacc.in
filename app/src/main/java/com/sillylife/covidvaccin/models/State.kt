package com.sillylife.covidvaccin.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created on 24/09/18.
 */

@Parcelize
data class State(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("slug") var slug: String? = null,
        @SerializedName("image") var image: String? = null,
        @SerializedName("cowin_state_id") var cowinStateId: Int? = null
) : Parcelable {

}