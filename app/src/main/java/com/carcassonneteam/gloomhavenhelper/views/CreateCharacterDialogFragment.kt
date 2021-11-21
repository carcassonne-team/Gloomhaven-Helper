package gloomhaven.gloomhavenhelper.views

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.vanniktech.rxpermission.RealRxPermission
import gloomhaven.gloomhavenhelper.R
import gloomhaven.gloomhavenhelper.models.Character
import gloomhaven.gloomhavenhelper.utils.DataConverter
import java.io.File
import java.io.IOException

class CreateCharacterDialogFragment(
    private val activity: AppCompatActivity
) : AppCompatDialogFragment() {
    private lateinit var name: EditText
    private lateinit var imageSelectButton: MaterialCardView
    private lateinit var saveButton: Button
    private lateinit var imageView: ImageView
    private var mBitmap: Bitmap? = null
    private lateinit var spinner: Spinner
    private lateinit var onCreateCharacterListener: CreateCharacterListener
    private val clazz = arrayOf(
        "Sawas Skałosercy",
        "Orchidea Tkaczka Zaklęć",
        "Szczurok Myślołap",
        "Inoks Kark",
        "Człowiek Szelma",
        "Kwatryl Druciarz"
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity.setTheme(R.style.Theme_GloomhavenHelper)
        val builder = MaterialAlertDialogBuilder(requireActivity())
        val inflater = activity.layoutInflater
        val view: View = inflater.inflate(R.layout.character_dialog, null)

        builder.setView(view)
        builder.setCancelable(true)
        builder.setTitle(null)

        name = view.findViewById(R.id.character_name)
        imageSelectButton = view.findViewById(R.id.select_image)
        saveButton = view.findViewById(R.id.save_character_button)
        imageView = view.findViewById(R.id.party_image)
        spinner = view.findViewById(R.id.class_spinner)
        spinner.onItemSelectedListener = spinner.onItemSelectedListener

        val arrayAdapter: ArrayAdapter<*>
        arrayAdapter =
            ArrayAdapter<Any?>(requireContext(), android.R.layout.simple_spinner_item, clazz)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                spinner.prompt = arrayAdapter.getItem(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
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
            val name = name.text.toString()
            val clazz = spinner.prompt.toString()
            if (name.isNotEmpty() && mBitmap != null) {
                val character =
                    Character(name, DataConverter.convertImageToByteArray(mBitmap!!), clazz)
                onCreateCharacterListener.saveNewCharacter(character)
                dismiss()
            } else if (name.isNotEmpty()) {
                val character =
                    Character(
                        name,
                        DataConverter.convertImageToByteArray(
                            BitmapFactory.decodeResource(
                                resources,
                                R.drawable.default_party
                            )
                        ),
                        clazz
                    )
                onCreateCharacterListener.saveNewCharacter(character)
                dismiss()
            } else {
                Snackbar.make(view, R.string.error_note, Snackbar.LENGTH_LONG).show()
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
                    mBitmap = BitmapFactory.decodeFile(imageFile.path)
                    imageView.setImageBitmap(mBitmap)
                    Log.d(tag, "onActivityResult: ")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onCreateCharacterListener = context as CreateCharacterListener
    }

    interface CreateCharacterListener {
        fun saveNewCharacter(character: Character)
    }

    private fun getRealPathFromURI(contentURI: Uri): String {
        val result: String
        val cursor = activity.contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) {
            result = contentURI.path!!
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }
}