package com.supercmmetry.spacesaver.ui.deletion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import com.supercmmetry.spacesaver.R
import com.supercmmetry.spacesaver.data.FileFolder
import com.supercmmetry.spacesaver.ui.commit.CommitActivity
import com.supercmmetry.spacesaver.ui.explorer.FileFolderViewModel
import com.supercmmetry.spacesaver.utils.Recommender
import kotlinx.android.synthetic.main.activity_deletion.*
import java.lang.Integer.min


class DeletionActivity : AppCompatActivity() {
    companion object {
        var fileFolderViewModel: FileFolderViewModel? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deletion)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Choose Files To Keep"

        deletion_swipe_view.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
            .setDisplayViewCount(5).setSwipeDecor(
                SwipeDecor().setPaddingTop(20).setRelativeScale(0.01f).setSwipeInMsgLayoutId(R.layout.fragment_swipe_card_in).setSwipeOutMsgLayoutId(
                    R.layout.fragment_swipe_card_out
                )
            )

        var fflist = mutableListOf<FileFolder>()
        fileFolderViewModel?.checkMap?.forEach {
            if (it.value) {
                fflist.add(FileFolder(it.key))
            }
        }

        var recommender = Recommender(fflist)

        recommender.onCompletion {
            val intent = Intent(this, CommitActivity::class.java)
            intent.putExtra(
                "selectedFiles", bundleOf(
                    "files" to recommender.getSelectedFiles()
                )
            )
            startActivity(intent)
        }

        deletion_continue_button.setOnClickListener {
            val intent = Intent(this, CommitActivity::class.java)
            intent.putExtra(
                "selectedFiles", bundleOf(
                    "files" to recommender.getSelectedFiles()
                )
            )
            startActivity(intent)
        }

        for (i in 1..min(recommender.getQueuedFiles().size, 5)) {
            val ff = recommender.recommend()
            if (ff != null) {
                deletion_swipe_view.addView(
                    CustomSwipeCardView(
                        ff,
                        recommender,
                        deletion_swipe_view,
                        this
                    )
                )
            }
        }


    }
}