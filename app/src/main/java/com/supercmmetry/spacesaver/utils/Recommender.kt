package com.supercmmetry.spacesaver.utils

import android.util.Log
import com.supercmmetry.spacesaver.data.FileFolder
import java.io.File
import java.lang.Integer.max
import java.lang.Math.pow
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sqrt

class Recommender(private val fflist: List<FileFolder>) {
    private var queue = mutableListOf<FileFolder>()
    private var selectedQueue = mutableListOf<FileFolder>()
    private var maxLd = 0L
    private var maxMd = 0L
    private var currIndex = 0
    private lateinit var callback : () -> Unit
    private var callbackCompleted : Boolean = false
    private var currentCount = 0

    init {
        getAllFiles()
    }

    fun increment() {
        currentCount++
        if (currentCount == queue.size) {
            callback()
        }
    }

    private fun levenshteinDistance(a: String, b: String): Int {
        val levMat = Array(a.length + 1) { Array(b.length + 1) { 0 } }
        for (i in 0..a.length) {
            for (j in 0..b.length) {
                levMat[i][j] = if (min(i, j) == 0) {
                    max(i, j)
                } else {
                    min(
                        1 + min(levMat[i][j - 1], levMat[i - 1][j]),
                        levMat[i - 1][j - 1] + (a[i - 1] != b[j - 1]).toInt()
                    )
                }
            }
        }

        return levMat[a.length][b.length]
    }

    private fun getAllFiles() {
        val bfsQueue = mutableListOf<FileFolder>()
        fflist.forEach {
            if (!it.isFile) {
                bfsQueue.add(it)
            }
        }

        var i: Int = 0
        while (true) {
            if (i == bfsQueue.size) {
                break
            }

            val ff = bfsQueue[i++]
            ff.getObjects().forEach {
                if (it.isFile) {
                    queue.add(it)
                } else {
                    bfsQueue.add(it)
                }
            }

        }
    }

    private fun getTotalLDist(path: String, b: Int = 4): Long {
        return if (selectedQueue.size == 0) {
            0
        } else {
            if (selectedQueue.size <= b) {
                var total: Int = 0
                selectedQueue.forEach {
                    total += levenshteinDistance(it.path, path)
                }
                if (total.toLong() > maxLd) {
                    maxLd = total.toLong()
                }
                total.toLong()
            } else {
                var total: Int = 0
                for (i in (selectedQueue.size - b - 1)..(selectedQueue.size - 1)) {
                    total += levenshteinDistance(selectedQueue[i].path, path)
                }
                if (total.toLong() > maxLd) {
                    maxLd = total.toLong()
                }
                total.toLong()
            }
        }
    }

    private fun getMDist(a: String, b: String): Long {
        return abs(File(a).lastModified() - File(b).lastModified())
    }

    private fun getTotalMDist(path: String, b: Int): Long {
        return if (selectedQueue.size == 0) {
            0
        } else {
            if (selectedQueue.size <= b) {
                var total: Long = 0
                selectedQueue.forEach {
                    total += getMDist(path, it.path)
                }
                if (total > maxMd) {
                    maxMd = total
                }
                total
            } else {
                var total: Long = 0
                for (i in (selectedQueue.size - b - 1)..(selectedQueue.size - 1)) {
                    total += getMDist(path, selectedQueue[i].path)
                }
                if (total > maxMd) {
                    maxMd = total
                }
                total
            }
        }
    }

    private fun getScore(ff: FileFolder): Double {
        var b = 4
        if (selectedQueue.size == 0) {
            return 0.0
        }
        return sqrt(
            pow(getTotalLDist(ff.path, b).toDouble() / (b * maxLd.toDouble()), 2.0) +
                    pow(getTotalMDist(ff.path, b).toDouble() / (b * maxLd.toDouble()), 2.0)
        )
    }

    fun selectFile(ff: FileFolder) {
        selectedQueue.add(ff)
    }

    fun getSelectedFiles() = selectedQueue

    fun getQueuedFiles() = queue

    fun recommend(): FileFolder? {
        var b = 5
        if (currIndex + b >= queue.size) {
            b = queue.size - currIndex - 1
        }


        if (b >= 0) {
            queue.subList(currIndex, currIndex + b).sortWith(
                compareBy<FileFolder> { getScore(it) }.reversed()
            )
        }

        if (currIndex == queue.size) {
            return null
        } else {
            val ff = queue[currIndex++]
            return ff
        }
    }

    fun onCompletion(callback: () -> Unit) {
        this.callback = callback
    }

}