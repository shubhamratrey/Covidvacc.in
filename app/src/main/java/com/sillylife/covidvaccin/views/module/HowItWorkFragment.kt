package com.sillylife.covidvaccin.views.module

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import com.sillylife.covidvaccin.R
import com.sillylife.covidvaccin.views.activity.LoginActivity
import com.sillylife.covidvaccin.views.fragments.BaseFragment
import com.sillylife.covidvaccin.views.fragments.HomeFragment
import kotlinx.android.synthetic.main.bottom_btn_layout.*
import kotlinx.android.synthetic.main.fragment_how_it_works.*


class HowItWorkFragment : BaseFragment() {

    companion object {
        val TAG = HowItWorkFragment::class.java.simpleName
        fun newInstance() = HowItWorkFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_how_it_works, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        htwTv1?.text = SpannableStringBuilder().bold { append("Login as child ") }.append("on your child phone")
        htwTv2?.text = SpannableStringBuilder().append("Type your ").bold { append("unique code") }
        htwTv3?.text = SpannableStringBuilder().bold { append("Select ") }.append("productive & unproductive apps")
        htwTv4?.text = SpannableStringBuilder().bold { append("vallah! ") }.append("Check your phone to moniter your child activities")
        htwTv5?.text = SpannableStringBuilder().append("Find your ").bold { append("unique code ") }.append("later in ").bold { append("profile") }

        leftBtn?.visibility = View.VISIBLE
        rightBtn?.visibility = View.VISIBLE
        rightBtn?.text = getString(R.string.dashboard)
        leftBtn?.text = getString(R.string.back)

        leftBtn?.setOnClickListener {
            if (activity is LoginActivity) {
                (activity as LoginActivity).onBackPressed()
            }
        }

        rightBtn?.setOnClickListener {
            if (activity is LoginActivity) {
                addFragment(HomeFragment.newInstance(), HomeFragment.TAG)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}