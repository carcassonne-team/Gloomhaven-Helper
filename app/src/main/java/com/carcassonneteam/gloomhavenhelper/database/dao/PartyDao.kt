package com.carcassonneteam.gloomhavenhelper.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import gloomhaven.gloomhavenhelper.models.Party
import io.reactivex.Flowable

@Dao
interface PartyDao {
    @Insert
    fun insert(party: Party)

    @Update
    fun update(party: Party)

    @Delete
    fun delete(party: Party)

    @Query("DELETE FROM party_table")
    fun deleteAllParty()

    @Query("SELECT * FROM party_table")
    fun getAllParties(): Flowable<List<Party>>
}