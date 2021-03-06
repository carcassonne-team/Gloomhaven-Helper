package com.carcassonneteam.gloomhavenhelper.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "class_table")
data class Clazz(
    private var name: String
){
    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
    this.name = name
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int){
        this.id = id
    }

}