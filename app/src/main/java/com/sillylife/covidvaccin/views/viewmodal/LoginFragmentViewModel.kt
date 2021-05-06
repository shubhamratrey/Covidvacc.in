package com.sillylife.covidvaccin.views.viewmodal

import android.app.Activity
import com.google.firebase.auth.PhoneAuthCredential
import com.sillylife.covidvaccin.models.responses.UserResponse
import com.sillylife.covidvaccin.views.fragments.BaseFragment
import com.sillylife.covidvaccin.views.module.BaseModule
import com.sillylife.covidvaccin.views.module.LoginFragmentModule

class LoginFragmentViewModel(fragment: BaseFragment) : BaseViewModel(), LoginFragmentModule.IModuleListener {

    override fun onPhoneAuthCompleted() {
        viewListener.onPhoneAuthCompleted()
    }

    override fun onAuthError(error: String) {
        viewListener.onAuthError(error)
    }

    override fun onCodeSent(verificationId: String) {
        viewListener.onCodeSent(verificationId)
    }

    override fun onAccountExists() {
        viewListener.onAccountExists()
    }

    override fun onGetMeApiSuccess(response: UserResponse) {
        viewListener.onGetMeApiSuccess(response)
    }

    override fun onApiFailure(statusCode: Int, message: String) {
        viewListener.onApiFailure(statusCode, message)
    }

    override fun onUpdateProfileApiSuccess(response: UserResponse) {
        viewListener.onUpdateProfileApiSuccess(response)
    }

    val module = LoginFragmentModule(this)
    val viewListener = fragment as LoginFragmentModule.IModuleListener

    override fun setViewModel(): BaseModule {
        return module
    }

    fun getMe() {
        module.getMe()
    }

    fun updateProfile(fullname: String, districtId: Int?) {
        module.updateProfile(fullname, districtId)
    }

    /*fun loginAnonymously() {
        module.loginAnonymously()
    }*/

    fun signInWithPhone(phoneNumber: String, countryCode: String, activity: Activity) {
        module.signInWithPhone(phoneNumber, countryCode, activity)
    }

    fun submitCode(credential: PhoneAuthCredential, mobile: String) {
        module.submitCode(credential, mobile)
    }

    fun resendCode(phoneNumber: String, activity: Activity) {
        module.resendCode(phoneNumber, activity)
    }

}
