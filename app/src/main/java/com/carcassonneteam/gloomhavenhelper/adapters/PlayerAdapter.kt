package com.carcassonneteam.gloomhavenhelper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.carcassonneteam.gloomhavenhelper.R
import com.google.android.material.card.MaterialCardView
import com.carcassonneteam.gloomhavenhelper.models.Player
import gloomhaven.gloomhavenhelper.utils.DataConverter

class PlayerAdapter (
    private var playerList: List<Player>
) : RecyclerView.Adapter<PlayerAdapter.AdapterViewHolder>() {

    lateinit var onPlayerClickListener: OnPlayerClickListener

    @NonNull
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterViewHolder {
        parent.context.setTheme(R.style.Theme_GloomhavenHelper)
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.single_player_layout, null)
        return AdapterViewHolder(view, onPlayerClickListener)
    }

    fun setItemClickListener(onPartyClickListener: OnPlayerClickListener) {
        this.onPlayerClickListener = onPartyClickListener
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val player = playerList[position]
        holder.titleView.setText(player.getName())
        holder.imageView.setImageBitmap(DataConverter.convertByteArrayToImage(player.getImage()))
    }

    fun getPlayerAt(position: Int): Player {
        val player: Player = playerList[position]
        player.setId(playerList[position].getId())
        return player
    }

    inner class AdapterViewHolder(
        itemView: View,
        onPlayerClickListener: OnPlayerClickListener
    ) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        var onPlayerClickListener: OnPlayerClickListener
        var imageView: ImageView
        var titleView: TextView
        var cardView: MaterialCardView

        init {
            this.onPlayerClickListener = onPlayerClickListener
            imageView = itemView.findViewById(R.id.party_image)
            titleView = itemView.findViewById(R.id.player_name)
            cardView = itemView.findViewById(R.id.select_image)
            cardView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            val currentPlayer = playerList[position]
            onPlayerClickListener.onPartyClick(currentPlayer)
        }

    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    interface OnPlayerClickListener {
        fun onPartyClick(player: Player)
    }
}
