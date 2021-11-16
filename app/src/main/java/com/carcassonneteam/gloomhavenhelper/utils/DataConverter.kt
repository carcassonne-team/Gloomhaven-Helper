package gloomhaven.gloomhavenhelper.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class DataConverter {
    companion object{
        fun convertImageToByteArray(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream)
            return stream.toByteArray()
        }

        fun convertByteArrayToImage(array: ByteArray): Bitmap {
            return BitmapFactory.decodeByteArray(array, 0, array.size)
        }
    }

}