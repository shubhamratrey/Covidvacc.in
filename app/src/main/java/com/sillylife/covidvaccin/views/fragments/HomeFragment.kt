package com.sillylife.covidvaccin.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.sillylife.covidvaccin.R
import com.sillylife.covidvaccin.models.District
import com.sillylife.covidvaccin.models.State
import com.sillylife.covidvaccin.models.responses.LocationResponse
import com.sillylife.covidvaccin.views.module.HomeFragmentModule
import com.sillylife.covidvaccin.views.viewmodal.HomeFragmentViewModel
import com.sillylife.covidvaccin.views.viewmodelfactory.FragmentViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import org.angmarch.views.OnSpinnerItemSelectedListener
import java.util.*


class HomeFragment : BaseFragment(), HomeFragmentModule.APIModuleListener {

    companion object {
        val TAG = HomeFragment::class.java.simpleName
        fun newInstance() = HomeFragment()
    }

    private var viewModel: HomeFragmentViewModel? = null
    private var mState: State? = null
    private var mDistrict: District? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, FragmentViewModelFactory(this@HomeFragment))
                .get(HomeFragmentViewModel::class.java)
        viewModel?.getLocation(-1, -1)
        slotBtn?.setOnClickListener {
            if (mDistrict != null) {
                addFragment(SlotsFragment.newInstance(mDistrict?.slug!!, mDistrict?.name!!), SlotsFragment.TAG)
            } else {
                showToast("Select location", Toast.LENGTH_SHORT)
            }
        }
    }

    override fun onLocationApiSuccess(response: LocationResponse?) {
        if (isAdded && response != null) {
            if (response.states != null && mState == null) {
                val stateNames: ArrayList<String> = ArrayList()
                stateNames.add(0, "Select State")
                response.states?.forEach {
                    stateNames.add(it.name!!)
                }
                stateSpinner.attachDataSource(stateNames)

                stateSpinner.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                districtSpinner.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                districtSpinner.attachDataSource(listOf("Select District"))
                stateSpinner.onSpinnerItemSelectedListener =
                        OnSpinnerItemSelectedListener { parent, view, position, id -> // This example uses String, but your type can be any
                            val item = parent.getItemAtPosition(position) as String
                            for (it in response.states!!) {
                                if (it.name?.contains(item, true) == true) {
                                    mState = it
                                    mDistrict = null
                                    viewModel?.getLocation(mState?.id, -1)
                                    break
                                }
                            }
                        }
            } else if (response.districts != null && mDistrict == null) {
                val districtNames: ArrayList<String> = ArrayList()
                districtNames.add(0, "Select District")
                response.districts?.forEach {
                    districtNames.add(it.name!!)
                }
                districtSpinner.attachDataSource(districtNames)
                districtSpinner.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                districtSpinner.onSpinnerItemSelectedListener =
                        OnSpinnerItemSelectedListener { parent, view, position, id -> // This example uses String, but your type can be any
                            val item = parent.getItemAtPosition(position) as String
                            for (it in response.districts!!) {
                                if (it.name?.contains(item, true) == true) {
                                    mDistrict = it
                                    break
                                }
                            }
                        }
            }
        }
    }

    override fun onApiFailure(statusCode: Int, message: String) {
        if (isAdded) {
            Log.d(TAG, "statusCode $statusCode message $message")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.onDestroy()
    }

}