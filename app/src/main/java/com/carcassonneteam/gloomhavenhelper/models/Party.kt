package gloomhaven.gloomhavenhelper.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "party_table")
data class Party(
    private var name: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private var image: ByteArray,
    private var reputation: String
){
    @PrimaryKey(autoGenerate = true)
   private var id = 0


    fun getImage(): ByteArray {
        return image
    }

    fun setImage(image: ByteArray) {
        this.image = image
    }

    fun getId(): Int {
        return id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getReputation(): String {
        return reputation
    }
}