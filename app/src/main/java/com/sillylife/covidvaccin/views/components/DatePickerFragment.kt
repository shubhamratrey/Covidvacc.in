package com.sillylife.covidvaccin.views.components

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.sillylife.covidvaccin.R
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    interface DatePickerFragmentListener {
        fun onDateSet(calendar: Calendar, meta: Any? = null)
        fun onDismiss() {}
    }

    companion object {

        fun newInstance(listener: DatePickerFragmentListener, date: String? = null): DatePickerFragment {
            var fragment = DatePickerFragment()
            fragment.setDatePickerListener(listener)
            var bundle = Bundle()
            bundle.putInt("type", PICKER_DOB)
            if (date?.isNotEmpty() == true) {
                bundle.putString("date", date)
            }
            fragment.arguments = bundle
            return fragment
        }

        private const val PICKER_DOB = 0
        private const val PICKER_EPISODE = 1
    }

    var pickerType: Int = 0
    var listener: DatePickerFragmentListener? = null
    var calendar: Calendar = Calendar.getInstance()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var datePickerDialog = DatePickerDialog(requireActivity(), R.style.DatePicker, this, year, month, day)

        if (arguments != null) {
            if (arguments?.containsKey("type")!!) {
                pickerType = arguments?.getInt("type", 0)!!
            }

            if (arguments?.containsKey("date") == true) {
                val dateStr = arguments?.getString("date")
                if (!dateStr.isNullOrEmpty()) {
                    try {
                        val date = SimpleDateFormat("dd-MM-yyyy").parse(dateStr)
                        c.time = date
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            if (pickerType == PICKER_DOB && activity != null) {
                datePickerDialog = setCalendarForVaccine(c)
            }
        }

        return datePickerDialog
    }

    override fun onDestroy() {
        super.onDestroy()
        listener?.onDismiss()
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        if (listener != null) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            calendar.set(Calendar.YEAR, year)
            when (pickerType) {
                PICKER_DOB -> {
                    listener?.onDateSet(calendar)
                }
            }
        }
    }

    fun setCalendarForVaccine(c: Calendar): DatePickerDialog {
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(requireActivity(), R.style.DatePicker, this, year, month, day)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
//        val twoHundredDays: Long = 90 * 24 * 60 * 60 * 1000
//        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() + twoHundredDays
        return datePickerDialog
    }

    fun setDatePickerListener(listener: DatePickerFragmentListener) {
        this.listener = listener
    }
}