package com.carcassonneteam.gloomhavenhelper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.carcassonneteam.gloomhavenhelper.R
import com.carcassonneteam.gloomhavenhelper.models.Item

class ItemJSONAdapter(context: Context?, arrayList: ArrayList<Item>?): BaseAdapter() {
    var context: Context? = null
    var arrayList: ArrayList<Item>? = null

    init {
        this.context = context
        this.arrayList = arrayList
    }

    override fun getCount(): Int {
        return arrayList!!.size
    }

    override fun getItem(position: Int): Any? {
        return arrayList!![position]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var newConvertView = convertView
        if (newConvertView == null) {
            newConvertView = LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false)
        }
        val name: TextView = newConvertView!!.findViewById(R.id.name_item)
        name.text = arrayList!![position].getName()
        return newConvertView
    }
}