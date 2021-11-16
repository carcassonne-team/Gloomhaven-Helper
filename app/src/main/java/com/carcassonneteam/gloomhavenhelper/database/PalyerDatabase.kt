package com.carcassonneteam.gloomhavenhelper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.carcassonneteam.gloomhavenhelper.database.dao.CharacterDao
import com.carcassonneteam.gloomhavenhelper.database.dao.PartyDao
import com.carcassonneteam.gloomhavenhelper.database.dao.PlayerDao
import com.carcassonneteam.gloomhavenhelper.models.Character
import com.carcassonneteam.gloomhavenhelper.models.Party
import com.carcassonneteam.gloomhavenhelper.models.Player

@Database(
    entities = [Party::class, Player::class, Character::class],
    version = 1,
    exportSchema = false
)
abstract class PlayerDatabase : RoomDatabase() {
    abstract fun partyDao(): PartyDao
    abstract fun playerDao(): PlayerDao
    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        var instance: PlayerDatabase? = null

        fun getInstance(context: Context): PlayerDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlayerDatabase::class.java,
                    "player_database"
                ).build()
            }
            return instance!!
        }
    }
}
