package com.supercmmetry.spacesaver.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.supercmmetry.spacesaver.R
import com.supercmmetry.spacesaver.data.Analytics
import kotlinx.android.synthetic.main.analytics_item.view.*
import kotlin.math.roundToInt



class AnalyticsAdapter(private val listener : OnItemClickListener) : RecyclerView.Adapter<AnalyticsAdapter.AnalyticsViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(analytics: Analytics)
    }

    private var data = listOf<Analytics>()

    fun update(analyticsList: List<Analytics>) {
        data = analyticsList
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AnalyticsViewHolder, position: Int) {
        val elem = data[position]

        holder.itemView.analytics_item_textview.text = elem.storageVolumeName
        holder.itemView.analytics_item_progressbar.progress = elem.percentageUsedSpace
        holder.itemView.analytics_item_progresstextview.text =
            elem.percentageUsedSpace.toString() + "%"

        val usedMb = elem.usedBytes / 1048576
        val totalMb = elem.totalBytes / 1048576

        val usedTxt = if (usedMb > 1024) {
            (usedMb.toDouble() / 1024).roundToInt().toString() + " GB"
        } else {
            "$usedMb MB"
        }

        val totalTxt = if (totalMb > 1024) {
            (totalMb.toDouble() / 1024).roundToInt().toString() + " GB"
        } else {
            "$totalMb MB"
        }


        holder.view.analytics_item_freespace_textview.text =
            "${usedTxt} used out of ${totalTxt}"

        holder.bind(elem, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalyticsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.analytics_item, parent, false)
        return AnalyticsViewHolder(v)
    }

    inner class AnalyticsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item : Analytics, listener : OnItemClickListener) {
            view.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}