package com.carcassonneteam.gloomhavenhelper.views

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.carcassonneteam.gloomhavenhelper.R
import com.carcassonneteam.gloomhavenhelper.models.Player
import gloomhaven.gloomhavenhelper.utils.DataConverter
import java.io.File
import java.io.IOException

class CreatePlayerDialogFragment(private val activity: AppCompatActivity) : AppCompatDialogFragment() {
    private lateinit var name: EditText
    private lateinit var imageSelectBtn: MaterialCardView
    private lateinit var saveButton: Button
    private lateinit var imageView: ImageView
    private var bitmap: Bitmap? = null
    private lateinit var onCreatePlayerListener: OnCreatePlayerListener

   override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
       activity.setTheme(R.style.Theme_GloomhavenHelper)
        val builder = MaterialAlertDialogBuilder(requireContext())
        val inflater: LayoutInflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.player_layout_dialog, null)

        builder.setView(view)
        builder.setCancelable(true)
        builder.setTitle(null)

        name = view.findViewById(R.id.name)
        imageSelectBtn = view.findViewById(R.id.select_image)
        saveButton = view.findViewById(R.id.save_player_button)
        imageView = view.findViewById(R.id.party_image)
        imageSelectBtn.setOnClickListener {
            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, 1)
        }
       saveButton.setOnClickListener {
           val name = name.text.toString()
           if (name.isNotEmpty() && bitmap != null) {
               val player = Player(name, DataConverter.convertImageToByteArray(bitmap!!))
               onCreatePlayerListener.saveNewPlayer(player)
               dismiss()
           } else if (name.isNotEmpty()) {
               val player = Player(
                   name,
                   DataConverter.convertImageToByteArray(
                       BitmapFactory.decodeResource(
                           resources,
                           R.drawable.default_player
                       )
                   )
               )
               onCreatePlayerListener.saveNewPlayer(player)
               dismiss()
           }
       }
       return builder.create()
    }

   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val selectedImage = data!!.data!!
                val imageFile = File(getRealPathFromURI(selectedImage))
                try {
                    bitmap = BitmapFactory.decodeFile(imageFile.path)
                    imageView.setImageBitmap(bitmap)
                    Log.d(tag, "onActivityResult: ")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getRealPathFromURI(contentURI: Uri): String {
        val result: String
        val cursor: Cursor =
            requireActivity().contentResolver.query(contentURI, null, null, null, null)!!
        cursor.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        result = cursor.getString(idx)
        cursor.close()
        return result
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onCreatePlayerListener = context as OnCreatePlayerListener
    }

    interface OnCreatePlayerListener {
        fun saveNewPlayer(player: Player)
    }
}