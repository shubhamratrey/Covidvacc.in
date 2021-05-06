package com.sillylife.covidvaccin.views.module

import android.app.Activity
import com.google.firebase.auth.PhoneAuthCredential
import com.sillylife.covidvaccin.managers.AuthManager
import com.sillylife.covidvaccin.models.UserProfile
import com.sillylife.covidvaccin.models.responses.UserResponse
import com.sillylife.covidvaccin.services.CallbackWrapper
import com.sillylife.covidvaccin.services.sharedpreference.SharedPreferenceManager
import com.sillylife.covidvaccin.utils.PhoneNumberUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class LoginFragmentModule(val iModuleListener: IModuleListener) : BaseModule() {

    fun getMe() {
        val hashMap = HashMap<String, String>()
        appDisposable.add(application.getAPIService()
                .getMe(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackWrapper<Response<UserResponse>>() {
                    override fun onSuccess(t: Response<UserResponse>) {
                        if (t.isSuccessful && t.body() != null && t.body()?.user != null) {
                            val user: UserProfile? = t.body()?.user
                            SharedPreferenceManager.setUser(user!!)
                            iModuleListener.onGetMeApiSuccess(t.body()!!)
                        } else {
                            iModuleListener.onApiFailure(t.code(), "empty body")
                        }
                    }

                    override fun onFailure(code: Int, message: String) {
                        iModuleListener.onApiFailure(code, message)
                    }
                })
        )
    }

    fun updateProfile(fullname: String, districtId: Int?) {
        appDisposable.add(apiService
                .updateProfile(fullname, districtId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackWrapper<Response<UserResponse>>() {
                    override fun onSuccess(t: Response<UserResponse>) {
                        if (t.body() != null) {
                            SharedPreferenceManager.setUser(t.body()?.user!!)
                            iModuleListener.onUpdateProfileApiSuccess(t.body()!!)
                        } else {
                            iModuleListener.onApiFailure(t.code(), t.message())
                        }
                    }

                    override fun onFailure(code: Int, message: String) {
                        iModuleListener.onApiFailure(code, message)
                    }
                })
        )
    }

    fun signInWithPhone(phoneNumber: String, countryCode: String, activity: Activity) {
        val formattedNumber = PhoneNumberUtils.getPseudoValidPhoneNumber(phoneNumber, countryCode)
        AuthManager.signInWithPhone(
                formattedNumber!!,
                object : AuthManager.IAuthCredentialCallback {
                    override fun onAuthCompleted(isNewUser: Boolean) {
                        iModuleListener.onPhoneAuthCompleted()
                    }

                    override fun onAuthError(error: String, smsAutoDetect: Boolean) {
                        iModuleListener.onAuthError(error)
                    }

                    override fun onCodeSent(verificationId: String) {
                        iModuleListener.onCodeSent(verificationId)
                    }

                    override fun onAccountExists() {
                        iModuleListener.onAccountExists()
                    }
                }, activity)
    }

    /*fun loginAnonymously() {
        AuthManager.signInAnonymously(object : AuthManager.IAuthCredentialAnonymouslyLoginCallback {
            override fun onSignInAnonymously() {
                iModuleListener.onAnonymouslyAuthCompleted()
            }
        })
    }*/

    fun submitCode(credential: PhoneAuthCredential, mobile: String) {
        AuthManager.signInWithPhoneCredential(
                credential,
                object : AuthManager.IAuthCredentialCallback {
                    override fun onAuthCompleted(isNewUser: Boolean) {
                        iModuleListener.onPhoneAuthCompleted()
                    }

                    override fun onAuthError(error: String, smsAutoDetect: Boolean) {
                        iModuleListener.onAuthError(error)
                    }

                    override fun onCodeSent(verificationId: String) {
                        iModuleListener.onCodeSent(verificationId)
                    }

                    override fun onAccountExists() {
                        iModuleListener.onAccountExists()
                    }
                }, mobile, false)
    }

    fun resendCode(phoneNumber: String, activity: Activity) {
        AuthManager.signInWithPhone(
                phoneNumber,
                object : AuthManager.IAuthCredentialCallback {
                    override fun onAuthCompleted(isNewUser: Boolean) {
                        iModuleListener.onPhoneAuthCompleted()
                    }

                    override fun onAuthError(error: String, smsAutoDetect: Boolean) {
                        iModuleListener.onAuthError(error)
                    }

                    override fun onCodeSent(verificationId: String) {
                        iModuleListener.onCodeSent(verificationId)
                    }

                    override fun onAccountExists() {
                        iModuleListener.onAccountExists()
                    }
                }, activity)
    }

    interface IModuleListener {
        fun onGetMeApiSuccess(response: UserResponse)
        fun onApiFailure(statusCode: Int, message: String)
        fun onUpdateProfileApiSuccess(response: UserResponse)
        fun onPhoneAuthCompleted()
        fun onAuthError(error: String)
        fun onCodeSent(verificationId: String)
        fun onAccountExists()
    }
}