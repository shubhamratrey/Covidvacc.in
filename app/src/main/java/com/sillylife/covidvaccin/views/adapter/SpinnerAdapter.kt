package com.sillylife.covidvaccin.views.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.sillylife.covidvaccin.R
import com.sillylife.covidvaccin.models.District
import com.sillylife.covidvaccin.models.State

class SpinnerAdapter(val context: Context, var items: List<Any>, val anyObject: Any) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val holder: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_spinner_location, parent, false)
            holder = ItemHolder(view)
            view?.tag = holder
        } else {
            view = convertView
            holder = view.tag as ItemHolder
        }
        holder.label.setTextColor(Color.BLACK);
        val item = items[position]
        holder.label.text = when (item) {
            is State -> {
                item.name
            }
            is District -> {
                item.name
            }
            else -> {
                if (anyObject is String) {
                    anyObject
                } else {
                    "Select from here"
                }
            }
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return items[position];
    }

    override fun getCount(): Int {
        return items.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val label: TextView = row?.findViewById(R.id.text) as TextView
    }

}