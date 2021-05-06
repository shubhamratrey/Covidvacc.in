package com.sillylife.covidvaccin.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created on 24/09/18.
 */

@Parcelize
data class UserProfile(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("user_ptr_id") var userPtrId: Int? = null,
        @SerializedName("fullname") var fullname: String? = null,
        @SerializedName("is_parent") var isParent: Boolean? = null,
        @SerializedName("unique_code") var uniqueCode: String? = null,
        @SerializedName("non_productive_usage") var non_productive_usage: Long? = null,
) : Parcelable {

}