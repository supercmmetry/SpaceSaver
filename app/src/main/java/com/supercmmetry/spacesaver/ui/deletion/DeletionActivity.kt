package com.supercmmetry.spacesaver.ui.deletion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.supercmmetry.spacesaver.R
import com.supercmmetry.spacesaver.data.FileFolder
import com.supercmmetry.spacesaver.ui.explorer.FileFolderViewModel

class DeletionActivity : AppCompatActivity() {
    companion object {
        var fileFolderViewModel : FileFolderViewModel? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deletion)
    }
}