package com.carcassonneteam.gloomhavenhelper.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class Character(
    private var name: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private var image: ByteArray,
    private var clazz: String,
) {
    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    @ColumnInfo(name = "player_id")
    private var playerId: String = ""

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(newName: String) {
        this.name = newName
    }

    fun getImage(): ByteArray {
        return image
    }


    fun getClazz(): String{
        return clazz
    }

    fun getPlayerId(): String {
        return playerId
    }

    fun setPlayerId(playerId: String){
        this.playerId = playerId
    }

}