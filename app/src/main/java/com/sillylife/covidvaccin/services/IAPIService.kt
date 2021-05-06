package com.sillylife.covidvaccin.services

import com.sillylife.covidvaccin.constants.NetworkConstants
import com.sillylife.covidvaccin.models.responses.*
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*


interface IAPIService {

    @GET("${NetworkConstants.V1}/covid19/me/")
    fun getMe(@QueryMap queryMap: Map<String, String>): Observable<Response<UserResponse>>

    @FormUrlEncoded
    @POST("${NetworkConstants.V1}/users/register-fcm/")
    fun registerFCM(
            @Field("app_name") appName: String,
            @Field("os_type") osType: String,
            @Field("app_instance_id") appInstanceId: String,
            @Field("app_build_number") appBuildNumber: Int,
            @Field("installed_version") installedVersion: String,
            @Field("fcm_token") fcmToken: String,
            @Field("advertising_id") advertisingId: String
    ): Observable<Response<GenericResponse>>

    @FormUrlEncoded
    @POST("${NetworkConstants.V1}/users/unregister-fcm/")
    fun unregisterFCM(@Field("fcm_token") fcmToken: String): Observable<Response<String>>

    @GET("${NetworkConstants.V1}/home/partner/")
    fun getHomeData(@QueryMap queryMap: Map<String, String>): Observable<Response<HomeDataResponse>>

    @GET("${NetworkConstants.V2}/covid19/locations/")
    fun getLocations(@QueryMap queryMap: Map<String, String>): Observable<Response<LocationResponse>>

    @GET("${NetworkConstants.V2}/covid19/slots/")
    fun getSlots(@QueryMap queryMap: Map<String, String>, @Query("filter") filters: List<String>?): Observable<Response<SlotsResponse>>

    @GET("v2/appointment/sessions/public/findByDistrict/")
    fun getSessions(@QueryMap queryMap: Map<String, String>): Observable<Response<SlotsResponse>>

    @FormUrlEncoded
    @POST("${NetworkConstants.V1}/covid19/me/update/")
    fun updateProfile(
            @Field("fullname") fullname: String,
            @Field("district_id") districtId: Int?,
    ): Observable<Response<UserResponse>>

    @FormUrlEncoded
    @POST("${NetworkConstants.V1}/covid19/reminder/")
    fun addReminder(
            @Field("center_id") centerId: Int?,
            @Field("district_id") districtId: Int?,
            @Field("date") date: String?,
    ): Observable<Response<GenericResponse>>

}