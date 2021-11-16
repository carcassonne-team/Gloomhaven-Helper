package com.carcassonneteam.gloomhavenhelper.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.carcassonneteam.gloomhavenhelper.models.Player
import io.reactivex.Flowable

@Dao
interface PlayerDao {
    @Insert
    fun insert(player: Player)

    @Update
    fun update(player: Player)

    @Delete
    fun delete(player: Player)

    @Query("DELETE FROM player_table")
    fun deleteAllPlayers()

    @Query("SELECT * FROM player_table WHERE party_id LIKE :party_id")
    fun getAllPlayersByParty(party_id: Int): Flowable<List<Player>>

    @Query("DELETE FROM player_table WHERE party_id==:party_id")
    fun deleteAllPlayersUnderParty(party_id: Int)
}