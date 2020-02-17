package com.supercmmetry.spacesaver.ui.explorer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.supercmmetry.spacesaver.data.FileFolder
import com.supercmmetry.spacesaver.repository.FileFolderRepo
import com.supercmmetry.spacesaver.ui.dashboard.AnalyticsFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class FileFolderViewModel(private val fileFolderRepo: FileFolderRepo) : ViewModel() {
    var liveFileFolderData = MutableLiveData<List<FileFolder>>()
    val checkMap = mutableMapOf<String, Boolean>()
    var currPath = ""
    private set

    fun goToParent() {
        populate(File(currPath).parent!!)
    }

    fun populate(path: String) {
        currPath = path
        val file = File(currPath)
        if (file.exists() && file.isDirectory) {
            liveFileFolderData.value = fileFolderRepo.getFileFolders(currPath)
        }
    }
}