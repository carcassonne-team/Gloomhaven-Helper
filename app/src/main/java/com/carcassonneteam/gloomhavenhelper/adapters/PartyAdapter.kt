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
import com.carcassonneteam.gloomhavenhelper.models.Party
import gloomhaven.gloomhavenhelper.utils.DataConverter

class PartyAdapter(
    private var partyList: List<Party>
) : RecyclerView.Adapter<PartyAdapter.AdapterViewHolder>() {

    lateinit var onPartyClickListener: OnPartyClickListener

    @NonNull
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.party_item_layout, null)
        return AdapterViewHolder(view, onPartyClickListener)
    }

    fun setItemClickListener(onPartyClickListener: OnPartyClickListener) {
        this.onPartyClickListener = onPartyClickListener
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val singleParty: Party = partyList[position]
        holder.imageView.setImageBitmap(DataConverter.convertByteArrayToImage(singleParty.getImage()))
        holder.partyText.text = singleParty.getName()
        holder.reputationText.text = singleParty.getReputation()
    }

    fun getPartyAt(position: Int): Party {
        val party: Party = partyList[position]
        party.setId(partyList[position].getId())
        return party
    }

    inner class AdapterViewHolder(
        itemView: View,
        onPartyClickListener: OnPartyClickListener
    ) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
        var reputationText: TextView
        var imageView: ImageView
        var partyText: TextView
        var cardView: MaterialCardView
        var onPartyClickListener: OnPartyClickListener

        init {
            this.onPartyClickListener = onPartyClickListener
            reputationText = itemView.findViewById(R.id.reputation)
            imageView = itemView.findViewById(R.id.party_image)
            partyText = itemView.findViewById(R.id.party_name)
            cardView = itemView.findViewById(R.id.card_view)
            cardView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            val currentParty = partyList[position]
            onPartyClickListener.onPartyClick(currentParty)
        }

    }

    override fun getItemCount(): Int {
        return partyList.size
    }

    interface OnPartyClickListener {
        fun onPartyClick(party: Party)
    }
}