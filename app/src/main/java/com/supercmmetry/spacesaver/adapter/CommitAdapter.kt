package com.supercmmetry.spacesaver.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.supercmmetry.spacesaver.R
import com.supercmmetry.spacesaver.data.FileFolder

class CommitAdapter(private val fflist : List<FileFolder>) : RecyclerView.Adapter<CommitAdapter.CommitViewHolder>() {

    override fun getItemCount() = fflist.size

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        val txtView = holder.view.findViewById<TextView>(R.id.commit_item_textview)
        txtView.text = fflist[position].name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.commit_item, parent, false)
        return CommitViewHolder(v)
    }

    inner class CommitViewHolder(val view : View) : RecyclerView.ViewHolder(view) {

    }
}