package com.supercmmetry.spacesaver.ds

import com.supercmmetry.spacesaver.data.FileFolder

class FileFolderDataSource {
    fun getFileFolders(path : String) : List<FileFolder> {
        return FileFolder(path).getObjects()
    }
}