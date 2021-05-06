package com.sillylife.covidvaccin.views.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.sillylife.covidvaccin.R
import com.sillylife.covidvaccin.models.Slot
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_slot.*

class SlotsAdapter(val context: Context,
                   var items: ArrayList<Slot>,
                   val listener: Listeners) : RecyclerView.Adapter<SlotsAdapter.ViewHolder>(), Filterable {
    var commonItemList = ArrayList<Any>()
    private var valueFilter: ValueFilter? = null

    init {
        commonItemList.addAll(items)
        commonItemList.add(FOOTER)
    }

    companion object {
        const val PROGRESS_VIEW = 0
        const val SLOT_ITEEM = 1
        const val FOOTER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            commonItemList[position] is Slot -> {
                SLOT_ITEEM
            }
            commonItemList[position] is Int && commonItemList[position] == FOOTER -> {
                FOOTER
            }
            else -> {
                PROGRESS_VIEW
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = when (viewType) {
            SLOT_ITEEM -> LayoutInflater.from(context).inflate(R.layout.item_slot, parent, false)
            FOOTER -> LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false)
            else -> LayoutInflater.from(context).inflate(R.layout.item_progress, parent, false)
        }
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            SLOT_ITEEM -> {
                val slot = commonItemList[holder.absoluteAdapterPosition] as Slot
                holder.tvPrimary.text = slot.center_name
                holder.tvSecondary.text = "\uD83D\uDCCD ${slot.address}, ${slot.district_name} | ${slot.pincode}"
                holder.tvThird.text = "${slot.min_age_limit}+"
                holder.tvForth.text = "Vaccine: ${slot.vaccine} (${slot.fee_type})"
                holder.tvSlots.text = "${slot.available_capacity}\nSlots"
                //Analytics stuff
                holder.containerView.setOnClickListener {
                    listener.onSlotClicked(slot, position, it)
                }
                listener.onImpression(slot, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return commonItemList.size
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

    inner class ValueFilter : Filter() {
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results!!.values != null) {
                commonItemList = results.values as ArrayList<Any>
                notifyDataSetChanged()
            }
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            if (TextUtils.isEmpty(constraint)) {
                filterResults.count = items.size
                filterResults.values = items
            } else {
                val filterList: ArrayList<Slot> = ArrayList()
                for (item in items) {
                    if (item.address?.contains(constraint!!, ignoreCase = true)!!
                            || (item.center_name?.contains(constraint!!, ignoreCase = true)!!)
                            || (item.state_name?.contains(constraint!!, ignoreCase = true)!!)
                            || (item.pincode?.contains(constraint!!, ignoreCase = true)!!)
                    ) {
                        filterList.add(item)
                    }
                }
                filterResults.count = filterList.size
                filterResults.values = filterList
            }
            return filterResults
        }
    }

    override fun getFilter(): Filter {
        if (valueFilter == null) {
            valueFilter = ValueFilter()
        }
        return valueFilter!!
    }

    interface Listeners {
        fun onSlotClicked(slot: Slot, position: Int, view: View?)
        fun onSendRemindedClicked(slot: Slot, position: Int, view: View?)
        fun onImpression(slot: Slot, itemRank: Int)
    }
}