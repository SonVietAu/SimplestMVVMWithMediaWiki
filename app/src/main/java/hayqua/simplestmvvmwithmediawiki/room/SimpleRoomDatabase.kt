package hayqua.simplestmvvmwithmediawiki.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import hayqua.simplestmvvmwithmediawiki.room.dao.EntryDao
import hayqua.simplestmvvmwithmediawiki.room.dao.SearchDao
import hayqua.simplestmvvmwithmediawiki.room.entities.Entry
import hayqua.simplestmvvmwithmediawiki.room.entities.Search

/**
 * Created by Son Au on 23/03/2018.
 */
@Database(entities = [Entry::class, Search::class], version = 1)
abstract class SimpleRoomDatabase: RoomDatabase() {
    abstract fun entryDao(): EntryDao
    abstract fun searchDao(): SearchDao
}