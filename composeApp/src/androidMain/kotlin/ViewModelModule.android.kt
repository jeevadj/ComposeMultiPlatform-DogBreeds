import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import viewmodel.*

actual val viewModelModule = module {
    viewModelOf(::ListViewModel)
    viewModelOf(::CommonViewModel)
    viewModelOf(::DetailViewModel)
}