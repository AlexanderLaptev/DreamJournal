package app.dreamjournal.data.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.dreamjournal.data.Converters
import app.dreamjournal.data.dream.Dream
import app.dreamjournal.data.dream.DreamRepository
import app.dreamjournal.data.dream.DreamTagCrossRef
import app.dreamjournal.data.dream.DreamTagCrossRefRepository
import app.dreamjournal.data.dream.Tag
import app.dreamjournal.data.dream.TagRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@Database(
    version = 1,
    entities = [
        Dream::class,
        Tag::class,
        DreamTagCrossRef::class,
    ],
)
@TypeConverters(Converters::class)
private abstract class DreamJournalDatabase : RoomDatabase() {
    abstract fun dreamDao(): DreamRepository
    abstract fun tagDao(): TagRepository
    abstract fun dreamTagCrossRefDao(): DreamTagCrossRefRepository
}

private fun provideRoomDatabase(context: Context): DreamJournalDatabase {
    return Room.databaseBuilder(
        context,
        DreamJournalDatabase::class.java,
        "database"
    ).build()
}

val databaseModule = module {
    singleOf(::provideRoomDatabase)
    single { get<DreamJournalDatabase>().dreamDao() }
    single { get<DreamJournalDatabase>().tagDao() }
    single { get<DreamJournalDatabase>().dreamTagCrossRefDao() }
}
