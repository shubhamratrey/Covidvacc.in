package com.sillylife.covidvaccin.views.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sillylife.covidvaccin.views.fragments.BaseFragment
import com.sillylife.covidvaccin.views.viewmodal.HomeFragmentViewModel
import com.sillylife.covidvaccin.views.viewmodal.LoginFragmentViewModel
import com.sillylife.covidvaccin.views.viewmodal.SlotsViewModel

class FragmentViewModelFactory(private val fragment: BaseFragment) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(HomeFragmentViewModel::class.java) -> return HomeFragmentViewModel(fragment) as T
            modelClass.isAssignableFrom(LoginFragmentViewModel::class.java) -> return LoginFragmentViewModel(fragment) as T
            modelClass.isAssignableFrom(SlotsViewModel::class.java) -> return SlotsViewModel(fragment) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}