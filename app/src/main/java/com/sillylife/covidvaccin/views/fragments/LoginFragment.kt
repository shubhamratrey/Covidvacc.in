package com.sillylife.covidvaccin.views.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.bold
import androidx.core.text.color
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.PhoneAuthProvider
import com.sillylife.covidvaccin.R
import com.sillylife.covidvaccin.constants.BundleConstants
import com.sillylife.covidvaccin.models.responses.UserResponse
import com.sillylife.covidvaccin.services.AppDisposable
import com.sillylife.covidvaccin.utils.CommonUtil
import com.sillylife.covidvaccin.utils.PhoneNumberUtils
import com.sillylife.covidvaccin.utils.TextViewLinkHandler
import com.sillylife.covidvaccin.views.activity.LoginActivity
import com.sillylife.covidvaccin.views.activity.MainActivity
import com.sillylife.covidvaccin.views.activity.WebViewActivity
import com.sillylife.covidvaccin.views.module.LoginFragmentModule
import com.sillylife.covidvaccin.views.viewmodal.LoginFragmentViewModel
import com.sillylife.covidvaccin.views.viewmodelfactory.FragmentViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*


class LoginFragment : BaseFragment(), LoginFragmentModule.IModuleListener {

    companion object {
        val TAG = LoginFragment::class.java.simpleName
        fun newInstance() = LoginFragment()
    }


    private val mPhoneLayout: String = "phone_layout"
    private val mOtpLayout: String = "otp_layout"
    private var mType: String = mPhoneLayout

    private var appDisposable: AppDisposable = AppDisposable()
    private val mCountryCode = "+91-"
    private var mPhoneNumber: String? = null
    private var viewModel: LoginFragmentViewModel? = null
    private var mVerificationId: String? = null

