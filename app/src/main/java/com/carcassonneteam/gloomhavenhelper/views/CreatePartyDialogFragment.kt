package com.carcassonneteam.gloomhavenhelper.views

import android.Manifest
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
import com.vanniktech.rxpermission.RealRxPermission
import com.carcassonneteam.gloomhavenhelper.R
import com.carcassonneteam.gloomhavenhelper.models.Party
import gloomhaven.gloomhavenhelper.utils.DataConverter
import java.io.File
import java.io.IOException


class CreatePartyDialogFragment(
    private val activity: AppCompatActivity
    ) : AppCompatDialogFragment() {
    private lateinit var party: EditText
    private lateinit var reputation: EditText
    private lateinit var imageSelectButton: MaterialCardView
    private lateinit var saveButton: Button
    private lateinit var imageView: ImageView
    private lateinit var listener: CreatePartyListener
    private var bitmap: Bitmap? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity.setTheme(R.style.Theme_GloomhavenHelper)
        val builder = MaterialAlertDialogBuilder(activity)
        val inflater: LayoutInflater = activity.layoutInflater
        val view: View = inflater.inflate(R.layout.party_layout_dialog, null)

        builder.setView(view)
        builder.setCancelable(true)
        builder.setTitle(null)

        party = view.findViewById(R.id.party_name_input_label)
        imageSelectButton = view.findViewById(R.id.select_image)
        saveButton = view.findViewById(R.id.save_party_button)
        imageView = view.findViewById(R.id.party_image)
        reputation = view.findViewById(R.id.party_reputation_input)
        imageSelectButton.setOnClickListener {
            RealRxPermission.getInstance(getActivity())
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe()
            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, 1)
        }
        saveButton.setOnClickListener {
            val partyName = party.text.toString()
            val reputation = reputation.text.toString()
            if (partyName.isNotEmpty() && bitmap != null) {
                val party =
                    Party(partyName, DataConverter.convertImageToByteArray(bitmap!!), reputation)
                listener.saveNewParty(party)
                dismiss()
            } else if (partyName.isNotEmpty()) {
                val party = Party(
                    partyName,
                    DataConverter.convertImageToByteArray(
                        BitmapFactory.decodeResource(
                            resources,
                            R.drawable.default_party
                        )
                    ),
                    reputation
                )
                listener.saveNewParty(party)
                dismiss()
            }
        }
        return builder.create()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val selectedImage = data!!.data!!
                val imageFile = File(getRealPathFromURI(selectedImage))
                try {
                    bitmap = BitmapFactory.decodeFile(imageFile.path)
                    imageView.setImageBitmap(bitmap)
                    Log.d("CreatePartyDialog", "onActivityResult: ")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CreatePartyListener
    }

    interface CreatePartyListener {
        fun saveNewParty(party: Party)
    }

    private fun getRealPathFromURI(contentURI: Uri): String {
        val result: String
        val cursor: Cursor =
            activity.contentResolver.query(contentURI, null, null, null, null)!!
        cursor.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        result = cursor.getString(idx)
        cursor.close()
        return result
    }
}