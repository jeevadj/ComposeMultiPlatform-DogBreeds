import org.koin.dsl.module


actual val databaseModule = module {
    single { getDatabaseInit() }
}