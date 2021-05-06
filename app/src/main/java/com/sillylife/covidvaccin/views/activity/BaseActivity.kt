package com.sillylife.covidvaccin.views.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sillylife.covidvaccin.R
import com.sillylife.covidvaccin.constants.Constants
import com.sillylife.covidvaccin.events.RxBus
import com.sillylife.covidvaccin.events.RxEvent
import com.sillylife.covidvaccin.utils.FragmentHelper


@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    fun showToast(message: String?, length: Int? = 0) {
        if (message != null && !isFinishing) {
            Toast.makeText(this, message, length!!).show()
        }
    }

    fun addFragment(fragment: Fragment, tag: String? = null) {
        FragmentHelper.add(R.id.container, supportFragmentManager, fragment, tag!!)
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        FragmentHelper.replace(R.id.container, supportFragmentManager, fragment, tag)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.GALLERY || requestCode == Constants.AUDIO_LIBRARY) {
            RxBus.publish(RxEvent.ActivityResult(requestCode, resultCode, data))
            return
        }
    }
}