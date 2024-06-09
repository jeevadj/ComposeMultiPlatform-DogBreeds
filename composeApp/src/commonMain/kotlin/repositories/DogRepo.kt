package repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import networkhelpers.DogApi
import networkhelpers.RandomImageResult

class DogRepo(private val dogApi: DogApi) {

    private val scope = CoroutineScope(SupervisorJob())

    fun getDogBreeds(onFetched : (HashMap<String,ArrayList<String>>) -> Unit) {
        scope.launch {
            dogApi.getAllDogBreeds(onFetched = onFetched )
        }
    }

    suspend fun getDogImage(breed : String): RandomImageResult {
        println("getDogImage : $breed")
        return dogApi.getRandomDogBreedImage(breed)
    }
}