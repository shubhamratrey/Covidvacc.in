package com.sillylife.covidvaccin.views.activity

import android.content.Intent
import android.os.Bundle
import com.sillylife.covidvaccin.R
import com.sillylife.covidvaccin.managers.FirebaseAuthUserManager
import com.sillylife.covidvaccin.views.fragments.LoginFragment


class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        if (!FirebaseAuthUserManager.isUserLoggedIn()) {
            replaceFragment(LoginFragment.newInstance(), LoginFragment.TAG)
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}