package com.supercmmetry.spacesaver.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.supercmmetry.spacesaver.R
import com.supercmmetry.spacesaver.data.FileFolder
import com.supercmmetry.spacesaver.ui.deletion.DeletionActivity
import com.supercmmetry.spacesaver.ui.explorer.ExplorerActivity
import com.supercmmetry.spacesaver.ui.explorer.FileFolderViewModel
import com.supercmmetry.spacesaver.utils.Recommender
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Dashboard"
        main_continue_button.setOnClickListener {
            if (DeletionActivity.fileFolderViewModel != null && DeletionActivity.fileFolderViewModel!!.checkMap.isNotEmpty()) {
                val intent = Intent(this, DeletionActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select some folders", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
