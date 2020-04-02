package com.supercmmetry.spacesaver.ui.dashboard

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.supercmmetry.spacesaver.R
import com.supercmmetry.spacesaver.adapter.AnalyticsAdapter
import com.supercmmetry.spacesaver.data.Analytics
import com.supercmmetry.spacesaver.data.FileFolder
import com.supercmmetry.spacesaver.ui.explorer.ExplorerActivity
import com.supercmmetry.spacesaver.ui.explorer.FileFolderViewModel
import kotlinx.android.synthetic.main.fragment_analytics.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AnalyticsFragment : Fragment(), AnalyticsAdapter.OnItemClickListener {
    private val viewModel by sharedViewModel<AnalyticsViewModel>()

    companion object {
        var selectedPath: String = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analytics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AnalyticsAdapter(this)

        fragment_analytics_recyclerview.layoutManager = LinearLayoutManager(requireContext())
        fragment_analytics_recyclerview.adapter = adapter

        viewModel.liveAnalytics.observe(this, Observer { analytics ->
            adapter.update(analytics)
        })

    }

    override fun onItemClicked(analytics: Analytics) {
        selectedPath = analytics.mountPoint
        val intent = Intent(requireContext(), ExplorerActivity::class.java)
        startActivity(intent)
    }

}
