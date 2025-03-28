package com.example.onlinedatajson

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class MyAdapter(var context: Context, var list:MutableList<Model>): BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var layout = LayoutInflater.from(context)
        var view = layout.inflate(R.layout.design, parent, false)
        var txt1: TextView = view.findViewById(R.id.txt)
        var img: ImageView = view.findViewById(R.id.img)

        txt1.setText(list.get(position).name)
        Glide
            .with(context)
            .load(list.get(position).imageurl)
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(img)

        return view
    }

}