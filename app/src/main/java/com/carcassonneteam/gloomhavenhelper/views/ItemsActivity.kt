package com.carcassonneteam.gloomhavenhelper.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.carcassonneteam.gloomhavenhelper.R
import com.carcassonneteam.gloomhavenhelper.adapters.ItemJSONAdapter
import com.carcassonneteam.gloomhavenhelper.models.Item
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class ItemsActivity : AppCompatActivity() {

    var listView: ListView? = null
    var arrayList: ArrayList<Item>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_layout)
        listView = findViewById<View>(R.id.list) as ListView?
        arrayList = ArrayList()
        val title: TextView = findViewById(R.id.ClayoutTitle)
        title.text = getString(R.string.items_title)

        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(applicationContext, PartyActivity::class.java)
            startActivity(intent)
            finish()
        }
        try {
            val `object` = JSONObject(readJSON("Items.json"))
            val array = `object`.getJSONArray("data")
            for (i in 0 until array.length()) {
                val jsonObject = array.getJSONObject(i)
                val id = jsonObject.getString("ItemID")
                val name = jsonObject.getString("ItemName")
                val price = jsonObject.getString("ItemPrice")
                val state = jsonObject.getString("StateID")
                val type = jsonObject.getString("TypeID")
                val description = jsonObject.getString("ItemDescription")
                val model = Item()
                model.setName(name)
                model.setDescription(description)
                model.setPrice(price)
                model.setType(type)
                model.setState(state)
                arrayList!!.add(model)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val adapter = ItemJSONAdapter(this, arrayList)
        listView!!.adapter = adapter
        listView!!.onItemClickListener =
            OnItemClickListener {
                    parent,
                    view,
                    position,
                    id ->
                val i2 = Intent(this@ItemsActivity, ItemsDetailActivity::class.java)
                val args = Bundle()
                args.putString("name", arrayList!![position].getName())
                args.putString("description", arrayList!![position].getDescription())
                args.putString("price", arrayList!![position].getPrice())
                args.putString("type", arrayList!![position].getType())
                args.putString("state", arrayList!![position].getState())
                i2.putExtra("BUNDLE", args)
                startActivity(i2)
            }
    }

    private fun readJSON(filename: String): String {
        var json = ""
        json = try {
            val inputStream: InputStream = assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
            return json
        }
        return json
    }
}