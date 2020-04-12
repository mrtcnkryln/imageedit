package com.mrtcnkryln.image_edit.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Environment
import android.view.View
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.util.*


class ImageUtil(context : Context) {
    var context : Context? = null
    init {
        this.context = context
    }

    fun saveImage(view: View) {
        val bitmap = this.setViewToBitmapImage(view)
        saveImageTheDevice(bitmap)
    }


    fun setViewToBitmapImage(view: View): Bitmap {

        val returnedBitmap =
            Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.getBackground()
        if (bgDrawable != null) {
            bgDrawable!!.draw(canvas)
        }
        else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap
    }


    fun saveImageTheDevice(finalBitmap: Bitmap) {
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File(root + "/Pictures/saved_images")
        myDir.mkdirs()
        val generator = Random()
        var n = 10000
        n = generator.nextInt(n)
        val fname = "Image-$n.jpg"
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        file.createNewFile()
        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
            Toast.makeText(context,"Image Successfully Saved",Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(context,"A problem occurred",Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

    }
}