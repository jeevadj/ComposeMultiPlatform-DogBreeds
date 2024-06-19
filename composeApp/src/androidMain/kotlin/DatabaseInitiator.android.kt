import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.DogDatabase
import kotlinx.coroutines.Dispatchers
import org.sample.cmpproject.KotlinProjApp

actual fun getDatabaseInit() : RoomDatabase.Builder<DogDatabase> {
    val context = KotlinProjApp.context
    val dbFile = context.getDatabasePath("user.db")
    return Room.databaseBuilder<DogDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver()) // Very important
        .setQueryCoroutineContext(Dispatchers.IO)
}