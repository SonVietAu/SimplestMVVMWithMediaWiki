package hayqua.simplestmvvmwithmediawiki.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import hayqua.simplestmvvmwithmediawiki.room.entities.Entry

/**
 * Created by Son Au on 23/03/2018.
 */

@Dao
interface EntryDao {

    @Query("SELECT * FROM entries WHERE search_id = :searchId")
    fun getSearchedEntries(searchId: Long): List<Entry>

    @Insert
    fun insert(entry: Entry)
}