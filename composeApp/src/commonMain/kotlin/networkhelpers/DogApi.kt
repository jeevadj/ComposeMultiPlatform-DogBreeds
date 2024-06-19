package networkhelpers

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import networkhelpers.responses.RandomImageResponse
import kotlin.random.Random

class DogApi(val httpClient: HttpClient) {

    private val DOG_BASE_URL = "https://dog.ceo/api"

    private fun getDogsUrl(url: String) = "$DOG_BASE_URL/$url"

    suspend fun getAllDogBreeds(onFetched : (HashMap<String,ArrayList<String>>) -> Unit){
        val response = httpClient.get(getDogsUrl("breeds/list/all"))
        println(response.status)
        if(HttpStatusCode.OK == response.status){
            val breeds = hashMapOf<String,ArrayList<String>>()
            val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
            if(json.contains("message")){
                json["message"]?.jsonObject?.forEach { breedsMapRes ->
                    if(breedsMapRes.value.jsonArray.isNotEmpty()){
                        val subBreeds = arrayListOf<String>()
                        breedsMapRes.value.jsonArray.forEach { jsonElement ->
                            jsonElement.jsonPrimitive.contentOrNull?.let { it1 -> subBreeds.add(it1.capitalizeFirstLetter()) }
                        }
                        breeds[breedsMapRes.key.capitalizeFirstLetter()] = subBreeds
                    } else {
                        breeds[breedsMapRes.key.capitalizeFirstLetter()] = arrayListOf()
                    }
                }
                println(breeds)
                onFetched.invoke(breeds)
            }
        }
    }

    suspend fun getRandomDogBreedImage(breed : String): RandomImageResult {
        println("getRandomDogBreedImage")
        val response = httpClient.get(getDogsUrl("breed/$breed/images/random"))
        if(HttpStatusCode.OK == response.status){
            val randomImageResponse = Json.decodeFromString<RandomImageResponse>(response.bodyAsText())
            return RandomImageResult.OnSuccess(randomImageResponse)
        }
        return RandomImageResult.onFailed
    }
}

@Serializable
sealed class RandomImageResult {
    data class OnSuccess(val randomImageResponse: RandomImageResponse) : RandomImageResult()
    data object onFailed : RandomImageResult()
}

fun String.capitalizeFirstLetter(): String {
    return this.replaceFirst(this[0],this[0].uppercaseChar())
}