package com.sanke.ilafedoseev.statisticapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListAdapter(context: Context,al_flower:ArrayList<Like>) : BaseAdapter(){

    private val mInflator: LayoutInflater
    private val al_flower:ArrayList<Like>

    init {
        this.mInflator = LayoutInflater.from(context)
        this.al_flower=al_flower
    }

    override fun getItem(position: Int): Any {
        return al_flower.get(position)
    }

    override fun getCount(): Int {
        return al_flower.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val vh: ListRowHolder
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.item, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        vh.label.text = al_flower.get(position).value.toString()
        vh.tv_des.text = al_flower.get(position).date
        return view
    }
}

private class ListRowHolder(row: View?) {
    public val label: TextView
    public val tv_des: TextView

    init {
        this.label = row?.findViewById<TextView>(R.id.result) as TextView
        this.tv_des = row?.findViewById<TextView>(R.id.date) as TextView
    }
}