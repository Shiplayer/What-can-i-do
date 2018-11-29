package com.developer.ship.whatcanido

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.developer.ship.whatcanido.entity.ExampleDescription
import java.text.SimpleDateFormat

/**
 * Created by Shiplayer on 29.11.18.
 */

class ExampleItemAdapter : RecyclerView.Adapter<ExampleItemAdapter.ViewHolder>() {
    private var mDataList: List<ExampleDescription> = listOf()

    public fun setData(dataList: List<ExampleDescription>){
        mDataList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.example_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleView.text = mDataList[position].title
        holder.descriptionView.text = mDataList[position].description
        holder.dateView.text = SimpleDateFormat("dd.MM.yy").format(mDataList[position].date)
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleView= view.findViewById<TextView>(R.id.item_title)
        val descriptionView = view.findViewById<TextView>(R.id.item_description)
        val dateView = view.findViewById<TextView>(R.id.item_date)

    }
}