package com.carcassonneteam.gloomhavenhelper.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_table")
data class Player(
    @ColumnInfo(name = "name")
    private var name: String,
    @field:ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private var image: ByteArray
) {
    @PrimaryKey(autoGenerate = true)
    private var id = 0

    @ColumnInfo(name = "party_id")
    private var partyId = 0

    fun getName(): String {
        return name
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun setPartyId(partyId: Int) {
        this.partyId = partyId
    }

    fun getImage(): ByteArray {
        return image
    }

    fun getPartyId(): Int {
        return partyId
    }

}