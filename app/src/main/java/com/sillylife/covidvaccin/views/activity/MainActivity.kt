package com.sillylife.covidvaccin.views.activity

import android.os.Bundle
import com.sillylife.covidvaccin.R
import com.sillylife.covidvaccin.views.fragments.HomeFragment


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        replaceFragment(HomeFragment.newInstance(), HomeFragment.TAG)
    }

//    override fun onBackPressed() {
//        val profileFragment = supportFragmentManager.findFragmentByTag(ProfileFragment.TAG)
//        val loginFragment = supportFragmentManager.findFragmentByTag(LoginFragment.TAG)
//        if (profileFragment != null) {
//            if ((profileFragment as ProfileFragment).onBackPressed()) {
//                super.onBackPressed()
//            }
//        } else if (loginFragment != null) {
//            if ((loginFragment as LoginFragment).onBackPressed()) {
//                super.onBackPressed()
//            }
//        } else {
//            super.onBackPressed()
//        }
//    }
}