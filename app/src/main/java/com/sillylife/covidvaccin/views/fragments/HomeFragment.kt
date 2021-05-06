package com.sillylife.covidvaccin.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.sillylife.covidvaccin.R
import com.sillylife.covidvaccin.models.responses.LocationResponse
import com.sillylife.covidvaccin.views.module.HomeFragmentModule
import com.sillylife.covidvaccin.views.viewmodal.HomeFragmentViewModel
import com.sillylife.covidvaccin.views.viewmodelfactory.FragmentViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class HomeFragment : BaseFragment(), HomeFragmentModule.APIModuleListener {

    companion object {
        val TAG = HomeFragment::class.java.simpleName
        fun newInstance() = HomeFragment()
    }

    private var viewModel: HomeFragmentViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, FragmentViewModelFactory(this@HomeFragment))
                .get(HomeFragmentViewModel::class.java)
        viewModel?.getLocation("", "")
        slotBtn?.setOnClickListener {
            addFragment(SlotsFragment.newInstance(), SlotsFragment.TAG)
        }
    }

    override fun onLocationApiSuccess(response: LocationResponse?) {
        Log.d(TAG, response.toString())
    }

    override fun onApiFailure(statusCode: Int, message: String) {
        Log.d(TAG, "statusCode $statusCode message $message")

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.onDestroy()
    }

}