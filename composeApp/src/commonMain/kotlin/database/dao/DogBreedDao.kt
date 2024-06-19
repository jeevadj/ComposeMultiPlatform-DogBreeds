package database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import database.entities.DogBreed

@Dao
interface DogBreedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogBreed(dogBreed: DogBreed) : Long?

    @Query("Select * from DogBreed where parentBreed = -1 ")
    suspend fun getAllDogBreeds() : List<DogBreed>?

    @Delete
    suspend fun deleteDogBreed(dogBreed: DogBreed)
}