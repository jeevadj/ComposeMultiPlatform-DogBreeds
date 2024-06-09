package di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import networkhelpers.DogApi
import org.koin.core.KoinApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import repositories.DogRepo
import viewmodel.ListViewModel


val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient{
            install(ContentNegotiation){
                json(json, contentType = ContentType.Any)
            }
        }
    }

    single { DogApi(get()) }
    single { DogRepo(get()) }
}
