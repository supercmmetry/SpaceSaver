package com.supercmmetry.spacesaver.ui.explorer

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.supercmmetry.spacesaver.R
import com.supercmmetry.spacesaver.adapter.ExplorerAdapter
import com.supercmmetry.spacesaver.data.FileFolder
import com.supercmmetry.spacesaver.ui.dashboard.AnalyticsFragment
import com.supercmmetry.spacesaver.ui.dashboard.MainActivity
import com.supercmmetry.spacesaver.ui.deletion.DeletionActivity
import kotlinx.android.synthetic.main.fragment_explorer.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ExplorerFragment : Fragment(), ExplorerAdapter.OnItemClickListener {
    private val newViewModel by sharedViewModel<FileFolderViewModel>()
    var viewModel : FileFolderViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explorer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (DeletionActivity.fileFolderViewModel != null) {
            viewModel = DeletionActivity.fileFolderViewModel!!
        } else {
            viewModel = newViewModel
            DeletionActivity.fileFolderViewModel = viewModel
        }

        DeletionActivity.fileFolderViewModel = viewModel

        val adapter = ExplorerAdapter(this, viewModel!!)

        fragment_explorer_recyclerview.layoutManager = LinearLayoutManager(requireContext())
        fragment_explorer_recyclerview.adapter = adapter

        viewModel?.populate(AnalyticsFragment.selectedPath)

        viewModel?.liveFileFolderData?.observe(this, Observer {
            fflist ->
            adapter.update(fflist)
        })

        this.view!!.isFocusableInTouchMode = true
        this.view!!.requestFocus()

        var dual = false
        this.view!!.setOnKeyListener { view: View, i: Int, keyEvent: KeyEvent ->
            dual = !dual
            if (dual && i == KeyEvent.KEYCODE_BACK) {
                if (viewModel?.currPath == AnalyticsFragment.selectedPath) {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                } else {
                    viewModel?.goToParent()
                }
            }
            i == KeyEvent.KEYCODE_BACK
        }
    }

    override fun onItemClick(fileFolder: FileFolder) {
        viewModel?.populate(fileFolder.path)
    }

}