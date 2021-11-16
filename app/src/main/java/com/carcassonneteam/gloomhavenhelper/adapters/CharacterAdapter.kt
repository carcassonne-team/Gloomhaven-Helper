package com.carcassonneteam.gloomhavenhelper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.carcassonneteam.gloomhavenhelper.R
import com.carcassonneteam.gloomhavenhelper.models.Character
import gloomhaven.gloomhavenhelper.utils.DataConverter

class CharacterAdapter(
    private var characterList: List<Character>
) : RecyclerView.Adapter<CharacterAdapter.AdapterViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterViewHolder {
        parent.context.setTheme(R.style.Theme_GloomhavenHelper)
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.single_character_layout, null)
        return AdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val character: Character = characterList[position]
        holder.titleView.text = character.getName()
        holder.imageView.setImageBitmap(DataConverter.convertByteArrayToImage(character.getImage()))
        holder.clazzView.text = character.getClazz()
    }

    fun getCharacterAt(position: Int): Character {
        val character: Character = characterList[position]
        character.setId(characterList[position].getId())
        return character
    }

    inner class AdapterViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView
        var imageView: ImageView
        var clazzView: TextView

        init {
            titleView = itemView.findViewById(R.id.player_name)
            imageView = itemView.findViewById(R.id.party_image)
            clazzView = itemView.findViewById(R.id.class_name)
        }

    }

    override fun getItemCount(): Int {
        return characterList.size
    }
}