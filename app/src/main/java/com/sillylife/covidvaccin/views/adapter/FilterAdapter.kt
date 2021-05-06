package com.sillylife.covidvaccin.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sillylife.covidvaccin.R
import com.sillylife.covidvaccin.models.SlotFilter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_filter.*

class FilterAdapter(val context: Context, val list: ArrayList<SlotFilter>, val listener: (Any, Int, String) -> Unit) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    var TAG = FilterAdapter::class.java.simpleName
    private var selectedFilters: MutableList<String> = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_filter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[holder.absoluteAdapterPosition]
        holder.filterTv.text = item.text

        holder.containerView.setOnClickListener {
            if (item.type != "date") {
                if (holder.filterTv.isSelected) {
                    holder.filterTv.isSelected = false
                    holder.filterTv.setTextColor(ContextCompat.getColor(context, R.color.river_bed_40))
                    holder.filterTv.setBackgroundResource(R.drawable.ic_capsule_filter_unselected)
                    selectedFilters.remove(item.value!!)
                    notifyItemChanged(holder.absoluteAdapterPosition)
                } else {
                    holder.filterTv.isSelected = true
                    holder.filterTv.setTextColor(ContextCompat.getColor(context, R.color.chambray))
                    holder.filterTv.setBackgroundResource(R.drawable.ic_capsule_filter_selected)
                    selectedFilters.add(item.value!!)
                    notifyItemChanged(holder.absoluteAdapterPosition)
                }
                listener(item, holder.absoluteAdapterPosition, "ValidFilters")
            } else {
                listener(item, holder.absoluteAdapterPosition, "DateFilter")
            }

        }

        if (selectedFilters.contains(item.value)) {
            holder.filterTv.isSelected = true
            holder.filterTv.setTextColor(ContextCompat.getColor(context, R.color.chambray))
            holder.filterTv.setBackgroundResource(R.drawable.ic_capsule_filter_selected)
        } else {
            holder.filterTv.isSelected = false
            holder.filterTv.setTextColor(ContextCompat.getColor(context, R.color.river_bed_40))
            holder.filterTv.setBackgroundResource(R.drawable.ic_capsule_filter_unselected)
        }

        if (item.is_selected != null && item.is_selected!!) {
            holder.filterTv.isSelected = true
            if (item.type != "date") {
                selectedFilters.add(item.value!!)
            }
            holder.filterTv.setTextColor(ContextCompat.getColor(context, R.color.chambray))
            holder.filterTv.setBackgroundResource(R.drawable.ic_capsule_filter_selected)
        }
    }

    fun getSelectedFilters(): List<String> {
        return selectedFilters
    }

    fun notifyDateChange(item: SlotFilter) {
        list[0] = item
        notifyItemChanged(0)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}