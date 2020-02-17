package com.supercmmetry.spacesaver.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.supercmmetry.spacesaver.R
import com.supercmmetry.spacesaver.data.FileFolder
import com.supercmmetry.spacesaver.ui.deletion.DeletionActivity
import com.supercmmetry.spacesaver.ui.explorer.FileFolderViewModel
import kotlinx.android.synthetic.main.explorer_item.view.*

class ExplorerAdapter(private val listener: OnItemClickListener, private val viewModel: FileFolderViewModel) :
    RecyclerView.Adapter<ExplorerAdapter.ExplorerViewHolder>() {
    private var data = listOf<FileFolder>()

    interface OnItemClickListener {
        fun onItemClick(fileFolder: FileFolder)
    }

    fun update(fflist: List<FileFolder>) {
        val onlyFolders = mutableListOf<FileFolder>()
        fflist.forEach {
            if (!it.isFile) {
                onlyFolders.add(it)
            }
        }
        data = onlyFolders
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ExplorerViewHolder, position: Int) {

        val elem = data[position]
        holder.itemView.explorer_item_textview.text = "${elem.name} (${elem.inCount})"
        holder.itemView.explorer_item_checkbox.isChecked = if (elem.path in viewModel.checkMap) {
            viewModel.checkMap[elem.path]!!
        } else {
            false
        }

        holder.itemView.explorer_item_checkbox.setOnClickListener {
            viewModel.checkMap[elem.path] = holder.itemView.explorer_item_checkbox.isChecked
        }

        holder.bind(elem, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExplorerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.explorer_item, parent, false)
        return ExplorerViewHolder(view)
    }


    inner class ExplorerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(fileFolder: FileFolder, listener: OnItemClickListener) {
            view.setOnClickListener {
                listener.onItemClick(fileFolder)
            }
        }
    }
}
