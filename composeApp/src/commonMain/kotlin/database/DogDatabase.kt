package database

import androidx.room.Database
import androidx.room.RoomDatabase
import database.dao.DogBreedDao
import database.entities.DogBreed

@Database(entities = [DogBreed::class], version = 1)
abstract class DogDatabase  : RoomDatabase() {
    abstract fun dogBreedDao() : DogBreedDao
}