    private var otpTimer: Timer? = null
    private var otpTimerValue = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, FragmentViewModelFactory(this@LoginFragment)).get(LoginFragmentViewModel::class.java)
        setViews()
    }

    private fun setViews() {
        togglePhoneNumberError(false)
        toggleOtpError(false)
        when (mType) {
            mPhoneLayout -> {
                enableButton(true)
                otp_layout?.visibility = View.GONE
                otpBtn?.visibility = View.VISIBLE
                etPhoneNumber?.addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                    }

                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                    }

                    override fun afterTextChanged(s: Editable) {
                        togglePhoneNumberError(false)
                        mPhoneNumber = s.toString()
                    }
                })
            }
            mOtpLayout -> {
                otp_layout?.visibility = View.VISIBLE
                otpBtn?.visibility = View.GONE

                otpView?.setText("")
                otpView?.isFocusableInTouchMode = true
                otpView?.setAnimationEnable(true)
                otpView?.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        toggleOtpError(false)
                        val len = s?.length ?: 0
                        Log.d("onTextChanged", "---")
                        if (len == 6) {
                            if (mType == mOtpLayout) {
                                val code = otpView.text.toString()
                                val credential = PhoneAuthProvider.getCredential(if (mVerificationId != null) mVerificationId!! else "", code)
                                CommonUtil.hideKeyboard(context!!)
                                viewModel?.submitCode(credential, PhoneNumberUtils.getPseudoValidPhoneNumber(mPhoneNumber!!, "91")!!)
                            }
                            Log.d("onTextChanged", "++++")
                            otpView?.setTextColor(Color.parseColor("#DD0D4B"))
                        } else {
                            otpView?.setTextColor(Color.parseColor("#001A28"))
                        }
                    }
                })

            }
        }

        otpBtn?.setOnClickListener {
            if (validate()) {
                enableButton(false)
                when (mType) {
                    mPhoneLayout -> {
                        mType = mOtpLayout
                        if (activity != null && CommonUtil.textIsNotEmpty(mPhoneNumber)) {
                            viewModel?.signInWithPhone(phoneNumber = mPhoneNumber!!, "91", requireActivity())
                        }
                    }
                    mOtpLayout -> {
                        val code = otpView.text.toString()
                        val credential = PhoneAuthProvider.getCredential(if (mVerificationId != null) mVerificationId!! else "", code)
                        CommonUtil.hideKeyboard(requireContext())
                        viewModel?.submitCode(credential, PhoneNumberUtils.getPseudoValidPhoneNumber(mPhoneNumber!!, "91")!!)
                    }
                }
            }
        }

        resendTv?.setOnClickListener {
            val phoneNumber = "+$mCountryCode$mPhoneNumber"
            if (activity != null) {
                viewModel?.resendCode(phoneNumber, requireActivity())
                startTimer()
                setViews()
            }
        }

        tcPrivacyTv?.movementMethod = LinkMovementMethod.getInstance()
        tcPrivacyTv?.text = HtmlCompat.fromHtml(resources.getString(R.string.t_c_and_privacy_policy), HtmlCompat.FROM_HTML_MODE_LEGACY)
        try {
            tcPrivacyTv?.movementMethod = object : TextViewLinkHandler() {
                override fun onLinkClick(url: String?) {
                    if (url != null) {
                        if (url.contains("privacy-policy")) {
                            startActivity(Intent(requireContext(), WebViewActivity::class.java).putExtra(BundleConstants.WEB_URL, url))
                        } else if (url.contains("terms-condition")) {
                            startActivity(Intent(requireContext(), WebViewActivity::class.java).putExtra(BundleConstants.WEB_URL, url))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        when (mType) {
            mPhoneLayout -> {
                if (CommonUtil.textIsEmpty(etPhoneNumber?.text.toString()) || etPhoneNumber.text.toString().length < 9) {
                    togglePhoneNumberError(true)
                    isValid = false
                }
            }
            mOtpLayout -> {
                if (mVerificationId != null && CommonUtil.textIsEmpty(otpView?.text.toString()) || otpView.text.toString().length < 6) {
                    toggleOtpError(true)
                    isValid = false
                }
            }
        }
        return isValid
    }

    fun togglePhoneNumberError(isVisible: Boolean) {
        tvPhoneNumberError?.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        tvOtpError?.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    fun toggleOtpError(isVisible: Boolean) {
        tvOtpError?.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        otpView?.setLineColor(ContextCompat.getColor(requireContext(), if (isVisible) R.color.error_text else R.color.grey_phone_bg_stroke))
    }

    fun togglePhoneNumberIssueIcon(isVisible: Boolean) {

    }


    fun toggleResendBtn(isVisible: Boolean) {
        timerTv?.visibility = if (isVisible) View.GONE else View.VISIBLE
        resendTv?.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun startTimer() {
        stopTimer()
        toggleResendBtn(false)
        otpTimer = Timer()
        otpTimer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (isAdded && isVisible && activity != null) {
                    requireActivity().runOnUiThread {

                        otpTimerValue++
                        if (otpTimerValue > (otpProgress?.max ?: 30)) {
                            timerTv?.text = SpannableStringBuilder().append("Getting OTP  ").bold { color(ContextCompat.getColor(requireContext(), R.color.black)) { append("${otpTimerValue}S") } }
                            stopTimer()
                        } else {
                            otpProgress?.progress = otpTimerValue
                            timerTv?.text = SpannableStringBuilder().append("Getting OTP  ").bold { color(ContextCompat.getColor(requireContext(), R.color.black)) { append("${(otpProgress?.max ?: 30) - otpTimerValue}S") } }
                        }
                    }
                }
            }
        }, 1000, 1000)
    }

    private fun enableButton(isEnabled: Boolean) {
        proceedProgress.visibility = if (isEnabled) View.GONE else View.VISIBLE
        otpBtn.isEnabled = isEnabled
        when (mType) {
            mPhoneLayout -> {
                otpBtn.text = if (isEnabled) requireContext().getString(R.string.get_otp) else ""
            }
        }
    }

    private fun stopTimer() {
        if (isAdded && isVisible) {
            toggleResendBtn(true)
            otpTimer?.cancel()
            otpTimer?.purge()
            otpTimer = null
            otpTimerValue = 0
            otpProgress?.progress = otpTimerValue
            timerTv?.text = SpannableStringBuilder().append("Getting OTP  ").bold { color(ContextCompat.getColor(requireContext(), R.color.black)) { append("${(otpProgress?.max ?: 30) - otpTimerValue}S") } }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appDisposable.dispose()
        viewModel?.onDestroy()
    }

    override fun onGetMeApiSuccess(response: UserResponse) {
        if (isAdded) {
            if (requireActivity() is LoginActivity) {
                stopTimer()
                startMainActivity()
                Log.d(TAG, "onGetMeApiSuccess")
            }
        }
    }

    override fun onApiFailure(statusCode: Int, message: String) {
        if (statusCode == 404 && message == "User Not Found.") {
            stopTimer()
            viewModel?.updateProfile("", null)
        }
    }

    override fun onUpdateProfileApiSuccess(response: UserResponse) {
        if (activity != null && isAdded) {
            startMainActivity()
            Log.d(TAG, "onUpdateProfileApiSuccess")
        }
    }

    override fun onPhoneAuthCompleted() {
        if (activity != null && isAdded) {
            viewModel?.getMe()
            Log.d(TAG, "onPhoneAuthCompleted")
        }
    }

    override fun onAuthError(error: String) {
        if (activity != null && isAdded) {
            when (mType) {
                mPhoneLayout -> {
                    enableButton(true)
                }
                mOtpLayout -> {
                    toggleOtpError(true)
                }
            }
            Log.d(TAG, "onPhoneAuthCompleted")
        }
    }

    override fun onCodeSent(verificationId: String) {
        if (activity != null && isAdded) {
            this.mVerificationId = verificationId
            startTimer()
            setViews()
            Log.d(TAG, "onCodeSent")
        }
    }

    override fun onAccountExists() {

    }

    private fun startMainActivity() {
        if (requireActivity() is LoginActivity) {
            requireActivity().startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }
}
