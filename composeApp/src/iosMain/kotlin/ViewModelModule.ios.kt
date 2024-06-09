import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import viewmodel.*

actual val viewModelModule = module {
    singleOf(::ListViewModel)
    singleOf(::CommonViewModel)
    singleOf(::DetailViewModel)
}