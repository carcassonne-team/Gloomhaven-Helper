package com.carcassonneteam.gloomhavenhelper.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.carcassonneteam.gloomhavenhelper.models.Character
import io.reactivex.Flowable


@Dao
interface CharacterDao {
    @Insert
    fun insert(character: Character)

    @Update
    fun update(character: Character)

    @Delete
    fun delete(character: Character)

    @Query("DELETE FROM character_table")
    fun deleteAllCharacters()

    @Query("SELECT * FROM character_table WHERE player_id LIKE :player_id")
    fun getAllCharactersByPlayer(player_id: Int): Flowable<List<Character>>

    @Query("DELETE FROM character_table WHERE player_id==:player_id")
    fun deleteAllCharactersUnderPlayer(player_id: Int)

}