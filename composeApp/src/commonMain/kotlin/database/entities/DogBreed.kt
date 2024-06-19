package database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class DogBreed(
    @PrimaryKey(autoGenerate = true)var id : Long = 0,
    var name : String = "",
    var parentBreed : Long = -1
){}