package com.supercmmetry.spacesaver.ui.explorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.supercmmetry.spacesaver.R

class ExplorerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explorer)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Select Folders To Include"
    }
}
