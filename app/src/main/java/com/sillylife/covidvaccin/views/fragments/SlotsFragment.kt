package com.sillylife.covidvaccin.views.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sillylife.covidvaccin.R
import com.sillylife.covidvaccin.models.Slot
import com.sillylife.covidvaccin.models.SlotFilter
import com.sillylife.covidvaccin.models.responses.SlotsResponse
import com.sillylife.covidvaccin.utils.CommonUtil
import com.sillylife.covidvaccin.views.adapter.FilterAdapter
import com.sillylife.covidvaccin.views.adapter.SlotsAdapter
import com.sillylife.covidvaccin.views.components.DatePickerFragment
import com.sillylife.covidvaccin.views.module.SlotsModule
import com.sillylife.covidvaccin.views.viewmodal.SlotsViewModel
import com.sillylife.covidvaccin.views.viewmodelfactory.FragmentViewModelFactory
import kotlinx.android.synthetic.main.fragment_slots.*
import java.text.SimpleDateFormat
import java.util.*


class SlotsFragment : BaseFragment(), SlotsModule.APIModuleListener {

    companion object {
        val TAG = SlotsFragment::class.java.simpleName
        fun newInstance() = SlotsFragment()
    }

    private var adapter: SlotsAdapter? = null
    private var filterAdapter: FilterAdapter? = null
    private var viewModel: SlotsViewModel? = null
    private var mDate: String = ""
    private var mDistrict: String = "korba"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_slots, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, FragmentViewModelFactory(this@SlotsFragment))
                .get(SlotsViewModel::class.java)
        try {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            mDate = sdf.format(Date())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        viewModel?.getSlots(mDistrict, listOf(), mDate)
        progress?.visibility = View.VISIBLE
        notifyLayout?.visibility = View.GONE

        toolbar?.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
//        viewModel?.getSessions(125, "05-05-2021")
    }

    private fun setAdapter(slots: ArrayList<Slot>) {
        progress?.visibility = View.GONE
        notifyLayout?.visibility = View.GONE
        rcvAll?.visibility = View.VISIBLE
        adapter = SlotsAdapter(context = requireContext(), items = slots,
                object : SlotsAdapter.Listeners {
                    override fun onSlotClicked(slot: Slot, position: Int, view: View?) {

                    }

                    override fun onSendRemindedClicked(slot: Slot, position: Int, view: View?) {

                    }

                    override fun onImpression(slot: Slot, itemRank: Int) {
                        Log.d("IMPRESSION", "Contact - ${slot.center_name} | Source - $TAG")
                    }


                })

        rcvAll?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rcvAll?.adapter = adapter

        val HIDE_THRESHOLD = 100f
        val SHOW_THRESHOLD = 50f
        var scrollDist = 0
        rcvAll?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //  Check scrolled distance against the minimum
                if (searchView.hasFocus() && (scrollDist > HIDE_THRESHOLD) || scrollDist < -SHOW_THRESHOLD) {
                    //  Hide fab & reset scrollDist
//                    searchView.clearFocus()
                    CommonUtil.hideKeyboard(requireContext())
                    scrollDist = 0
                }
                //  Whether we scroll up or down, calculate scroll distance
                if (searchView.hasFocus() && (dy > 0 || dy < 0)) {
                    scrollDist += dy
                }

            }
        })
        setupSearchView()
    }

    private fun setupSearchView() {
        searchView.clearFocus()
        searchView.isFocusable = false
        CommonUtil.hideKeyboard(requireContext())
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            searchView.isFocusedByDefault = false
            CommonUtil.hideKeyboard(requireContext())
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (CommonUtil.textIsNotEmpty(newText)) {
                    if (adapter?.filter != null) {
                        adapter?.filter?.filter(newText)
                    }
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (CommonUtil.textIsNotEmpty(query)) {
                    if (adapter?.filter != null) {
                        adapter?.filter?.filter(query)
                    }
                }
                return false
            }
        })
//        searchView.onActionViewExpanded()
        val textview: TextView = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        val typeface = ResourcesCompat.getFont(activity!!, R.font.eudoxus_sans_light)
        textview.typeface = typeface
        textview.textSize = 16F

        val searchClose: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        searchClose.setColorFilter(Color.parseColor("#BAB5C6"))

        val v: View = searchView.findViewById(androidx.appcompat.R.id.search_plate)
        v.background = null

        val llSubmitArea: LinearLayout = searchView.findViewById(androidx.appcompat.R.id.submit_area)
        llSubmitArea.background = null

        val closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener {
            searchView.setQuery("", false)
            if (adapter != null && adapter?.filter != null) {
                adapter?.filter?.filter("")
                CommonUtil.hideKeyboard(requireContext())
            }
        }

        searchView.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                searchView.setQuery("", false)
                adapter!!.filter.filter("")
                CommonUtil.hideKeyboard(requireContext())
            }
        }
    }

    private fun setFilterRecyclerView(items: ArrayList<SlotFilter>) {
        if (filterAdapter != null) {
            return
        }
        filterAdapter = FilterAdapter(requireContext(), items) { any: Any, i: Int, s: String ->
            if (s == "ValidFilters" && any is SlotFilter) {
                Log.d(filterAdapter?.TAG, "size ${filterAdapter?.getSelectedFilters()}")
                adapter = null
                viewModel?.getSlots(mDistrict, filterAdapter?.getSelectedFilters()!!, mDate)
                progress?.visibility = View.VISIBLE
                notifyLayout?.visibility = View.GONE
            } else if (s == "DateFilter" && any is SlotFilter) {
                showDatePicker()
            }
        }
        filtersRcv?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        filtersRcv?.adapter = filterAdapter
        filtersRcv?.visibility = View.VISIBLE
    }

    override fun onSlotsApiSuccess(response: SlotsResponse?) {
        Log.d(TAG, response.toString())
        if (isAdded) {
            when {
                response?.slots != null && response.slots?.size!! > 0 -> {
                    setAdapter(response.slots!!)
                }
                response?.sessions != null && response.sessions?.size!! > 0 -> {
                    setAdapter(response.sessions!!)
                }
                else -> {
                    notifyLayout?.visibility = View.VISIBLE
                    progress?.visibility = View.GONE
                    rcvAll?.visibility = View.GONE
                }
            }
            response?.filters?.add(0, SlotFilter(type = "date", text = mDate, is_selected = true))
            if (response?.filters != null) {
                setFilterRecyclerView(response.filters!!)
            }
        }
    }

    fun showDatePicker() {
        val dialog = DatePickerFragment.newInstance(object :
                DatePickerFragment.DatePickerFragmentListener {
            override fun onDateSet(calendar: Calendar, meta: Any?) {
                try {
                    val FORMAT = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    mDate = FORMAT.format(calendar.time).toString()
                    adapter = null
                    viewModel?.getSlots(mDistrict, filterAdapter?.getSelectedFilters()!!, mDate)
                    progress?.visibility = View.VISIBLE
                    notifyLayout?.visibility = View.GONE

                    if (filterAdapter != null) {
                        filterAdapter?.notifyDateChange(SlotFilter(type = "date", text = mDate, is_selected = true))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onDismiss() {

            }
        }, mDate)
        activity?.let {
            if (it.isFinishing.not()) {
                dialog.show(childFragmentManager, "DatePickerFragment")
            }
        }
    }

    override fun onApiFailure(statusCode: Int, message: String) {
        if (isAdded) {
            Log.d(TAG, "statusCode $statusCode message $message")
            progress?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.onDestroy()
    }

}