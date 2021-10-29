package gloomhaven.gloomhavenhelper.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "dungeon_table")
data class Dungeon(
    private var name: String
): Serializable {
    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    fun getId(): Int {
        return id
    }

    fun setId(id: Int){
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String){
        this.name = name
    }
}