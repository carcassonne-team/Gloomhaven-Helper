package com.carcassonneteam.gloomhavenhelper.views

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.carcassonneteam.gloomhavenhelper.R

class ItemsDetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_details)
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            val i = Intent(applicationContext, ItemsActivity::class.java)
            startActivity(i)
            finish()
        }
        val intent: Intent = intent
        val args = intent.getBundleExtra("BUNDLE")
        val name: TextView = findViewById(R.id.layout_title)
        val description: TextView = findViewById(R.id.description)
        val price: TextView = findViewById(R.id.price)
        val vType: TextView = findViewById(R.id.type)
        val vState: TextView = findViewById(R.id.state)
        val type = args!!.getString("type")
        val state = args.getString("state")
        name.text = args.getString("name")
        description.text = args.getString("description")
        price.text = args.getString("price")
        when (type) {
            "1" -> {
                vType.text = getString(R.string.items_one_handed)
                vType.text = getString(R.string.items_two_handed)
            }
            "2" -> vType.text = getString(R.string.items_two_handed)
            "3" -> vType.text = getString(R.string.items_helmet)
            "4" -> vType.text = getString(R.string.items_torso)
            "5" -> vType.text = getString(R.string.items_legs)
            "6" -> vType.text = getString(R.string.items_small_item)
        }
        when (state) {
            "1" -> vState.text = getString(R.string.items_state_rotated)
            "2" -> vState.text = getString(R.string.items_state_discarded)
            "3" -> vState.text = getString(R.string.items_state_passive)
        }
    }
}