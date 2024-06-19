import androidx.room.RoomDatabase
import database.DogDatabase
import database.entities.DogBreed

expect fun getDatabaseInit(): DogDatabase