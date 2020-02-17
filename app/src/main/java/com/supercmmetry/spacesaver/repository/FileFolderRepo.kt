package com.supercmmetry.spacesaver.repository

import com.supercmmetry.spacesaver.data.FileFolder
import com.supercmmetry.spacesaver.ds.FileFolderDataSource

class FileFolderRepo (private val fileFolderDataSource: FileFolderDataSource) {
    fun getFileFolders(path : String) : List<FileFolder> = fileFolderDataSource.getFileFolders(path)
}