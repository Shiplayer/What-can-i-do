package com.developer.ship.whatcanido

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.developer.ship.whatcanido.entity.ExampleDescription
import com.developer.ship.whatcanido.utils.Utilities
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat

/**
 * Created by Shiplayer on 29.11.18.
 */

class ExampleItemAdapter() : RecyclerView.Adapter<ExampleItemAdapter.ViewHolder>() {
    private var mDataList: List<ExampleDescription> = listOf()
    private lateinit var mErrorHandler: IErrorHandler

    constructor(errorHandler: IErrorHandler) : this(){
        mErrorHandler = errorHandler
    }

    public fun setData(dataList: List<ExampleDescription>){
        mDataList = dataList
        notifyDataSetChanged()
        Log.i("ExampleItemAdapter", "notify data set changed (size = " + dataList.size)
    }

    override fun getItemViewType(position: Int): Int {
        Log.i("ExampleItemAdapter", "position: $position")
        return position % 2 * 2
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i("ExampleItemAdapter", "view type is $viewType")
        val view = if(viewType == 0) {
            LayoutInflater.from(parent.context).inflate(R.layout.example_item, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.example_item_odd, parent, false)
        }

        return ViewHolder(view, viewType)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Utilities.getImageByURL(mDataList[position].url)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{
                holder.itemProgressBar.visibility = View.VISIBLE
                holder.itemImageView.visibility = View.GONE
            }
            .doFinally {
                holder.itemProgressBar.visibility = View.GONE
                holder.itemImageView.visibility = View.VISIBLE
            }
            .subscribe({logoImage ->
                holder.itemImageView.setImageDrawable(logoImage)
            }, {error ->
                mErrorHandler.showError(error)
            })
        holder.titleView.text = mDataList[position].title
        holder.descriptionView.text = mDataList[position].description
        holder.dateView.text = SimpleDateFormat("dd.MM.yy").format(mDataList[position].date)

    }


    class ViewHolder(val view: View, val type:Int) : RecyclerView.ViewHolder(view) {
        val titleView = view.findViewById<TextView>(if(type == 0) R.id.item_title else R.id.item_title_odd)
        val descriptionView = view.findViewById<TextView>(if(type == 0) R.id.item_description else R.id.item_description_odd)
        val dateView = view.findViewById<TextView>(if(type == 0) R.id.item_date else R.id.item_date_odd)
        val itemProgressBar = view.findViewById<ProgressBar>(if(type == 0) R.id.item_image_pb else R.id.item_image_pb_odd)
        val itemImageView = view.findViewById<ImageView>(if(type == 0) R.id.item_image else R.id.item_image_odd)

    }
}