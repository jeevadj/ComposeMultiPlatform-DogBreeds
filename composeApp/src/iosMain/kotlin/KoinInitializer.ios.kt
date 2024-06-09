import di.dataModule
import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(dataModule, viewModelModule)
        }
    }
}