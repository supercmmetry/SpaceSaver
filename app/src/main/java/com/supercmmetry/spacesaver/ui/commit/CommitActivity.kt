package com.supercmmetry.spacesaver.ui.commit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.supercmmetry.spacesaver.R
import com.supercmmetry.spacesaver.adapter.CommitAdapter
import com.supercmmetry.spacesaver.data.FileFolder
import com.supercmmetry.spacesaver.ui.dashboard.MainActivity
import kotlinx.android.synthetic.main.activity_commit.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.math.round

class CommitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Finalize Changes"

        val fflist = intent.getBundleExtra("selectedFiles")!!["files"] as List<FileFolder>
        val adapter = CommitAdapter(fflist)
        commit_recycler_view.layoutManager = LinearLayoutManager(this)
        commit_recycler_view.adapter = adapter


        commit_discard_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        GlobalScope.launch {
            var totalSize : Long = 0L
            fflist.forEach {
                totalSize += it.size
            }

            if (totalSize < 1024) {
                commit_apply_button.text = "FREE " + totalSize.toString() + " Bytes"
            } else if (totalSize < 1048576) {
                commit_apply_button.text = "FREE " + round(totalSize.toDouble() / 1024).toString() + " KB"
            } else if (totalSize < 1024 * 1048576){
                commit_apply_button.text = "FREE " + round(totalSize.toDouble() / 1048576).toString() + " MB"
            } else {
                commit_apply_button.text = "FREE " + round(totalSize.toDouble() / (1024 * 1048576)).toString() + " GB"
            }
        }

        commit_apply_button.setOnClickListener {
            for (ff in fflist) {
                File(ff.path).delete()
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Freed space successfully", Toast.LENGTH_LONG).show()
        }
    }
}