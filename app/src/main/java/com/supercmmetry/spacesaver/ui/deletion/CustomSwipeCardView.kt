package com.supercmmetry.spacesaver.ui.deletion

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.swipe.SwipeIn
import com.mindorks.placeholderview.annotations.swipe.SwipeOut
import com.supercmmetry.spacesaver.R
import com.supercmmetry.spacesaver.data.FileFolder
import com.supercmmetry.spacesaver.utils.GenericFileProvider
import com.supercmmetry.spacesaver.utils.Recommender
import java.io.File
import java.net.URI
import kotlin.math.round


@Layout(R.layout.fragment_custom_swipe_card_view)
class CustomSwipeCardView(
    val fileFolder: FileFolder,
    var recommender: Recommender,
    var swipeView: SwipePlaceHolderView,
    var context: Context
) {

    @com.mindorks.placeholderview.annotations.View(R.id.card_filename)
    lateinit var filename: TextView

    @com.mindorks.placeholderview.annotations.View(R.id.card_filesize)
    lateinit var filesize: TextView

    @com.mindorks.placeholderview.annotations.View(R.id.card_image_view)
    lateinit var imageView : ImageView

    @com.mindorks.placeholderview.annotations.View(R.id.card_open_button)
    lateinit var openFile: Button

    private fun populate() {
        val ff = recommender.recommend()

        if (ff != null) {
            swipeView.addView(
                CustomSwipeCardView(
                    ff,
                    recommender,
                    swipeView,
                    context
                )
            )
        }
    }

    @Resolve
    fun onResolved() {
        filename.text = fileFolder.name
        val size = fileFolder.size
        if (size < 1024) {
            filesize.text = size.toString() + " Bytes"
        } else if (size < 1048576) {
            filesize.text = round(size.toDouble() / 1024).toString() + " KB"
        } else {
            filesize.text = round(size.toDouble() / 1048576).toString() + " MB"
        }

        val ext = fileFolder.getExtension().toLowerCase()
        if (ext == "jpg" || ext == "png" || ext == "jpeg") {
            imageView.setImageBitmap(BitmapFactory.decodeFile(fileFolder.path))
        }

        openFile.setOnClickListener {
            val intent = Intent()
            val uri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", File(fileFolder.path))
            intent.data = uri
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            //intent.type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileFolder.getExtension());
            startActivity(context, Intent.createChooser(intent, "Open in..."), null)
        }
    }

    @SwipeOut
    fun onSwipedOut() {
        recommender.selectFile(fileFolder)
        recommender.increment()
        populate()
    }

    @SwipeIn
    fun onSwipedIn() {
        recommender.increment()
        populate()
    }

}