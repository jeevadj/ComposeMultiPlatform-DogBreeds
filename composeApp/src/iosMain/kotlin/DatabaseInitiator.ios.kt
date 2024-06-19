import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.DogDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSHomeDirectory

actual fun getDatabaseInit() : RoomDatabase.Builder<DogDatabase> {
    val dbFile = NSHomeDirectory() + "/user.db"
    return Room.databaseBuilder<DogDatabase>(
        name = dbFile,
        factory = { DogDatabase::class.instantiateImpl() } // This too will show error
    )
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver()) // Very important
        .setQueryCoroutineContext(Dispatchers.IO)
}